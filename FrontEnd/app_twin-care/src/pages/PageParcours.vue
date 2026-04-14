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
      
      <v-card class="mb-10 rounded-xl" elevation="3" style="border: 2px solid white; overflow: visible !important;">
        
        <div style="height: 110px; background-color: #eaf4fc; position: relative; border-radius: 24px 24px 0 0;" class="d-flex justify-center">
          
          <img 
            :src="imageInsomnie" 
            style="height: 155px; position: absolute; bottom: 0; object-fit: contain; z-index: 1;" 
          />
          
        </div>

        <div class="py-3 text-center bg-white font-weight-bold" style="color: #37474F; font-size: 1rem; position: relative; z-index: 2; border-radius: 0 0 24px 24px;">
          Insomnie
        </div>
      </v-card>

      <v-card class="mb-10 rounded-xl overflow-hidden" elevation="3" style="border: 2px solid white;">
        <div style="height: 110px; background-color: #eaf4fc;" class="d-flex justify-center align-center pa-2">
          <div style="width: 100%; height: 100%; border: 1.5px dashed #90caf9; border-radius: 12px; display: flex; justify-content: center; align-items: center; color: #90caf9; font-size: 0.8rem;">
            Illustration Grossesse
          </div>
        </div>
        <div class="py-3 text-center bg-white font-weight-bold" style="color: #37474F; font-size: 1rem;">
          Grossesse
        </div>
      </v-card>

      <v-card class="mb-10 rounded-xl overflow-hidden" elevation="3" style="border: 2px solid white;">
        <div style="height: 110px; background-color: #eaf4fc;" class="d-flex justify-center align-center pa-2">
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
import { inject } from 'vue'
import imageInsomnie from '../assets/insomnie.png'

const toggleDrawer = inject('toggleDrawer')
</script>