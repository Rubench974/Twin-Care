import { createRouter, createWebHistory } from 'vue-router'

import PageConnexion from '../pages/PageConnexion.vue'
import PageDocuments from '../pages/PageDocuments.vue'
import PageParcours from '../pages/PageParcours.vue'
import PageChatbot from '../pages/PageChatbot.vue'
import PageEnvironnement from '../pages/PageEnvironnement.vue'

const routes = [
  { path: '/', component: PageConnexion },
  { path: '/documents', component: PageDocuments },
  { path: '/parcours', component: PageParcours },
  { path: '/chatbot', component: PageChatbot },
  { path: '/environnement', component: PageEnvironnement }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router