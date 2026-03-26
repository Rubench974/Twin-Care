import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // Import du routeur

// Configuration de Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import '@mdi/font/css/materialdesignicons.css' // Les icônes

const vuetify = createVuetify({
  components,
  directives,
})

const app = createApp(App)
app.use(router)  // Activation du routeur
app.use(vuetify) // Activation de Vuetify
app.mount('#app')