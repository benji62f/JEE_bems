import Vue from "vue";
import VueRouter from "vue-router";
import AboutView from "../views/AboutView.vue";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: "/",
      redirect: "/about",
    },
    {
      path: "/about",
      name: "about",
      component: AboutView,
    },
    {
      path: "/calendar",
      name: "calendar",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/CalendarView.vue"),
    },
  ],
});

export default router;
