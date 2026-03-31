<template>
  <v-container fluid class="pa-0 d-flex flex-column align-center" style="background: linear-gradient(180deg, #cae8fa 0%, #f4f9fd 40%, #f4f9fd 100%); min-height: 100vh; padding-bottom: 100px !important;">
    
    <div class="d-flex align-center w-100 px-4 mt-8 mb-6" style="max-width: 450px;">
      <v-btn icon="mdi-menu" variant="text" color="#37474F" size="large" @click="toggleDrawer"></v-btn>
      <h2 class="text-center flex-grow-1 text-white font-weight-black" style="font-size: 1.25rem; letter-spacing: 0.5px; text-shadow: 0px 2px 4px rgba(0,0,0,0.15);">
        ENVOI DES DOCUMENTS
      </h2>
      <div style="width: 48px;"></div>
    </div>

    <div class="w-100 px-5 mb-6" style="max-width: 380px;">
      <v-card color="white" class="pa-5 rounded-xl text-center" elevation="2">
        <span class="text-body-1 font-weight-medium" style="color: #37474F; line-height: 1.4;">
          J'envoie des documents à mon praticien pour compléter mon dossier !
        </span>
      </v-card>
      <div style="width: 0; height: 0; border-left: 12px solid transparent; border-right: 12px solid transparent; border-top: 15px solid white; margin-left: 65%; filter: drop-shadow(0px 3px 2px rgba(0,0,0,0.05));"></div>
    </div>

    <div class="d-flex flex-column align-center justify-center pa-8 mb-8 mt-4" style="width: 85%; max-width: 320px; border: 2px dashed #37474F; border-radius: 24px; background-color: rgba(255,255,255,0.5);">
      <v-icon size="56" color="#37474F" class="mb-3">mdi-camera-outline</v-icon>
      <span class="font-weight-bold" style="color: #37474F; font-size: 1.1rem;">Prendre une photo</span>
    </div>

    <div class="w-100 px-5" style="max-width: 360px;">
      <v-btn
        variant="outlined"
        color="#2A93D5"
        block
        rounded="xl"
        size="large"
        class="text-none font-weight-bold bg-white"
        style="border-width: 1.5px; height: 50px;"
      >
        <v-icon start size="22">mdi-upload-outline</v-icon>
        Importer un document
      </v-btn>
    </div>

  </v-container>
</template>

<script setup>
import { inject } from 'vue'
const toggleDrawer = inject('toggleDrawer')
</script>