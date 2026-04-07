<template>
<v-container fluid class="bg-blue-lighten-5 fill-height d-flex align-center justify-center">
  <v-card class="elevation-4 rounded-xl pa-6" width="100%" max-width="450">
    <div class="text-center mb-8">
      <v-avatar color="#156500" size="72" class="mb-4 elevation-2">
        <v-icon size="40" color="white">mdi-heart-pulse</v-icon>
      </v-avatar>
      <h1 class="text-h4 font-weight-bold" style="color: #156500;">Twin Care</h1>
    </div>

    <v-alert v-if="errorMessage" type="error" variant="tonal" class="mb-6" closable @click:close="errorMessage = ''">
      {{ errorMessage }}
    </v-alert>

    <v-form ref="formRef" @submit.prevent="soumettreConnexion" :disabled="chargement">
      <v-text-field
        v-model="email" label="Adresse e-mail" prepend-inner-icon="mdi-email-outline"
        variant="outlined" color="#156500" class="mb-4" :rules="[regles.requis]" required
      ></v-text-field>

      <v-text-field
        v-model="motDePasse" label="Mot de passe" prepend-inner-icon="mdi-lock-outline"
        type="password" variant="outlined" color="#156500" class="mb-6" :rules="[regles.requis]" required
      ></v-text-field>

      <v-btn
        type="submit" color="#156500" size="x-large" block
        class="font-weight-bold text-white rounded-lg" :loading="chargement" elevation="2"
      >
        Se connecter
      </v-btn>
    </v-form>
  </v-card>
</v-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api' // Import de l'instance axios (le relais)

const router = useRouter()
const emit = defineEmits(['mise-a-jour-auth'])

const formRef = ref(null)
const email = ref('')
const motDePasse = ref('')
const chargement = ref(false)
const errorMessage = ref('')
const regles = { requis: v => !!v || 'Requis' }

const soumettreConnexion = async () => {
  const { valid } = await formRef.value.validate()
  if (!valid) return

  chargement.value = true
  errorMessage.value = ''

  try {
    // 1. On envoie la requête avec les BONS noms de variables pour ton Swagger
    const reponse = await api.post('/api/auth/login', {
      email: email.value, 
      motDePasse: motDePasse.value // <-- C'est ce champ qui bloquait l'accès !
    })

    const donneesUtilisateur = reponse.data
    console.log("Connexion réussie ! Réponse :", donneesUtilisateur)

    // 2. On sauvegarde le Token JWT pour les prochaines requêtes
    if (donneesUtilisateur.token) {
      localStorage.setItem('token', donneesUtilisateur.token)
    }

    // 3. On détermine le rôle (le toLowerCase permet d'éviter les bugs si le back renvoie "MEDECIN" en majuscules)
    const role = donneesUtilisateur.role ? donneesUtilisateur.role.toLowerCase() : 'assistant' 
    
    localStorage.setItem('userRole', role)
    emit('mise-a-jour-auth')
    
    // 4. Redirection vers le bon tableau de bord
    if (role === 'assistant') {
      router.push('/assistant')
    } else {
      router.push('/medecin')
    }

  } catch (erreur) {
    console.error("Échec de connexion :", erreur)
    
    // Gestion précise de l'affichage de l'erreur
    if (erreur.response) {
      if (erreur.response.status === 403 || erreur.response.status === 401) {
        errorMessage.value = "Identifiants incorrects ou accès refusé." 
      } else {
        errorMessage.value = `Erreur du serveur (Code ${erreur.response.status}).`
      }
    } else {
      errorMessage.value = "Impossible de joindre le serveur. Vérifiez votre connexion."
    }
  } finally {
    chargement.value = false
  }
}
</script>