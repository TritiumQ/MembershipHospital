import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import IndexView from '@/views/IndexView.vue';
import UserLayout from '@/layout/UserLayout.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "root",
      component: UserLayout,
      children: [
        {
          path: "/",
          name: "index",
          component: IndexView,
        },
        {
          path: "/personal",
          name: "personal",
          component: () => import("../views/PersonalView.vue"),
        },
        {
          path: "/appointment",
          name: "appointment",
          component: () => import("../views/AppointmentView.vue"),
        },
        {
          path: "/appointmentcancel",
          name: "appointmentcancel",
          component: () => import("../views/AppointmentcancelView.vue"),
        },
        {
          path: "/appointmentlist",
          name: "appointmentlist",
          component: () => import("../views/AppointmentlistView.vue"),
        },
        {
          path: "/appointmentsuccess",
          name: "appointmentsuccess",
          component: () => import("../views/AppointmentsuccessView.vue"),
        },
        {
          path: "/confirmorder",
          name: "confirmorder",
          component: () => import("../views/ConfirmorderView.vue"),
        },
        {
          path: "/hospital",
          name: "hospital",
          component: () => import("../views/HospitalView.vue"),
        },
        {
          path: "reportlist",
          name: "reportlist",
          component: () => import("../views/ReportlistView.vue"),
        },
        {
          path: "/report",
          name: "report",
          component: () => import("../views/ReportView.vue"),
        },
        {
          path: "/selectdate",
          name: "selectdate",
          component: () => import("../views/SelectdateView.vue"),
        },
        {
          path: "/setmeal",
          name: "setmeal",
          component: () => import("../views/SetmealView.vue"),
        }
      ]
    },

    {
      path: "/login",
      name: "login",
      component: () => import("../views/LoginView.vue"),
    },
    {
      path: "/register",
      name: "register",
      component: () => import("../views/RegisterView.vue"),
    },


    {
      path: "/test",
      name: "home",
      component: HomeView,
    },
  ],
});


// router.beforeEach((to, from, next) => {
//   if (to.name !== 'login' && to.name !== 'register' && !localStorage.getItem('userToken')) {
//     next({ name: 'login' })
//   } else {
//     next()
//   }
// });

export default router
