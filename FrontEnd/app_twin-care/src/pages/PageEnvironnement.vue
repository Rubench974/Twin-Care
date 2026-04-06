<template>
  <div style="position: relative; width: 100%; height: 100vh; overflow: hidden;">

    <div id="mapcarto" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 0;"></div>

    <div style="position: absolute; top: 40px; left: 0; width: 100%; padding: 0 16px; display: flex; align-items: center; z-index: 1000;">

      <v-btn icon="mdi-menu" elevation="4" class="mr-3 bg-white" @click="toggleDrawer" style="width: 48px; height: 48px; color: #37474F;"></v-btn>

      <v-card class="rounded-pill d-flex align-center px-5 py-2 elevation-4 flex-grow-1" style="max-width: 260px;">
        <div>
          <div class="font-weight-bold" style="font-size: 0.95rem; color: #37474F;">
            Carte {{ modeActif === 'pollen' ? 'du pollen' : 'de l\'air' }}
          </div>
          <div class="text-caption" style="color: #6B7280; margin-top: -2px;">
            {{ modeActif === 'pollen' ? 'Taux modéré' : 'Qualité bonne' }}
          </div>
        </div>
        <v-spacer></v-spacer>
        <v-icon size="28" color="#37474F">
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
import { ref, onMounted, inject } from "vue";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

const toggleDrawer = inject('toggleDrawer')
const modeActif = ref('pollen');

let map = null;
let currentLayer = null;

function initialiserCarte() {
  map = L.map('mapcarto', { zoomControl: false }).setView([43.60548, 2.24167], 13);

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {}).addTo(map);

  dessinerZone();
}

function dessinerZone() {
  if (currentLayer !== null) {
    map.removeLayer(currentLayer);
  }

  if (modeActif.value === 'pollen') {
    currentLayer = L.circle([43.60548, 2.24167], {
      stroke: false,
      fillColor: '#fbc02d',
      fillOpacity: 0.35,
      radius: 4000 
    }).addTo(map);
  } else {
    currentLayer = L.circle([43.60548, 2.24167], {
      stroke: false,
      fillColor: '#4caf50',
      fillOpacity: 0.35,
      radius: 5000 
    }).addTo(map);
  }
}

function basculerMode() {
  if (modeActif.value === 'pollen') {
    modeActif.value = 'air';
  } else {
    modeActif.value = 'pollen';
  }
  dessinerZone();
}

onMounted(() => {
  initialiserCarte();
});
</script>

<style scoped>
:deep(.leaflet-control-attribution) {
  display: none !important;
}
#mapcarto {
  z-index: 0;
}
</style>