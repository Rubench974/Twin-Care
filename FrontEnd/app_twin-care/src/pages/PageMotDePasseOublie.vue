<template>
  <v-container fluid class="fill-height pa-0" style="background: linear-gradient(180deg, #cae8fa 0%, #f4f9fd 100%);">
    
    <v-btn icon="mdi-chevron-left" variant="text" color="#2A93D5" size="large" style="position: absolute; top: 20px; left: 10px;" to="/"></v-btn>

    <v-row justify="center" align="center" class="w-100 ma-0">
      <v-col cols="10" sm="8" md="6" class="text-center">
        <h1 class="font-weight-black mb-8" style="color: #2A93D5; font-size: 2.2rem; line-height: 1.2;">
          Mot de passe<br>oublié
        </h1>

        <v-form @submit.prevent="reinitialiser">
          
          <div class="text-left mb-1 ml-1 font-weight-medium" style="color: #37474F; font-size: 0.85rem;">
            Adresse mail
          </div>
          <v-text-field
            v-model="email"
            placeholder="ex: luc@email.com"
            variant="solo"
            bg-color="white"
            rounded="lg"
            elevation="0"
            density="comfortable"
            class="mb-4"
            hide-details
            style="border: 1px solid #E0E0E0; border-radius: 8px;"
          ></v-text-field>

          <div class="text-left mb-1 ml-1 font-weight-medium" style="color: #37474F; font-size: 0.85rem;">
            Nouveau mot de passe
          </div>
          <v-text-field
            v-model="nouveauMotDePasse"
            type="password"
            placeholder="Votre nouveau mot de passe"
            variant="solo"
            bg-color="white"
            rounded="lg"
            elevation="0"
            density="comfortable"
            class="mb-6"
            hide-details
            style="border: 1px solid #E0E0E0; border-radius: 8px;"
          ></v-text-field>

          <v-alert v-if="message" :type="typeMessage" variant="tonal" class="mb-4 text-caption rounded-lg" density="compact">
            {{ message }}
          </v-alert>

          <v-btn
            type="submit"
            :loading="chargement"
            color="#2A93D5"
            block
            rounded="lg"
            size="large"
            class="text-white font-weight-bold text-none mt-2"
            elevation="2"
          >
            Réinitialiser
          </v-btn>

        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'

const email = ref('')
const nouveauMotDePasse = ref('')
const chargement = ref(false)
const message = ref('')
const typeMessage = ref('error')

function reinitialiser() {
  if (!email.value || !nouveauMotDePasse.value) {
    message.value = "Veuillez remplir tous les champs.";
    typeMessage.value = "error";
    return;
  }

  chargement.value = true;
  message.value = "";

  const url = "https://twincare-t2xu.onrender.com/api/auth/reset-password";

  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email: email.value, nouveauMotDePasse: nouveauMotDePasse.value })
  })
  .then((response) => {
    if (!response.ok) throw new Error("Erreur");
    return response.json();
  })
  .then((data) => {
    message.value = "Mot de passe mis à jour avec succès !";
    typeMessage.value = "success";
    email.value = "";
    nouveauMotDePasse.value = "";
    chargement.value = false;
  })
  .catch((error) => {
    message.value = "Utilisateur introuvable ou erreur réseau.";
    typeMessage.value = "error";
    chargement.value = false;
  });
}
</script>