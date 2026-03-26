<template>
<v-card class="elevation-2 rounded-lg flex-grow-1 h-100">
  <v-card-title class="bg-primary text-white pa-4">
    <v-icon start>mdi-inbox-arrow-down</v-icon> Documents à vérifier
  </v-card-title>
  <v-list lines="two">
    <v-list-item
      v-for="doc in documents" :key="doc.id" :value="doc"
      @click="$emit('selectDoc', doc)"
      :class="{ 'bg-blue-lighten-4': selectedDocId === doc.id }"
    >
      <v-list-item-title class="font-weight-bold">{{ doc.patient }}</v-list-item-title>
      <v-list-item-subtitle>{{ doc.type }}</v-list-item-subtitle>
    </v-list-item>
  </v-list>
</v-card>
</template>

<script setup>
defineProps({
documents: Array,
selectedDocId: Number
})
defineEmits(['selectDoc'])
</script>

<style scoped>
.bg-primary { background-color: #156500 !important; }
</style>