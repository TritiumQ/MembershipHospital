import { createApp } from 'vue';
import { createPinia } from 'pinia';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import App from './App.vue';
import router from './router';
import 'element-plus/dist/index.css';
import './assets/css/icon.css';
import { t } from '@wangeditor/editor';

const app = createApp(App);
app.use(createPinia());
app.use(router);

// 注册elementplus图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}
// // 自定义权限指令
// const permiss = usePermissStore();
// app.directive('permiss', {
//     mounted(el, binding) {
//         if (!permiss.key.includes(String(binding.value))) {
//             //是否隐藏侧边栏元素，true为隐藏，false为不隐藏
//             el['hidden']=false;
     
//         }
//     },
// });

app.mount('#app');
