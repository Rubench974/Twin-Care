import { createRouter, createWebHistory } from 'vue-router'

import PageConnexion from '../pages/PageConnexion.vue'
import PageMotDePasseOublie from '../pages/PageMotDePasseOublie.vue'
import PageDocuments from '../pages/PageDocuments.vue'
import PageParcours from '../pages/PageParcours.vue'
import PageChatbot from '../pages/PageChatbot.vue'
import PageEnvironnement from '../pages/PageEnvironnement.vue'
import MesDocuments from '../pages/MesDocuments.vue'
import PageProfil from '../pages/PageProfil.vue'
import PageConfidentialite from '../pages/PageConfidentialite.vue'

const routes = [
  { path: '/', component: PageConnexion },
  { path: '/mot-de-passe-oublie', component: PageMotDePasseOublie },
  { path: '/documents', component: PageDocuments },
  { path: '/parcours', component: PageParcours },
  { path: '/chatbot', component: PageChatbot },
  { path: '/environnement', component: PageEnvironnement },
  { path: '/mes-documents', component: MesDocuments },
  { path: '/profil', component: PageProfil },
  { path: '/confidentialite', component: PageConfidentialite }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router