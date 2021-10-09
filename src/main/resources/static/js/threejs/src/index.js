import { WEBGL } from 'three/examples/jsm/WebGL';
import './css/common/common.css'
import register from './register/register.js'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './css/app.css'
import App from './App.vue'


window.onload = function() {
    if (WEBGL.isWebGLAvailable()) {
        console.log('Welcome');

        const app = createApp(App)
        app.use(ElementPlus, { size: 'small', zIndex: 99999 })
        app.mount('#container')
    } else {
        const warning = WEBGL.getWebGLErrorMessage();
        document.getElementById('container').appendChild(warning);
    }
}