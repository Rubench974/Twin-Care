<template>
  <v-app style="background-color: #f8f9fa;">
    <v-app-bar color="#37474F" elevation="2">
      <v-icon class="ml-4" color="white" size="large">mdi-stethoscope</v-icon>
      <v-toolbar-title class="text-white font-weight-bold ml-2">Twin Care | Consultation</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-chip color="white" variant="outlined" class="mr-4">
        <v-icon start>mdi-doctor</v-icon> Espace Médecin
      </v-chip>
      <v-btn icon @click="deconnexion" color="white">
        <v-icon>mdi-logout</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <v-container fluid class="pa-6">
        <v-row class="mb-4">
          <v-col cols="12">
            <h1 class="text-h4 font-weight-bold" style="color: #37474F;">Salle d'attente virtuelle</h1>
            <p class="text-subtitle-1 text-grey-darken-1">Dossiers dont tous les documents ont été validés par l'assistant(e)</p>
          </v-col>
        </v-row>

        <div v-if="chargement" class="text-center pa-6">
          <v-progress-circular indeterminate color="#156500" size="50"></v-progress-circular>
          <p class="mt-3 text-grey">Chargement des dossiers...</p>
        </div>

        <v-alert v-else-if="dossiersPrets.length === 0" type="info" variant="tonal" class="mb-4">
          Aucun dossier prêt pour consultation pour le moment.
        </v-alert>

        <v-row v-else>
          <v-col cols="12" md="4" v-for="dossier in dossiersPrets" :key="dossier.id">
            <v-card class="rounded-xl elevation-3 border-t-lg" style="border-top: 4px solid #156500; height: 100%;">
              <v-card-title class="d-flex justify-space-between align-start pt-4 px-4 pb-0">
                <div>
                  <div class="text-h6 font-weight-bold" style="color: #37474F;">{{ dossier.patientNom }}</div>
                  <div class="text-subtitle-2 text-grey-darken-1">Dossier #{{ dossier.id }}</div>
                </div>
                <v-avatar color="green-lighten-5">
                  <v-icon color="#156500">mdi-check-circle</v-icon>
                </v-avatar>
              </v-card-title>

              <v-card-text class="px-4 py-3 mt-2">
                <v-alert type="success" variant="tonal" density="compact" class="mb-3">
                  {{ dossier.nbDocuments }} document(s) validé(s) par l'assistant(e)
                </v-alert>
                <div class="text-caption text-grey-darken-1">
                  Créé le : {{ new Date(dossier.dateCreation).toLocaleDateString('fr-FR') }}
                </div>
              </v-card-text>

              <v-divider></v-divider>

              <v-card-actions class="px-4 py-3 bg-grey-lighten-4">
                <v-spacer></v-spacer>
                <v-btn color="#156500" variant="elevated" class="text-white px-4 font-weight-bold rounded-lg"
                       prepend-icon="mdi-folder-account" @click="ouvrirConsultation(dossier)">
                  Consulter le dossier
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>

    <v-dialog v-model="dialogConsultation" max-width="900px" scrollable>
      <v-card class="rounded-xl pa-2">
        <v-card-title class="text-h5 font-weight-bold d-flex align-center mt-2" style="color: #156500;">
          <v-icon start color="#156500" class="mr-2">mdi-folder-account</v-icon>
          Dossier de {{ consultationDossier?.patientNom }}
        </v-card-title>
        <v-divider></v-divider>

        <v-card-text style="max-height: 70vh; overflow-y: auto;">
          <h3 class="text-h6 font-weight-bold mb-3 mt-2" style="color: #37474F;">
            <v-icon start color="blue" class="mr-1">mdi-chat</v-icon> Réponses du Chatbot (Anamnèse)
          </h3>

          <div v-if="chargementInteractions" class="text-center pa-4">
            <v-progress-circular indeterminate color="blue" size="30"></v-progress-circular>
          </div>
          <v-alert v-else-if="interactions.length === 0" type="info" variant="tonal" density="compact" class="mb-4">
            Aucune réponse chatbot enregistrée pour ce patient.
          </v-alert>
          <v-table v-else density="compact" class="mb-6 rounded-lg">
            <thead class="bg-blue-lighten-5">
              <tr>
                <th>Catégorie</th>
                <th>Question</th>
                <th>Réponse</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="inter in interactions" :key="inter.id">
                <td><v-chip size="x-small" color="blue-lighten-4">{{ inter.categorie }}</v-chip></td>
                <td>{{ inter.question }}</td>
                <td class="font-weight-bold">{{ inter.reponseTexte || inter.reponseNumerique || '-' }}</td>
                <td class="text-caption">{{ new Date(inter.dateInteraction).toLocaleDateString('fr-FR') }}</td>
              </tr>
            </tbody>
          </v-table>

          <h3 class="text-h6 font-weight-bold mb-3" style="color: #37474F;">
            <v-icon start color="orange" class="mr-1">mdi-file-document-multiple</v-icon> Documents validés
          </h3>

          <div v-if="chargementDocs" class="text-center pa-4">
            <v-progress-circular indeterminate color="orange" size="30"></v-progress-circular>
          </div>
          <v-alert v-else-if="documentsValides.length === 0" type="info" variant="tonal" density="compact">
            Aucun document validé trouvé.
          </v-alert>

          <div v-else>
            <v-card v-for="doc in documentsValides" :key="doc.id" class="mb-4 rounded-lg elevation-1" variant="outlined">
              <v-card-title class="d-flex align-center py-2">
                <v-icon color="orange-darken-2" class="mr-2">mdi-file-document</v-icon>
                <span class="font-weight-bold">{{ doc.type }}</span>
                <v-chip size="x-small" color="green" class="ml-2">VALIDÉ</v-chip>
                <v-spacer></v-spacer>
                <span class="text-caption text-grey">{{ doc.dateDocument }}</span>
              </v-card-title>
              <v-divider></v-divider>
              <v-card-text class="pa-4">
                <div v-if="estImage(doc.nomFichier)" class="text-center">
                  <v-img :src="urlFichier(doc.nomFichier)" max-height="400" contain class="rounded-lg border"></v-img>
                </div>
                <div v-else class="d-flex align-center justify-center pa-4">
                  <v-icon size="40" color="red" class="mr-3">mdi-file-pdf-box</v-icon>
                  <div>
                    <p class="font-weight-bold mb-1">{{ doc.nomFichier }}</p>
                    <v-btn size="small" color="blue" variant="tonal" :href="urlFichier(doc.nomFichier)" target="_blank" prepend-icon="mdi-download">
                      Télécharger / Ouvrir
                    </v-btn>
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </div>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="grey-darken-1" variant="text" @click="dialogConsultation = false">Fermer</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script setup>
import { ref, onMounted } from 'vue'
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

const estImage = (nomFichier) => {
  if (!nomFichier) return false
  const ext = nomFichier.toLowerCase()
  return ext.endsWith('.jpg') || ext.endsWith('.jpeg') || ext.endsWith('.png') || ext.endsWith('.gif') || ext.endsWith('.webp')
}

const urlFichier = (nomFichier) => {
  return `${BASE_URL}/api/files/${nomFichier}`
}

const dossiersPrets = ref([])
const chargement = ref(false)

const chargerDossiers = async () => {
  chargement.value = true
  try {
    const reponse = await api.get('/api/dossiers')
    const tousDossiers = reponse.data

    const dossiersAvecPatient = await Promise.all(
      tousDossiers.map(async (dossier) => {
        let nbDocuments = 0
        let patientNom = 'Patient inconnu'

        // Récupérer le nom du patient depuis le dossier lui-même
        if (dossier.patient) {
          patientNom = `${dossier.patient.nom} ${dossier.patient.prenom}`
        }

        try {
          const docsReponse = await api.get(`/api/documents/dossier/${dossier.id}`)
          const docsValides = docsReponse.data.filter(d => d.statut === 'VALIDE')
          nbDocuments = docsValides.length
          if (nbDocuments === 0) return null
        } catch (e) {
          return null
        }

        return {
          ...dossier,
          patientNom,
          nbDocuments
        }
      })
    )

    dossiersPrets.value = dossiersAvecPatient.filter(d => d !== null)
  } catch (erreur) {
    console.error('Erreur chargement dossiers:', erreur)
  } finally {
    chargement.value = false
  }
}

onMounted(() => {
  chargerDossiers()
})

const dialogConsultation = ref(false)
const consultationDossier = ref(null)
const interactions = ref([])
const documentsValides = ref([])
const chargementInteractions = ref(false)
const chargementDocs = ref(false)

const ouvrirConsultation = async (dossier) => {
  consultationDossier.value = dossier
  dialogConsultation.value = true
  interactions.value = []
  documentsValides.value = []

  chargementInteractions.value = true
  try {
    const reponse = await api.get(`/api/interactions/dossier/${dossier.id}`)
    interactions.value = reponse.data
  } catch (e) {
    console.error('Erreur chargement interactions:', e)
  } finally {
    chargementInteractions.value = false
  }

  chargementDocs.value = true
  try {
    const reponse = await api.get(`/api/documents/dossier/${dossier.id}`)
    documentsValides.value = reponse.data.filter(d => d.statut === 'VALIDE')
  } catch (e) {
    console.error('Erreur chargement documents:', e)
  } finally {
    chargementDocs.value = false
  }
}
</script>