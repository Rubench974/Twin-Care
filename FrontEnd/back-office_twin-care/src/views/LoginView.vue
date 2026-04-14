<template>
  <v-app style="background-color: #E3F2FD;">
    <v-main class="d-flex align-center justify-center">
      <v-card class="pa-8 rounded-xl elevation-5" width="400" color="#2b2b2b" theme="dark">
        <div class="text-center mb-6">
          <v-avatar color="#156500" size="70" class="mb-4 elevation-3">
            <v-icon size="40" color="white">mdi-heart-pulse</v-icon>
          </v-avatar>
          <h1 class="text-h4 font-weight-bold" style="color: #4CAF50;">Twin Care</h1>
        </div>

        <v-alert v-if="erreur" type="error" variant="tonal" class="mb-4">
          {{ erreur }}
        </v-alert>

        <v-form @submit.prevent="soumettreConnexion" :disabled="chargement">
          <v-text-field
            v-model="email"
            label="Adresse e-mail"
            prepend-inner-icon="mdi-email-outline"
            variant="outlined"
            class="mb-2"
            required
          ></v-text-field>

          <v-text-field
            v-model="motDePasse"
            label="Mot de passe"
            type="password"
            prepend-inner-icon="mdi-lock-outline"
            variant="outlined"
            class="mb-6"
            required
          ></v-text-field>

          <v-btn
            type="submit"
            color="#156500"
            size="large"
            block
            class="text-white font-weight-bold rounded-lg"
            :loading="chargement"
          >
            Se connecter
          </v-btn>
        </v-form>
      </v-card>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api'

const router = useRouter()
const email = ref('')
const motDePasse = ref('')
const erreur = ref('')
const chargement = ref(false)

const soumettreConnexion = async () => {
  chargement.value = true
  erreur.value = ''

  try {
    const reponse = await api.post('/api/auth/login', {
      email: email.value,
      motDePasse: motDePasse.value
    })

    const token = reponse.data.token
    const role = reponse.data.role
    const userId = reponse.data.patientId

    if (token) {
      localStorage.setItem('token', token)
      localStorage.setItem('role', role)
      localStorage.setItem('userId', userId)

      if (role === 'MEDECIN') {
        router.push('/medecin')
      } else if (role === 'ASSISTANT_MEDICAL' || role === 'ASSISTANT') {
        router.push('/assistant')
      } else {
        erreur.value = "Votre rôle n'est pas reconnu par l'application."
      }
    }
  } catch (err) {
    if (err.response && err.response.status === 403) {
      erreur.value = "Identifiants incorrects."
    } else if (err.response && err.response.status === 404) {
      erreur.value = "Utilisateur introuvable."
    } else {
      erreur.value = "Erreur de connexion au serveur."
    }
  } finally {
    chargement.value = false
  }
}
</script>