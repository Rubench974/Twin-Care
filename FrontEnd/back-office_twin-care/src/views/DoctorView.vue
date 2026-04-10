<template>
  <v-app style="background-color: #f8f9fa;">
    <v-app-bar color="#37474F" elevation="2">
      <v-icon class="ml-4" color="white" size="large">mdi-stethoscope</v-icon>
      <v-toolbar-title class="text-white font-weight-bold ml-2">Twin Care | Consultation</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-chip color="white" variant="outlined" class="mr-4">
        <v-icon start>mdi-doctor</v-icon> Dr. House
      </v-chip>
      <v-btn icon @click="deconnexion" color="white">
        <v-icon>mdi-logout</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <v-container fluid class="pa-6">
        <v-row class="mb-4">
          <v-col cols="12">
            <h1 class="text-h4 font-weight-bold" style="color: #37474F;">Salle d'attente virtuelle</h1>
            <p class="text-subtitle-1 text-grey-darken-1">Dossiers avec StatutDossier = PRET_POUR_CONSULTATION</p>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="12" md="4" v-for="patient in patientsPrets" :key="patient.id">
            <v-card class="rounded-xl elevation-3 border-t-lg" style="border-top: 4px solid #156500; height: 100%;">
              <v-card-title class="d-flex justify-space-between align-start pt-4 px-4 pb-0">
                <div>
                  <div class="text-h6 font-weight-bold" style="color: #37474F;">{{ patient.nom }}</div>
                  <div class="text-subtitle-2 text-grey-darken-1">ID Dossier : {{ patient.dossierId }}</div>
                </div>
                <v-avatar color="green-lighten-5">
                  <v-icon color="#156500">mdi-check-circle</v-icon>
                </v-avatar>
              </v-card-title>
              
              <v-card-text class="px-4 py-3 mt-4">
                <v-alert type="success" variant="tonal" density="compact" class="mb-2">
                  Documents validés par l'assistant !
                </v-alert>
                <div class="mb-2">
                  <span class="font-weight-bold text-caption text-grey-darken-1 text-uppercase">Motif IA Détecté :</span>
                  <p class="mb-0 font-weight-medium">Toux sévère, fièvre signalée dans le chatbot.</p>
                </div>
              </v-card-text>
              
              <v-divider></v-divider>
              
              <v-card-actions class="px-4 py-3 bg-grey-lighten-4">
                <v-spacer></v-spacer>
                <v-btn color="#156500" variant="elevated" class="text-white px-4 font-weight-bold rounded-lg" prepend-icon="mdi-folder-account">
                  Commencer la consultation
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Données purement visuelles pour le médecin
const patientsPrets = ref([
  { id: 1, nom: "Dubois Emma", dossierId: 1 },
  { id: 2, nom: "Lefebvre Thomas", dossierId: 2 },
])

const deconnexion = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('role')
  router.push('/login')
}
</script>