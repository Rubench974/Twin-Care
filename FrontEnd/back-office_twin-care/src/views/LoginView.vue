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
    
    <div class="mt-8 pa-4 bg-grey-lighten-4 rounded text-caption text-center">
      Comptes de test :<br>
      <strong>assistant@twincare.fr</strong> / pass<br>
      <strong>medecin@twincare.fr</strong> / pass
    </div>
  </v-card>
</v-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

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
  await new Promise(resolve => setTimeout(resolve, 800)) // Simulation réseau

  if (email.value === 'assistant@twincare.fr' && motDePasse.value === 'pass') {
    localStorage.setItem('userRole', 'assistant')
    emit('mise-a-jour-auth')
    router.push('/assistant')
  } else if (email.value === 'medecin@twincare.fr' && motDePasse.value === 'pass') {
    localStorage.setItem('userRole', 'medecin')
    emit('mise-a-jour-auth')
    router.push('/medecin')
  } else {
    throw new Error('Identifiants incorrects.')
  }
} catch (erreur) {
  errorMessage.value = erreur.message
} finally {
  chargement.value = false
}
}
</script>