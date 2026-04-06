<template>
  <v-container fluid class="fill-height pa-0" style="background: linear-gradient(180deg, #cae8fa 0%, #f4f9fd 100%);">
    
    <v-btn icon="mdi-chevron-left" variant="text" color="#2A93D5" size="large" style="position: absolute; top: 20px; left: 10px;" to="/"></v-btn>

    <v-row justify="center" align="center" class="w-100 ma-0">
      <v-col cols="10" sm="8" md="6" class="text-center">
        <h1 class="font-weight-black mb-12" style="color: #2A93D5; font-size: 2.2rem; line-height: 1.2;">
          Mot de passe<br>oublié
        </h1>

        <div class="text-left mb-1 ml-1 font-weight-medium" style="color: #37474F; font-size: 0.85rem;">
          Adresse mail
        </div>
        <v-text-field
          placeholder="ex: luc@email.com"
          variant="solo"
          bg-color="white"
          rounded="lg"
          elevation="0"
          density="comfortable"
          class="mb-6"
          hide-details
          style="border: 1px solid #E0E0E0; border-radius: 8px;"
        ></v-text-field>

        <v-btn
          color="#2A93D5"
          block
          rounded="lg"
          size="large"
          class="text-white font-weight-bold text-none mt-2"
          elevation="2"
        >
          Réinitialiser
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
</script>