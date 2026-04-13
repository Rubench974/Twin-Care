<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" temporary width="300">
      <div class="pa-6" style="background-color: #2A93D5; color: white; height: 180px;">
        <v-avatar size="70" color="white" class="mb-4"></v-avatar>
        <div class="text-h5 font-weight-bold">Bonjour<br>Luc</div>
      </div>

      <v-list class="pa-2 mt-2">
        <v-list-item to="/mes-documents" prepend-icon="mdi-file-document-outline" title="Mes documents" class="py-3">
          <template v-slot:append><v-icon size="small">mdi-chevron-right</v-icon></template>
        </v-list-item>

        <v-list-item link prepend-icon="mdi-calendar-range" title="Mes rendez-vous" class="py-3">
          <template v-slot:append><v-icon size="small">mdi-chevron-right</v-icon></template>
        </v-list-item>

        <v-list-item link prepend-icon="mdi-account-outline" title="Mon profil" class="py-3">
          <template v-slot:append><v-icon size="small">mdi-chevron-right</v-icon></template>
        </v-list-item>

        <v-list-item link prepend-icon="mdi-lock-outline" title="Confidentialité" class="py-3">
          <template v-slot:append><v-icon size="small">mdi-chevron-right</v-icon></template>
        </v-list-item>
      </v-list>

      <template v-slot:append>
        <div class="pa-4">
          <v-list-item link prepend-icon="mdi-logout" title="Deconnexion" class="mb-4" @click="seDeconnecter"></v-list-item>
          <v-btn block variant="outlined" color="red" rounded="lg" class="text-none font-weight-bold" href="tel:15">
            Urgence 15
          </v-btn>
        </div>
      </template>
    </v-navigation-drawer>

    <v-main>
      <router-view></router-view>
    </v-main>

    <BarreNavigation v-if="!isLoginPage" />

  </v-app>
</template>

<script setup>
import { ref, computed, provide } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import BarreNavigation from './components/BarreNavigation.vue'

const drawer = ref(false)
const route = useRoute()
const router = useRouter()

const isLoginPage = computed(() => route.path === '/' || route.path === '/mot-de-passe-oublie')

const toggleDrawer = () => {
  drawer.value = !drawer.value
}
provide('toggleDrawer', toggleDrawer)

const seDeconnecter = () => {
  drawer.value = false
  router.push('/')
}
</script>

<style>
body {
  background-color: #f4f9fd;
}
</style>