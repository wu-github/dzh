import { createRouter, createWebHashHistory } from "vue-router";

import home from "../home/home.vue";
import threeDemo from "../three.js/three.vue";
import threeAmmo from "../three.js/three-ammo.vue";
import threeKali from "../three.js/three-kali.vue";

const routes = [{
        path: "/",
        redirect: "/home"
    },
    {
        path: "/home",
        component: home
    },
    {
        path: "/three-demo",
        component: threeDemo
    },
    {
        path: "/three-ammo",
        component: threeAmmo
    },
    {
        path: "/three-kali",
        component: threeKali
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: routes
});
router.beforeEach((guard) => {
    console.debug(guard);
});

export default router