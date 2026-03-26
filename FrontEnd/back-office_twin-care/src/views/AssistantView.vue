<template>
<v-container fluid class="bg-blue-lighten-5 fill-height align-start pa-4">
  <v-row class="h-100">
    
    <v-col cols="12" md="4" class="h-100">
      <DocumentList 
        :documents="documentsAttente" 
        :selectedDocId="documentSelectionne?.id"
        @selectDoc="selectionnerDocument"
      />
    </v-col>

    <v-col cols="12" md="8" class="h-100">
      <DocumentViewer 
        :document="documentSelectionne"
        @validate="validerDocument"
        @reject="rejeterDocument"
      />
    </v-col>

  </v-row>
</v-container>
</template>

<script setup>
import { ref } from 'vue'
import DocumentList from '../components/DocumentList.vue'
import DocumentViewer from '../components/DocumentViewer.vue'

const documentsAttente = ref([
{ id: 1, patient: 'Alice Tremblay', type: 'Ordonnance', date: '25/03/2026', format: 'image', url: 'https://images.unsplash.com/photo-1586281380349-632531db7ed4?auto=format&fit=crop&q=80&w=400&h=600' },
{ id: 2, patient: 'Bob Martin', type: 'Imagerie', date: '24/03/2026', format: 'image', url: 'https://images.unsplash.com/photo-1530497610245-94d3c16cda28?auto=format&fit=crop&q=80&w=400&h=600' },
{ id: 3, patient: 'Charlie Dupont', type: 'Biologie', date: '23/03/2026', format: 'pdf', url: '' },
])

const documentSelectionne = ref(null)

const selectionnerDocument = (doc) => { documentSelectionne.value = doc }

const validerDocument = () => {
alert(`✅ Validé !`)
retirerDocumentDeLaListe()
}

const rejeterDocument = () => {
if(prompt("Motif du rejet ?")) retirerDocumentDeLaListe()
}

const retirerDocumentDeLaListe = () => {
documentsAttente.value = documentsAttente.value.filter(d => d.id !== documentSelectionne.value.id)
documentSelectionne.value = null
}
</script>