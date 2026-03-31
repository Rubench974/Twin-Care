<template>
  <div style="position: fixed; bottom: 20px; left: 50%; transform: translateX(-50%); width: 90%; max-width: 400px; z-index: 1000;">
    
    <v-bottom-navigation
      bg-color="#6B7280"
      color="#ffffff"
      elevation="6"
      rounded="pill"
      height="56"
    >
      <v-btn to="/documents" style="min-width: 25%; padding: 0;">
        <v-icon size="22" class="mb-1">mdi-file-document-outline</v-icon>
        <span class="font-weight-medium" style="font-size: 0.65rem;">Documents</span>
      </v-btn>

      <v-btn to="/chatbot" style="min-width: 25%; padding: 0;">
        <v-icon size="22" class="mb-1">mdi-forum</v-icon>
        <span class="font-weight-medium" style="font-size: 0.65rem;">Chatbot</span>
      </v-btn>

      <v-btn to="/parcours" style="min-width: 25%; padding: 0;">
        <v-icon size="22" class="mb-1">mdi-sign-direction</v-icon>
        <span class="font-weight-medium" style="font-size: 0.65rem;">Parcours</span>
      </v-btn>

      <v-btn to="/environnement" style="min-width: 25%; padding: 0;">
        <v-icon size="22" class="mb-1">mdi-leaf</v-icon>
        <span class="font-weight-medium" style="font-size: 0.65rem;">Environnement</span>
      </v-btn>
    </v-bottom-navigation>

  </div>
</template>

<script setup>

</script>