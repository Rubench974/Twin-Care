<template>
<v-card v-if="document" class="elevation-2 rounded-lg h-100 d-flex flex-column">
  <v-card-title class="bg-grey-lighten-4 pa-4 border-b">
    Dossier : <span class="font-weight-bold" style="color:#156500;">{{ document.patient }}</span>
  </v-card-title>

  <v-card-text class="flex-grow-1 bg-grey-lighten-3 d-flex justify-center align-center ma-4 rounded">
    <v-img v-if="document.format === 'image'" :src="document.url" max-height="400" contain></v-img>
    <div v-else class="text-center text-grey"><v-icon size="64">mdi-file-pdf-box</v-icon><p>Aperçu PDF</p></div>
  </v-card-text>

  <v-card-actions class="pa-4 d-flex justify-center gap-4">
    <v-btn color="error" variant="flat" @click="$emit('reject')">Rejeter</v-btn>
    <v-btn color="success" variant="flat" @click="$emit('validate')">Valider</v-btn>
  </v-card-actions>
</v-card>
<v-card v-else class="h-100 d-flex justify-center align-center">
  <h2 class="text-grey">Sélectionnez un document</h2>
</v-card>
</template>

<script setup>
defineProps({ document: Object })
defineEmits(['validate', 'reject'])
</script>