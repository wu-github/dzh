import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import index from './index.vue'
import router from "./router/router.js"
import './css/common/common.css'

const app = createApp(index)
app.use(ElementPlus, { size: 'small', zIndex: 99999 })
app.use(router)
app.mount('#container')