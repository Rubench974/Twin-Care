<template>
  <v-container fluid class="pa-0 d-flex flex-column align-center" style="background: linear-gradient(180deg, #cae8fa 0%, #f4f9fd 40%, #f4f9fd 100%); min-height: 100vh; padding-bottom: 100px !important;">
    
    <div class="d-flex align-center w-100 px-4 mt-8 mb-6" style="max-width: 450px;">
      <v-btn icon="mdi-arrow-left" variant="text" color="#37474F" size="large" @click="retour"></v-btn>
      <h2 class="text-center flex-grow-1 font-weight-black" style="color: #2c3e50; font-size: 1.25rem; letter-spacing: 0.5px; margin-right: 48px;">
        CONFIDENTIALITÉ
      </h2>
    </div>

    <div class="w-100 px-5 mt-2" style="max-width: 400px;">
      <v-card class="rounded-xl pa-5 elevation-3" style="border: 2px solid white; background-color: rgba(255, 255, 255, 0.9);">
        
        <div class="d-flex flex-column align-center mb-4">
          <v-icon size="60" color="#2A93D5" class="mb-2">mdi-shield-check-outline</v-icon>
          <h3 class="text-h6 font-weight-bold text-center" style="color: #37474F; line-height: 1.2;">Protection de vos données</h3>
        </div>

        <p class="text-body-2 text-center mb-4" style="color: #6B7280;">
          Chez Twin-Care, la sécurité de vos informations médicales est notre priorité absolue.
        </p>

        <v-divider class="mb-4"></v-divider>

        <v-list density="compact" bg-color="transparent">
          <v-list-item prepend-icon="mdi-server-security" class="px-0 mb-2">
            <v-list-item-title class="font-weight-medium" style="color: #37474F;">Hébergement HDS</v-list-item-title>
            <v-list-item-subtitle class="text-caption mt-1" style="color: #9e9e9e; white-space: normal;">Vos données sont stockées sur des serveurs certifiés Hébergeur de Données de Santé.</v-list-item-subtitle>
          </v-list-item>

          <v-list-item prepend-icon="mdi-file-document-check-outline" class="px-0">
            <v-list-item-title class="font-weight-medium" style="color: #37474F;">Conformité RGPD</v-list-item-title>
            <v-list-item-subtitle class="text-caption mt-1" style="color: #9e9e9e; white-space: normal;">Traitement strictement confidentiel dans le respect du Règlement Général sur la Protection des Données.</v-list-item-subtitle>
          </v-list-item>
        </v-list>

      </v-card>
    </div>
  </v-container>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function retour() {
  router.push('/documents')
}
</script>