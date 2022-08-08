<script>
export default {
  name: "AboutView",
  mounted() {
    document
      .getElementsByClassName("v-application")[0]
      .classList.add("transparent");
  },
  destroyed() {
    document
      .getElementsByClassName("v-application")[0]
      .classList.remove("transparent");
  },
};
</script>

<template>
  <v-container>
    <v-row class="fill-height" justify="center">
      <section id="home">
        <div class="content">
          <div class="content-container">
            <div class="logo">
              <img src="../assets/icon.png" alt="logo">
            </div>
            <h1>
              Basic Event Management System
            </h1>
            <h2>
              Give less time to manage events, gain more time to organize events!
            </h2>
            <v-btn text rounded x-large class="btn-kumojin-style" @click="$router.push('/calendar')">
              Discover
            </v-btn>
          </div>
        </div>
      </section>
    </v-row>
  </v-container>
</template>

<style>
@import "../assets/about.css";
</style>
