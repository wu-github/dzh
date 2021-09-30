import { WEBGL } from 'three/examples/jsm/WebGL';
import './css/common/common.css'
import { demo1 } from './demo/demo1';
import { demo2 } from './demo/demo2.js';

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './css/app.css'
import App from './App.vue'

import constants from './constants/constants.js'

window.onload = function() {
    if (WEBGL.isWebGLAvailable()) {
        console.log('Welcome');

        constants.examples.push({ title: 'demo1', view: demo1 });
        constants.examples.push({ title: 'demo2', view: demo2 });

        const app = createApp(App)
        app.use(ElementPlus, { size: 'small', zIndex: 99999 })
        app.mount('#container')
    } else {
        const warning = WEBGL.getWebGLErrorMessage();
        document.getElementById('container').appendChild(warning);
    }
}