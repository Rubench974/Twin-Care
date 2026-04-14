import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../views/LoginView.vue';
import AssistantView from '../views/AssistantView.vue';
import DoctorView from '../views/DoctorView.vue';

const routes = [
  { path: '/', redirect: '/login' }, // Redirige l'accueil vers la connexion
  { path: '/login', name: 'Login', component: LoginView },
  { path: '/assistant', name: 'Assistant', component: AssistantView },
  { path: '/medecin', name: 'Medecin', component: DoctorView },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Gardien de navigation (Vigile mis à jour pour Vue Router 4)
router.beforeEach((to, from) => {
  // On vérifie le "token" (le badge officiel) ou le "role" (comme écrit dans ton LoginView)
  const estConnecte = localStorage.getItem('token'); 

  if (to.name !== 'Login' && !estConnecte) {
    // Si pas connecté et qu'on essaie d'aller ailleurs, on renvoie vers le Login
    return { name: 'Login' };
  }
  // Sinon, on laisse passer naturellement (plus besoin de next() !)
  return true;
});

export default router;