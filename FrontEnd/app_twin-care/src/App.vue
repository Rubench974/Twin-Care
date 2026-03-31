<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" temporary width="300">
      <div class="pa-6" style="background-color: #2A93D5; color: white; height: 180px;">
        <v-avatar size="70" color="white" class="mb-4"></v-avatar>
        <div class="text-h5 font-weight-bold">Bonjour<br>Luc</div>
      </div>

      <v-list class="pa-2 mt-2">
        <v-list-item link prepend-icon="mdi-file-document-outline" title="Mes documents" class="py-3">
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
          <v-list-item link prepend-icon="mdi-logout" title="Deconnexion" class="mb-4"></v-list-item>
          <v-btn
            block
            variant="outlined"
            color="red"
            rounded="lg"
            class="text-none font-weight-bold"
            href="tel:15"
          >
            Urgence 15
          </v-btn>
        </div>
      </template>
    </v-navigation-drawer>

    <v-main>
      <router-view></router-view>
    </v-main>

    <v-bottom-navigation v-if="!isLoginPage" v-model="activeTab" grow color="#2A93D5" height="70">
      <v-btn value="documents" to="/documents">
        <v-icon>mdi-file-document</v-icon>
        <span>Documents</span>
      </v-btn>

      <v-btn value="chatbot" to="/chatbot">
        <v-icon>mdi-chat</v-icon>
        <span>Chatbot</span>
      </v-btn>

      <v-btn value="parcours" to="/parcours">
        <v-icon>mdi-sign-direction</v-icon>
        <span>Parcours</span>
      </v-btn>

      <v-btn value="environnement" to="/environnement">
        <v-icon>mdi-leaf</v-icon>
        <span>Environnement</span>
      </v-btn>
    </v-bottom-navigation>
  </v-app>
</template>

<script setup>
import { ref, computed, provide } from 'vue'
import { useRoute } from 'vue-router'

const drawer = ref(false)
const activeTab = ref('documents')
const route = useRoute()

const isLoginPage = computed(() => route.path === '/')

const toggleDrawer = () => {
  drawer.value = !drawer.value
}
provide('toggleDrawer', toggleDrawer)
</script>

<style>
body {
  background-color: #f4f9fd;
}
</style>