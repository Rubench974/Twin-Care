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

// Gardien de navigation (empêche d'accéder aux tableaux de bord sans être connecté)
router.beforeEach((to, from, next) => {
  const estConnecte = localStorage.getItem('userRole');

  if (to.name !== 'Login' && !estConnecte) {
    next({ name: 'Login' });
  } else {
    next();
  }
});

export default router;
