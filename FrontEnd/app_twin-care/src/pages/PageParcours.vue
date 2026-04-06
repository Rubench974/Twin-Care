<template>
  <v-container fluid class="pa-0 d-flex flex-column align-center" style="background: linear-gradient(180deg, #cae8fa 0%, #f4f9fd 40%, #f4f9fd 100%); min-height: 100vh; padding-bottom: 100px !important;">
    
    <div class="d-flex align-center w-100 px-4 mt-8 mb-6" style="max-width: 450px;">
      <v-btn icon="mdi-menu" variant="text" color="#37474F" size="large" @click="toggleDrawer"></v-btn>
      <h2 class="text-center flex-grow-1 text-white font-weight-black" style="font-size: 1.25rem; letter-spacing: 0.5px; text-shadow: 0px 2px 4px rgba(0,0,0,0.15);">
        PARCOURS DE SANTÉ
      </h2>
      <div style="width: 48px;"></div>
    </div>

    <div class="w-100 px-5 mt-2" style="max-width: 380px;">
      
      <v-card class="mb-6 rounded-xl overflow-hidden" elevation="3" style="border: 2px solid white;">
        <div style="height: 120px; background-color: #eaf4fc;" class="d-flex justify-center align-center pa-2">
          <div style="width: 100%; height: 100%; border: 1.5px dashed #90caf9; border-radius: 12px; display: flex; justify-content: center; align-items: center; color: #90caf9; font-size: 0.8rem;">
            Illustration Insomnie
          </div>
        </div>
        <div class="py-3 text-center bg-white font-weight-bold" style="color: #37474F; font-size: 1rem;">
          Insomnie
        </div>
      </v-card>

      <v-card class="mb-6 rounded-xl overflow-hidden" elevation="3" style="border: 2px solid white;">
        <div style="height: 120px; background-color: #eaf4fc;" class="d-flex justify-center align-center pa-2">
          <div style="width: 100%; height: 100%; border: 1.5px dashed #90caf9; border-radius: 12px; display: flex; justify-content: center; align-items: center; color: #90caf9; font-size: 0.8rem;">
            Illustration Grossesse
          </div>
        </div>
        <div class="py-3 text-center bg-white font-weight-bold" style="color: #37474F; font-size: 1rem;">
          Grossesse
        </div>
      </v-card>

      <v-card class="mb-6 rounded-xl overflow-hidden" elevation="3" style="border: 2px solid white;">
        <div style="height: 120px; background-color: #eaf4fc;" class="d-flex justify-center align-center pa-2">
          <div style="width: 100%; height: 100%; border: 1.5px dashed #90caf9; border-radius: 12px; display: flex; justify-content: center; align-items: center; color: #90caf9; font-size: 0.8rem;">
            Illustration Thérapie
          </div>
        </div>
        <div class="py-3 text-center bg-white font-weight-bold" style="color: #37474F; font-size: 1rem;">
          Thérapie
        </div>
      </v-card>

    </div>
  </v-container>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue'

const toggleDrawer = inject('toggleDrawer')
const listeParcours = ref([])

const url = "https://twincare-t2xu.onrender.com/api/parcours"

function chargerParcours() {
  const token = localStorage.getItem('user-token')
  const myHeaders = new Headers()
  myHeaders.append("Content-Type", "application/json")
  myHeaders.append("Authorization", "Bearer " + token)

  const fetchOptions = {
    method: "GET",
    headers: myHeaders
  }

  fetch(url, fetchOptions)
    .then((response) => {
      if (response.ok) return response.json()
    })
    .then((dataJSON) => {
      if (dataJSON) {
        listeParcours.value = dataJSON
        console.log("Parcours chargés depuis le serveur")
      }
    })
    .catch((error) => {
      console.log("Erreur :", error)
    })
}

onMounted(() => {
  chargerParcours()
})
</script>