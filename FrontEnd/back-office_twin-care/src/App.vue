<template>
<v-app>
  <!-- Barre de navigation affichée seulement si l'utilisateur est connecté -->
  <v-app-bar v-if="utilisateurRole" color="#156500" class="text-white" elevation="2">
    <v-app-bar-title class="font-weight-bold">
      <v-icon start>mdi-heart-pulse</v-icon> Twin Care
    </v-app-bar-title>
    <v-spacer></v-spacer>
    
    <v-chip class="mr-4" color="white" variant="outlined" v-if="utilisateurRole">
      Espace {{ utilisateurRole.toUpperCase() }}
    </v-chip>
    
    <v-btn variant="text" prepend-icon="mdi-logout" @click="deconnexion">
      Déconnexion
    </v-btn>
  </v-app-bar>

  <v-main>
    <!-- C'est ici que LoginView, AssistantView ou DoctorView s'affichent -->
    <router-view @mise-a-jour-auth="verifierAuth" />
  </v-main>
</v-app>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const utilisateurRole = ref(null)

// Met à jour l'affichage de la barre de menu
const verifierAuth = () => {
utilisateurRole.value = localStorage.getItem('userRole')
}

// Gère la déconnexion
const deconnexion = () => {
localStorage.removeItem('userRole')
verifierAuth()
router.push('/login')
}

// Vérifie au démarrage si on est déjà connecté
onMounted(() => {
verifierAuth()
})
</script>