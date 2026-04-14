<template>
  <v-app style="background-color: #E3F2FD;">
    <v-app-bar color="#156500" elevation="2">
      <v-icon class="ml-4" color="white" size="large">mdi-heart-pulse</v-icon>
      <v-toolbar-title class="text-white font-weight-bold ml-2">Twin Care | Accueil</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-chip color="white" variant="outlined" class="mr-4">
        <v-icon start>mdi-account-tie</v-icon> Espace Assistant(e)
      </v-chip>
      <v-btn icon @click="deconnexion" color="white">
        <v-icon>mdi-logout</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <v-container fluid class="pa-6">
        <v-row class="mb-6 align-center">
          <v-col cols="12" md="5">
            <h1 class="text-h4 font-weight-bold" style="color: #37474F;">Tableau de Bord</h1>
            <p class="text-subtitle-1 text-grey-darken-2">Gestion des flux et des documents</p>
          </v-col>
          <v-col cols="12" md="4">
            <v-text-field v-model="recherche" label="Rechercher un patient..." prepend-inner-icon="mdi-magnify" variant="outlined" density="compact" clearable hide-details></v-text-field>
          </v-col>
          <v-col cols="12" md="3" class="text-right">
            <v-btn size="large" color="#156500" class="text-white font-weight-bold" prepend-icon="mdi-account-plus" @click="dialogPatient = true">
              Nouveau Patient
            </v-btn>
          </v-col>
        </v-row>

        <v-card class="rounded-xl elevation-3">
          <v-card-title class="bg-white pa-4 font-weight-bold d-flex align-center" style="color: #37474F;">
            <v-icon start color="#156500" class="mr-2">mdi-clipboard-list</v-icon>
            File Active du Cabinet (Dossiers EN_COURS)
          </v-card-title>
          <v-divider></v-divider>
          
          <div v-if="chargementTableau" class="text-center pa-4">
            <v-progress-circular indeterminate color="#156500"></v-progress-circular>
            <p class="mt-2 text-grey">Chargement des patients...</p>
          </div>

          <v-table v-else>
            <thead class="bg-grey-lighten-4">
              <tr>
                <th class="font-weight-bold">Patient</th>
                <th class="font-weight-bold">Email</th>
                <th class="font-weight-bold">ID Système</th>
                <th class="text-right font-weight-bold">Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr v-if="patientsFiltres.length === 0">
                <td colspan="4" class="text-center py-4 text-grey">Aucun patient trouvé.</td>
              </tr>
              <tr v-for="patient in patientsFiltres" :key="patient.id" class="hover-row">
                <td class="font-weight-medium py-3">{{ patient.nom }} {{ patient.prenom }}</td>
                <td>{{ patient.email }}</td>
                <td><v-chip size="small">ID: {{ patient.id }}</v-chip></td>
                <td class="text-right">
                  <v-btn size="small" color="#156500" class="text-white mr-2" prepend-icon="mdi-folder-open" @click="ouvrirDossier(patient)">
                    Gérer Dossier
                  </v-btn>
                  <v-btn size="small" color="red" variant="tonal" prepend-icon="mdi-account-remove" @click="supprimerPatient(patient)">
                    Supprimer
                  </v-btn>
                </td>
              </tr>
            </tbody>
          </v-table>
        </v-card>
      </v-container>
    </v-main>

    <v-dialog v-model="dialogPatient" max-width="600px">
      <v-card class="rounded-xl pa-2">
        <v-card-title class="text-h5 font-weight-bold d-flex align-center mt-2" style="color: #156500;">
          <v-icon start color="#156500" class="mr-2">mdi-account-plus</v-icon>
          Inscrire un nouveau patient
        </v-card-title>
        <v-card-text>
          <v-alert v-if="msgCreation" :type="typeMsg" class="mb-4" closable @click:close="msgCreation = ''">
            {{ msgCreation }}
          </v-alert>
          <v-form @submit.prevent="creerPatient" :disabled="chargementPatient">
            <v-row>
              <v-col cols="12" md="6"><v-text-field v-model="nouveau.nom" label="Nom" variant="outlined" color="#156500" required></v-text-field></v-col>
              <v-col cols="12" md="6"><v-text-field v-model="nouveau.prenom" label="Prénom" variant="outlined" color="#156500" required></v-text-field></v-col>
              <v-col cols="12" md="6"><v-text-field v-model="nouveau.email" label="Email" type="email" variant="outlined" color="#156500" required></v-text-field></v-col>
              <v-col cols="12" md="6"><v-select v-model="nouveau.sexe" :items="['HOMME', 'FEMME']" label="Sexe" variant="outlined" color="#156500" required></v-select></v-col>
              <v-col cols="12" md="6"><v-text-field v-model="nouveau.dateNaissance" label="Date de naissance" type="date" variant="outlined" color="#156500" required></v-text-field></v-col>
              <v-col cols="12" md="6"><v-text-field v-model="nouveau.motDePasse" label="Mot de passe provisoire" type="password" variant="outlined" color="#156500" required></v-text-field></v-col>
            </v-row>
            <div class="d-flex justify-end mt-4">
              <v-btn variant="text" color="grey-darken-1" @click="dialogPatient = false" class="mr-3">Annuler</v-btn>
              <v-btn type="submit" color="#156500" class="text-white" :loading="chargementPatient">Créer le dossier</v-btn>
            </div>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>

    <v-dialog v-model="dialogDossier" max-width="900px" scrollable>
      <v-card class="rounded-xl pa-2">
        <v-card-title class="text-h5 font-weight-bold d-flex align-center mt-2" style="color: #156500;">
          <v-icon start color="#156500" class="mr-2">mdi-file-document-multiple</v-icon>
          Documents de {{ patientSelectionne?.nom }} {{ patientSelectionne?.prenom }}
        </v-card-title>
        <v-divider></v-divider>
        
        <v-card-text style="max-height: 70vh; overflow-y: auto;">
          <div v-if="chargementDocs" class="text-center pa-6">
            <v-progress-circular indeterminate color="#156500"></v-progress-circular>
            <p class="mt-2">Recherche des documents sur le serveur...</p>
          </div>

          <v-alert v-else-if="documents.length === 0" type="info" variant="tonal">
            Aucun document trouvé pour ce patient.
          </v-alert>

          <div v-else>
            <v-card v-for="doc in documents" :key="doc.id" class="mb-4 rounded-lg elevation-1" variant="outlined">
              <v-card-title class="d-flex align-center py-2">
                <v-icon color="blue" class="mr-2">mdi-file-document</v-icon>
                <span class="font-weight-bold">{{ doc.type || 'Non spécifié' }}</span>
                <v-chip size="x-small" :color="couleurStatut(doc.statut)" class="ml-2">{{ doc.statut }}</v-chip>
                <v-spacer></v-spacer>
                <span class="text-caption text-grey">{{ doc.dateDocument }}</span>
              </v-card-title>
              <v-divider></v-divider>
              
              <v-card-text class="pa-4" v-if="doc.cheminFichier">
                <div v-if="estImage(doc.cheminFichier)" class="text-center mb-3">
                  <v-img :src="urlFichier(doc.cheminFichier)" max-height="300" contain class="rounded-lg border"></v-img>
                </div>
                <div v-else class="d-flex align-center mb-3">
                  <v-icon size="30" color="red" class="mr-2">mdi-file-pdf-box</v-icon>
                  <span>{{ doc.nomFichier }}</span>
                  <v-btn size="small" color="blue" variant="tonal" class="ml-3" :href="urlFichier(doc.cheminFichier)" target="_blank" prepend-icon="mdi-eye">
                    Ouvrir
                  </v-btn>
                </div>
              </v-card-text>

              <v-card-actions class="px-4 pb-3">
                <template v-if="doc.statut === 'EN_ATTENTE'">
                  <v-btn size="small" color="success" prepend-icon="mdi-check" @click="validerDocument(doc.id, 'VALIDER')">Valider</v-btn>
                  <v-btn size="small" color="error" prepend-icon="mdi-close" @click="validerDocument(doc.id, 'REFUSER')">Refuser</v-btn>
                </template>
                <v-spacer></v-spacer>
                <v-btn size="small" color="grey-darken-1" variant="tonal" prepend-icon="mdi-delete" @click="supprimerDocument(doc.id)">Supprimer</v-btn>
              </v-card-actions>
            </v-card>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="grey-darken-1" variant="text" @click="dialogDossier = false">Fermer</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-app>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api'

const router = useRouter()
const BASE_URL = 'https://twincare-t2xu.onrender.com'

const deconnexion = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('role')
  localStorage.removeItem('userId')
  router.push('/login')
}

const estImage = (cheminFichier) => {
  if (!cheminFichier) return false
  const ext = cheminFichier.toLowerCase()
  return ext.endsWith('.jpg') || ext.endsWith('.jpeg') || ext.endsWith('.png') || ext.endsWith('.gif') || ext.endsWith('.webp')
}

const urlFichier = (cheminFichier) => {
  if (!cheminFichier) return ''
  const filename = cheminFichier.split('/').pop()
  return `${BASE_URL}/api/files/${filename}`
}

const recherche = ref('')
const patients = ref([])
const chargementTableau = ref(false)

const patientsFiltres = computed(() => {
  if (!recherche.value) return patients.value
  const q = recherche.value.toLowerCase()
  return patients.value.filter(p =>
    `${p.nom} ${p.prenom}`.toLowerCase().includes(q) || p.email.toLowerCase().includes(q)
  )
})

const chargerPatients = async () => {
  chargementTableau.value = true
  try {
    const reponse = await api.get('/api/users')
    patients.value = reponse.data.filter(u => u.role === 'PATIENT')
  } catch (erreur) {
    console.error("Erreur de récupération des patients.", erreur)
    patients.value = []
  } finally {
    chargementTableau.value = false
  }
}

onMounted(() => {
  chargerPatients()
})

const dialogPatient = ref(false)
const chargementPatient = ref(false)
const msgCreation = ref('')
const typeMsg = ref('success')
const nouveau = ref({ nom: '', prenom: '', dateNaissance: '', email: '', motDePasse: '', role: 'PATIENT', sexe: null })

const creerPatient = async () => {
  chargementPatient.value = true
  msgCreation.value = ''
  try {
    await api.post('/api/auth/register', nouveau.value)
    typeMsg.value = 'success'
    msgCreation.value = "Patient créé avec succès dans la base !"
    await chargerPatients()
    nouveau.value = { nom: '', prenom: '', dateNaissance: '', email: '', motDePasse: '', role: 'PATIENT', sexe: null }
    setTimeout(() => { dialogPatient.value = false; msgCreation.value = '' }, 2000)
  } catch (erreur) {
    typeMsg.value = 'error'
    msgCreation.value = "Erreur de création. L'email existe-t-il déjà ?"
  } finally {
    chargementPatient.value = false
  }
}

const supprimerPatient = async (patient) => {
  if (!confirm(`Voulez-vous vraiment supprimer le patient ${patient.nom} ${patient.prenom} ? Cette action est irréversible.`)) return
  try {
    await api.delete(`/api/users/${patient.id}`)
    await chargerPatients()
  } catch (erreur) {
    console.error("Erreur suppression patient:", erreur)
    alert("Erreur lors de la suppression du patient.")
  }
}

const dialogDossier = ref(false)
const patientSelectionne = ref(null)
const documents = ref([])
const chargementDocs = ref(false)
const dossierId = ref(null)

const couleurStatut = (statut) => {
  if (statut === 'VALIDE') return 'green'
  if (statut === 'REFUSE') return 'red'
  return 'orange'
}

const ouvrirDossier = async (patient) => {
  patientSelectionne.value = patient
  dialogDossier.value = true
  chargementDocs.value = true
  documents.value = []

  try {
    const dossierReponse = await api.get(`/api/dossiers/patient/${patient.id}`)
    dossierId.value = dossierReponse.data.id

    const reponse = await api.get(`/api/documents/dossier/${dossierId.value}`)
    documents.value = reponse.data
  } catch (erreur) {
    console.error("Erreur récupération documents:", erreur)
  } finally {
    chargementDocs.value = false
  }
}

const validerDocument = async (documentId, decision) => {
  try {
    const assistantId = localStorage.getItem('userId')
    await api.post(`/api/validations/document/${documentId}`, {
      decision: decision,
      commentaire: "Traité par l'assistant(e)",
      assistantMedicalId: Number(assistantId)
    })
    const reponse = await api.get(`/api/documents/dossier/${dossierId.value}`)
    documents.value = reponse.data
  } catch (erreur) {
    console.error("Erreur de validation:", erreur)
    alert("Erreur lors de la validation.")
  }
}

const supprimerDocument = async (documentId) => {
  if (!confirm("Voulez-vous vraiment supprimer ce document ?")) return
  try {
    await api.delete(`/api/documents/${documentId}`)
    const reponse = await api.get(`/api/documents/dossier/${dossierId.value}`)
    documents.value = reponse.data
  } catch (erreur) {
    console.error("Erreur suppression:", erreur)
    alert("Erreur lors de la suppression.")
  }
}
</script>

<style scoped>
.hover-row:hover {
  background-color: #f5f5f5;
  transition: background-color 0.2s;
}
</style>