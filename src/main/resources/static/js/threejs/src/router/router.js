import { createRouter, createWebHashHistory } from "vue-router";

import home from "../home/home.vue";
import three from "../three.js/three.vue";

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
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: routes
});
router.beforeEach((guard) => {
    console.log(guard);
});

export default router