//import './assets/main.css'
import { createApp } from 'vue'

import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import App from './App.vue'

import router from './router'

import Antd from 'ant-design-vue';
//import 'ant-design-vue/dist/reset.css';
// import ElementPlus from 'element-plus'
// //import 'element-plus/dist/index.css'
// import zhCn from 'element-plus/es/locale/lang/zh-cn'

const app = createApp(App)

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

app.use(pinia)

app.use(router)

app.use(Antd)

// app.use(ElementPlus, {
//     locale: zhCn,
// })

app.mount('#app')
