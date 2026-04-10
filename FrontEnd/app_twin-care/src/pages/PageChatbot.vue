<template>
  <v-container fluid class="pa-0 d-flex flex-column align-center" style="background: linear-gradient(180deg, #cae8fa 0%, #f4f9fd 40%, #f4f9fd 100%); min-height: 100vh; padding-bottom: 100px !important;">

    <div class="d-flex align-center w-100 px-4 mt-8 mb-6" style="max-width: 450px;">
      <v-btn icon="mdi-menu" variant="text" color="#37474F" size="large" @click="toggleDrawer"></v-btn>
      <div class="flex-grow-1"></div>
    </div>

    <div class="avatar-container mt-4 mb-10">
      <div class="pulse-ring pulse-ring-1"></div>
      <div class="pulse-ring pulse-ring-2"></div>
      <div class="avatar-center"></div>
    </div>

    <div class="px-6 text-center mb-10" style="max-width: 350px;">
      <h2 class="text-h6 font-weight-bold" style="color: #2c3e50; line-height: 1.4; font-size: 1.15rem !important;">
        {{ questionActuelle }}
      </h2>
    </div>

    <v-row v-if="questionId" class="w-100 px-6" style="max-width: 380px;" justify="center">
      <v-col cols="6" class="pr-2">
        <v-btn @click="envoyerReponse('OUI')" variant="outlined" block rounded="xl" size="large" color="#2c3e50" class="font-weight-bold bg-white text-none" style="border-width: 1.5px; height: 50px;">
          Oui
        </v-btn>
      </v-col>
      <v-col cols="6" class="pl-2">
        <v-btn @click="envoyerReponse('NON')" variant="outlined" block rounded="xl" size="large" color="#2c3e50" class="font-weight-bold bg-white text-none" style="border-width: 1.5px; height: 50px;">
          Non
        </v-btn>
      </v-col>
    </v-row>

  </v-container>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue'

const toggleDrawer = inject('toggleDrawer')
const questionActuelle = ref("Chargement de la question...")
const questionId = ref(null) 
const questionComplete = ref(null) 
const url = "https://twincare-t2xu.onrender.com/api/chatbot" 

function getQuestion() {
  const token = localStorage.getItem('token')
  const patientId = localStorage.getItem('patientId')

  if (!patientId) {
    questionActuelle.value = "Erreur : Veuillez vous reconnecter."
    return
  }

  const myHeaders = new Headers()
  myHeaders.append("Authorization", "Bearer " + token)

  fetch(`${url}/session/patient/${patientId}?limit=1`, { method: "GET", headers: myHeaders })
    .then(response => {
      if (response.status === 204) return null
      if (!response.ok) throw new Error("Erreur serveur")
      return response.json()
    })
    .then(data => {
      let q = null
      if (data && data.questions && data.questions.length > 0) {
        q = data.questions[0]
      }

      if (q) {
        questionActuelle.value = q.question
        questionId.value = q.questionId
        questionComplete.value = q 
      } else {
        questionActuelle.value = "Toutes les questions ont été traitées. Merci !"
        questionId.value = null
      }
    })
    .catch(err => {
      console.error(err)
      questionActuelle.value = "Erreur de connexion au serveur."
    })
}

function envoyerReponse(choix) {
  if (!questionId.value || !questionComplete.value) return

  const token = localStorage.getItem('token')
  const patientId = localStorage.getItem('patientId')
  const dossierId = localStorage.getItem('dossierId') 
  
  const myHeaders = new Headers()
  myHeaders.append("Content-Type", "application/json")
  myHeaders.append("Authorization", "Bearer " + token)

  const payload = {
    questionId: questionComplete.value.questionId,
    question: questionComplete.value.question,
    categorie: questionComplete.value.categorie,
    typeReponse: questionComplete.value.typeReponse,
    reponseTexte: choix,
    reponseNumerique: null
  }

  fetch(`${url}/session/patient/${patientId}/dossier/${dossierId}/answer`, { 
    method: "POST", 
    headers: myHeaders, 
    body: JSON.stringify(payload) 
  })
    .then(response => {
      if (response.ok) {
        getQuestion()
      } else {
        console.error("Le serveur a refusé la réponse. Statut :", response.status)
      }
    })
    .catch(err => console.error("Erreur réseau :", err))
}

onMounted(() => {
  getQuestion()
})
</script>

<style scoped>
.avatar-container {
  position: relative;
  width: 250px;
  height: 250px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.avatar-center {
  position: relative;
  width: 160px;
  height: 160px;
  background: linear-gradient(135deg, #7db9e8 0%, #2A93D5 100%);
  border-radius: 50%;
  z-index: 10;
  box-shadow: 0px 6px 20px rgba(42, 147, 213, 0.4); 
}
.pulse-ring {
  position: absolute;
  border-radius: 50%;
  border: 2px solid rgba(255, 255, 255, 0.9); 
  animation: pulseAnim 3s cubic-bezier(0.215, 0.61, 0.355, 1) infinite;
}
.pulse-ring-1 {
  width: 160px;
  height: 160px;
  animation-delay: 0s;
}
.pulse-ring-2 {
  width: 160px;
  height: 160px;
  animation-delay: 1.5s;
}
@keyframes pulseAnim {
  0% { transform: scale(1); opacity: 0.8; }
  100% { transform: scale(2.2); opacity: 0; }
}
</style>