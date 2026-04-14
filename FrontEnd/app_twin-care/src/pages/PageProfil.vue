<template>
  <v-container fluid class="pa-0 d-flex flex-column align-center" style="background: linear-gradient(180deg, #cae8fa 0%, #f4f9fd 40%, #f4f9fd 100%); min-height: 100vh; padding-bottom: 100px !important;">
    
    <div class="d-flex align-center w-100 px-4 mt-8 mb-6" style="max-width: 450px;">
      <v-btn icon="mdi-arrow-left" variant="text" color="#37474F" size="large" @click="retour"></v-btn>
      <h2 class="text-center flex-grow-1 font-weight-black" style="color: #2c3e50; font-size: 1.25rem; letter-spacing: 0.5px;">
        MON PROFIL
      </h2>
      <div style="width: 48px;"></div>
    </div>

    <div class="w-100 px-5 mt-2" style="max-width: 400px;">
      <v-card class="rounded-xl pa-5 elevation-3" style="border: 2px solid white; background-color: rgba(255, 255, 255, 0.9);">
        
        <div class="d-flex flex-column align-center mb-6">
          <v-avatar size="90" color="#eaf4fc" class="mb-3" style="border: 3px solid #2A93D5;">
            <v-icon size="50" color="#2A93D5">mdi-account</v-icon>
          </v-avatar>
          <h3 class="text-h6 font-weight-bold text-capitalize" style="color: #37474F; line-height: 1.2;">{{ infosPatient.prenom }} {{ infosPatient.nom }}</h3>
          <v-chip color="#2A93D5" size="small" class="mt-2 font-weight-bold" variant="flat">{{ infosPatient.role }}</v-chip>
        </div>

        <v-divider class="mb-4"></v-divider>

        <v-list density="compact" bg-color="transparent">
          
          <v-list-item prepend-icon="mdi-email-outline" class="px-0">
            <v-list-item-title class="text-caption" style="color: #9e9e9e;">Adresse email</v-list-item-title>
            <v-list-item-subtitle class="font-weight-medium" style="color: #37474F; font-size: 0.95rem;">{{ infosPatient.email }}</v-list-item-subtitle>
          </v-list-item>

          <v-list-item prepend-icon="mdi-phone-outline" class="px-0 mt-2">
            <v-list-item-title class="text-caption" style="color: #9e9e9e;">Téléphone</v-list-item-title>
            <v-list-item-subtitle class="font-weight-medium" style="color: #37474F; font-size: 0.95rem;">Non renseigné</v-list-item-subtitle>
          </v-list-item>

          <v-list-item prepend-icon="mdi-calendar-blank-outline" class="px-0 mt-2">
            <v-list-item-title class="text-caption" style="color: #9e9e9e;">Date de naissance</v-list-item-title>
            <v-list-item-subtitle class="font-weight-medium" style="color: #37474F; font-size: 0.95rem;">Non renseignée</v-list-item-subtitle>
          </v-list-item>

          <v-list-item prepend-icon="mdi-card-account-details-outline" class="px-0 mt-2">
            <v-list-item-title class="text-caption" style="color: #9e9e9e;">N° Sécurité Sociale</v-list-item-title>
            <v-list-item-subtitle class="font-weight-medium" style="color: #37474F; font-size: 0.95rem;">Non renseigné</v-list-item-subtitle>
          </v-list-item>

        </v-list>
        
        <v-btn block variant="tonal" color="#2A93D5" class="mt-6 text-none font-weight-bold rounded-lg">
          Modifier mes informations
        </v-btn>

      </v-card>
    </div>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const infosPatient = ref({
  prenom: 'Non renseigné',
  nom: '',
  email: 'Non renseigné',
  role: 'Utilisateur'
})

onMounted(() => {
  infosPatient.value.prenom = localStorage.getItem('prenom') || 'Non renseigné'
  infosPatient.value.nom = localStorage.getItem('nom') || ''
  infosPatient.value.email = localStorage.getItem('email') || 'Non renseigné'
  
  let roleBrut = localStorage.getItem('role') || 'Utilisateur'
  infosPatient.value.role = roleBrut === 'PATIENT' ? 'Patient' : roleBrut
})

function retour() {
  router.push('/documents')
}
</script>