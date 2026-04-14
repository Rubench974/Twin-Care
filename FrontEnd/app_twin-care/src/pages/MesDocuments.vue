<template>
  <v-container fluid class="pa-0 d-flex flex-column align-center" style="background: linear-gradient(180deg, #cae8fa 0%, #f4f9fd 40%, #f4f9fd 100%); min-height: 100vh; padding-bottom: 100px !important;">
    
    <div class="d-flex align-center w-100 px-4 mt-8 mb-6" style="max-width: 450px;">
      <v-btn icon="mdi-arrow-left" variant="text" color="#2c3e50" size="large" @click="$router.go(-1)"></v-btn>
      <h2 class="text-center flex-grow-1 font-weight-black" style="color: #2c3e50; font-size: 1.25rem; letter-spacing: 0.5px;">
        MES DOCUMENTS
      </h2>
      <div style="width: 48px;"></div>
    </div>

    <div class="w-100 px-5 mb-4" style="max-width: 400px;">
      
      <div v-if="isLoading" class="text-center mt-10">
        <v-progress-circular indeterminate color="#2A93D5" size="50"></v-progress-circular>
        <p class="mt-4 font-weight-medium" style="color: #37474F;">Chargement de vos documents...</p>
      </div>

      <template v-else>
        <v-card v-for="doc in documents" :key="doc.id" class="mb-4 rounded-xl" elevation="2" style="border: 1px solid rgba(42, 147, 213, 0.2);">
          <v-list-item class="pa-4">
            <template v-slot:prepend>
              <v-avatar color="#e1f0fa" rounded="lg" size="50">
                <v-icon color="#2A93D5" size="28">{{ getIconForType(doc.type) }}</v-icon>
              </v-avatar>
            </template>
            
            <v-list-item-title class="font-weight-bold text-body-1 mb-1" style="color: #2c3e50;">
              {{ formatType(doc.type) }}
            </v-list-item-title>
            <v-list-item-subtitle style="font-size: 0.85rem; color: #546E7A;">
              {{ doc.nomFichier }}
            </v-list-item-subtitle>
            <v-list-item-subtitle class="font-weight-medium mt-1" style="font-size: 0.75rem; color: #2A93D5;">
              Envoyé le : {{ formaterDate(doc.dateDocument) }}
            </v-list-item-subtitle>

            <template v-slot:append>
              <v-btn icon="mdi-eye-outline" variant="text" color="#2A93D5" class="mr-2" @click="ouvrirDocument(doc)"></v-btn>
              <v-btn icon="mdi-delete-outline" variant="text" color="#E53935" @click="confirmerSuppression(doc.id)"></v-btn>
            </template>
          </v-list-item>
        </v-card>

        <div v-if="documents.length === 0" class="d-flex flex-column align-center justify-center mt-16 text-center">
          <v-avatar color="rgba(255,255,255,0.6)" size="100" class="mb-4">
            <v-icon size="50" color="#90A4AE">mdi-folder-open-outline</v-icon>
          </v-avatar>
          <span class="text-body-1 font-weight-medium" style="color: #546E7A;">
            Aucun document n'a été envoyé.
          </span>
          <v-btn to="/documents" variant="text" color="#2A93D5" class="mt-4 font-weight-bold text-none">
            Envoyer un document
          </v-btn>
        </div>
      </template>

    </div>

    <v-dialog v-model="dialogSuppression" max-width="320">
      <v-card rounded="xl" class="text-center pa-4">
        <v-icon size="50" color="#E53935" class="mx-auto mt-2 mb-2">mdi-alert-circle-outline</v-icon>
        <h3 class="text-h6 font-weight-bold mb-2" style="color: #2c3e50;">Supprimer ?</h3>
        <p class="text-body-2 mb-6" style="color: #546E7A;">Voulez-vous vraiment supprimer ce document ? Cette action est irréversible.</p>
        <div class="d-flex justify-space-between w-100">
          <v-btn variant="text" color="#546E7A" class="text-none font-weight-bold" @click="dialogSuppression = false">Annuler</v-btn>
          <v-btn color="#E53935" variant="flat" rounded="xl" class="text-none font-weight-bold" @click="executerSuppression" :loading="isDeleting">Supprimer</v-btn>
        </div>
      </v-card>
    </v-dialog>

    <v-snackbar v-model="showSnackbar" :color="snackbarColor" elevation="6" rounded="pill" timeout="3000" location="bottom" class="mb-16">
      <div class="d-flex align-center w-100 px-1">
        <v-icon :icon="snackbarIcon" color="white" class="mr-3" size="24"></v-icon>
        <span class="text-body-2 text-white font-weight-medium">{{ snackbarText }}</span>
        <v-spacer></v-spacer>
        <v-btn variant="tonal" color="white" size="small" class="text-none font-weight-bold rounded-pill ml-3" @click="showSnackbar = false">
          Fermer
        </v-btn>
      </div>
    </v-snackbar>

  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const BASE_URL = "https://twincare-t2xu.onrender.com"
const url = `${BASE_URL}/api/documents`

const documents = ref([])
const isLoading = ref(true)

const showSnackbar = ref(false)
const snackbarText = ref("")
const snackbarColor = ref("#2c3e50")
const snackbarIcon = ref("mdi-check-circle")

const dialogSuppression = ref(false)
const docASupprimer = ref(null)
const isDeleting = ref(false)

function afficherNotification(message, couleur = "#2c3e50", icone = "mdi-check-circle") {
  snackbarText.value = message
  snackbarColor.value = couleur
  snackbarIcon.value = icone
  showSnackbar.value = true
}

function chargerDocuments() {
  isLoading.value = true
  const token = localStorage.getItem('token')
  const dossierId = localStorage.getItem('dossierId')

  if (!dossierId) {
    isLoading.value = false
    return
  }

  fetch(`${url}/dossier/${dossierId}`, {
    method: "GET",
    headers: { "Authorization": "Bearer " + token }
  })
  .then(res => res.json())
  .then(data => {
    documents.value = data
  })
  .catch(err => {
    console.error("Erreur chargement documents :", err)
    afficherNotification("Erreur de connexion.", "#E53935", "mdi-wifi-strength-1-alert")
  })
  .finally(() => {
    isLoading.value = false
  })
}

function ouvrirDocument(doc) {
  if (!doc.cheminFichier) {
    afficherNotification("Aucun fichier associé à ce document.", "#E53935", "mdi-alert-circle")
    return
  }
  const filename = doc.cheminFichier.split('/').pop()
  const fileUrl = `${BASE_URL}/api/files/${filename}`
  window.open(fileUrl, '_blank')
}

function formaterDate(dateString) {
  if (!dateString) return ""
  const date = new Date(dateString)
  return date.toLocaleDateString('fr-FR')
}

function formatType(type) {
  const types = {
    'ORDONNANCE': 'Ordonnance',
    'BIOLOGIE': 'Résultats Biologie',
    'IMAGERIE': 'Imagerie médicale',
    'COURRIER': 'Courrier médical',
    'CERTIFICAT': 'Certificat',
    'CERFA': 'Formulaire CERFA',
    'AUTRE': 'Autre document'
  }
  return types[type] || type
}

function getIconForType(type) {
  const icons = {
    'ORDONNANCE': 'mdi-pill',
    'BIOLOGIE': 'mdi-flask-outline',
    'IMAGERIE': 'mdi-radiology-box-outline',
    'COURRIER': 'mdi-email-outline',
    'CERTIFICAT': 'mdi-certificate-outline',
    'CERFA': 'mdi-file-document-edit-outline',
    'AUTRE': 'mdi-file-document-outline'
  }
  return icons[type] || 'mdi-file-document-outline'
}

function confirmerSuppression(id) {
  docASupprimer.value = id
  dialogSuppression.value = true
}

function executerSuppression() {
  if (!docASupprimer.value) return
  isDeleting.value = true
  
  const token = localStorage.getItem('token')
  
  fetch(`${url}/${docASupprimer.value}`, {
    method: "DELETE",
    headers: { "Authorization": "Bearer " + token }
  })
  .then(response => {
    if (response.ok) {
      afficherNotification("Document supprimé.", "#4CAF50", "mdi-delete-empty")
      documents.value = documents.value.filter(doc => doc.id !== docASupprimer.value)
    } else if (response.status === 404 || response.status === 405) {
      afficherNotification("La suppression arrive bientôt !", "#FF9800", "mdi-tools")
    } else {
      afficherNotification("Erreur lors de la suppression.", "#E53935", "mdi-alert-circle")
    }
  })
  .catch(() => afficherNotification("Erreur réseau.", "#E53935", "mdi-wifi-strength-1-alert"))
  .finally(() => {
    isDeleting.value = false
    dialogSuppression.value = false
  })
}

onMounted(() => {
  chargerDocuments()
})
</script>