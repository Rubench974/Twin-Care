<template>
  <v-container fluid class="fill-height pa-0" style="background: linear-gradient(180deg, #cae8fa 0%, #f4f9fd 100%);">
    <v-row justify="center" align="center" class="w-100 ma-0">
      <v-col cols="10" sm="8" md="6" class="text-center">
        
        <h1 class="font-weight-black mb-12" style="color: #2A93D5; font-size: 2.5rem; letter-spacing: 1px;">
          Twin Care
        </h1>

        <!-- Enveloppe le tout dans un formulaire connecté à ta fonction -->
        <v-form @submit.prevent="seConnecter">
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
            Mot de passe
          </div>
          <v-text-field
            v-model="motDePasse"
            type="password"
            variant="solo"
            bg-color="white"
            rounded="lg"
            elevation="0"
            density="comfortable"
            hide-details
            style="border: 1px solid #E0E0E0; border-radius: 8px;"
          ></v-text-field>
          
          <div class="text-right mt-1 mb-6">
            <router-link to="/mot-de-passe-oublie" class="text-caption font-weight-bold" style="color: #37474F; text-decoration: underline;">
              Mot de passe oublié ?
            </router-link>
          </div>

          <!-- Affichage du message d'erreur si le fetch échoue -->
          <v-alert v-if="erreurAffichee" type="error" variant="tonal" class="mb-4 text-caption rounded-lg" density="compact">
            Identifiants incorrects ou serveur injoignable.
          </v-alert>

          <!-- Le bouton déclenche le 'submit' du formulaire au lieu d'un simple lien 'to=' -->
          <v-btn
            type="submit"
            :loading="chargement"
            color="#2A93D5"
            block
            rounded="lg"
            size="large"
            class="text-white font-weight-bold text-none"
            elevation="2"
          >
            Se connecter
          </v-btn>
        </v-form>
        
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const email = ref('')
const motDePasse = ref('')
const chargement = ref(false)
const erreurAffichee = ref(false)

function seConnecter() {
  chargement.value = true;
  erreurAffichee.value = false;

  const url = "https://twincare-t2xu.onrender.com/api/auth/login";

  let myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");

  const fetchOptions = {
    method: "POST",
    headers: myHeaders,
    body: JSON.stringify({ email: email.value, motDePasse: motDePasse.value })
  };

  fetch(url, fetchOptions)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Identifiants incorrects");
      }
      return response.json();
    })
    .then((dataJSON) => {
      console.log(dataJSON); 
      chargement.value = false;
      
      router.push('/documents');
    })
    .catch((error) => {
      console.log(error); 
      erreurAffichee.value = true;
      chargement.value = false;
    });
}
</script>