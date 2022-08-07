import Vue from "vue";

import App from "./App.vue";
import Vuetify from "vuetify";
import "vuetify/dist/vuetify.min.css";

import "./assets/main.css";
import moment from "moment-timezone";

Vue.use(Vuetify);

Vue.filter("formatDate", function (value: Date): string {
  if (value) {
    return moment(value).format("MMMM Do, HH:mm");
  }
  return "";
});

new Vue({
  vuetify: new Vuetify(),
  render: (h) => h(App),
}).$mount("#app");
