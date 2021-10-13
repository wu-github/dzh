import { createRouter, createWebHashHistory } from "vue-router";

import home from "../home/home.vue";
import three from "../three.js/three.vue";
import threeAmmo from "../three.js/three-ammo.vue";

const routes = [{
        path: "/",
        redirect: "/home"
    },
    {
        path: "/home",
        component: home
    },
    {
        path: "/three",
        component: three
    },
    {
        path: "/three-ammo",
        component: threeAmmo
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