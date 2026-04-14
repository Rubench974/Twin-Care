<template>
  <div style="position: relative; width: 100%; height: 100vh; overflow: hidden;">

    <div ref="mapContainer" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 0;"></div>

    <div style="position: absolute; top: 40px; left: 0; width: 100%; padding: 0 16px; display: flex; align-items: center; z-index: 1000;">

      <v-btn icon="mdi-menu" elevation="4" class="mr-3 bg-white" @click="toggleDrawer" style="width: 48px; height: 48px; color: #37474F;"></v-btn>

      <v-card class="rounded-pill d-flex align-center px-5 py-2 elevation-4 flex-grow-1" style="max-width: 260px;">
        <div>
          <div class="font-weight-bold" style="font-size: 0.95rem; color: #37474F;">
            Carte {{ modeActif === 'pollen' ? 'du pollen' : 'de l\'air' }}
          </div>
          <div v-if="!isLoading" class="text-caption font-weight-medium" :style="{ color: modeActif === 'pollen' ? envData.pollenColor : envData.airColor, marginTop: '-2px' }">
            {{ modeActif === 'pollen' ? envData.pollenStatus : envData.airStatus }}
          </div>
          <div v-else class="text-caption" style="color: #6B7280; margin-top: -2px;">
            Recherche de position...
          </div>
        </div>
        <v-spacer></v-spacer>
        <v-icon size="28" :color="modeActif === 'pollen' ? envData.pollenColor : envData.airColor">
          {{ modeActif === 'pollen' ? 'mdi-flower-pollen' : 'mdi-cloud-outline' }}
        </v-icon>
      </v-card>

      <v-btn icon elevation="4" class="ml-3 bg-white" @click="basculerMode" style="width: 48px; height: 48px;">
        <v-icon size="24" color="#37474F">
          {{ modeActif === 'pollen' ? 'mdi-cloud-outline' : 'mdi-flower-pollen' }}
        </v-icon>
      </v-btn>

    </div>

  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, inject, nextTick } from "vue";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

const toggleDrawer = inject('toggleDrawer');
const modeActif = ref('pollen');
const isLoading = ref(true);

const envData = ref({
  airStatus: "En attente...",
  airColor: "#9e9e9e",
  pollenStatus: "En attente...",
  pollenColor: "#9e9e9e"
});

const mapContainer = ref(null); 
let map = null;
let currentLayer = null;

let userLat = 43.60548;
let userLon = 2.24167;

function demanderGeolocalisation() {
  isLoading.value = true;

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        userLat = position.coords.latitude;
        userLon = position.coords.longitude;
        initialiserCarte(userLat, userLon);
      },
      (error) => {
        console.warn("Géolocalisation refusée ou indisponible.", error);
        initialiserCarte(userLat, userLon);
      },
      { timeout: 10000 }
    );
  } else {
    initialiserCarte(userLat, userLon);
  }
}

async function initialiserCarte(lat, lon) {
  await nextTick();

  if (!mapContainer.value) return;

  if (!map) {
    map = L.map(mapContainer.value, { zoomControl: false }).setView([lat, lon], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {}).addTo(map);
  } else {
    map.setView([lat, lon], 13);
  }
  
  chargerDonneesBackend(lat, lon);
}

function chargerDonneesBackend(lat, lon) {
  const token = localStorage.getItem('token');
  const url = `https://twincare-t2xu.onrender.com/api/environnement/donnees?lat=${lat}&lon=${lon}`;

  fetch(url, {
    method: "GET",
    headers: { "Authorization": "Bearer " + token }
  })
  .then(res => res.json())
  .then(data => {
    envData.value = data;
    isLoading.value = false;
    dessinerZone();
  })
  .catch(err => {
    console.error("Erreur API Environnement:", err);
    isLoading.value = false;
    dessinerZone();
  });
}

function dessinerZone() {
  if (!map) return;

  if (currentLayer !== null) {
    map.removeLayer(currentLayer);
  }

  const couleurActuelle = modeActif.value === 'pollen' ? envData.value.pollenColor : envData.value.airColor;

  currentLayer = L.circle([userLat, userLon], {
    stroke: false,
    fillColor: couleurActuelle,
    fillOpacity: 0.35,
    radius: modeActif.value === 'pollen' ? 4000 : 5000 
  }).addTo(map);
}

function basculerMode() {
  modeActif.value = modeActif.value === 'pollen' ? 'air' : 'pollen';
  dessinerZone();
}

onMounted(() => {
  demanderGeolocalisation();
});

onUnmounted(() => {
  if (map) {
    map.remove();
    map = null;
  }
});
</script>

<style scoped>
:deep(.leaflet-control-attribution) {
  display: none !important;
}
</style>