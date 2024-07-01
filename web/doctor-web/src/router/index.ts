import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
import { usePermissStore } from '../store/permiss';
import Home from '../views/home.vue';
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const routes: RouteRecordRaw[] = [


    // {
    //     path: '/room/:date',
    //     name:'room',
    //     component: () => import('../views/room/index.vue'),
    //     props: true,
    // },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login/index.vue'),

    },
    
    {
        path: '/',
        redirect: '/dashboard',
    },
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {
                path: '/dashboard',
                name: 'dashboard',
                meta: {
                    title: '系统首页',
                    permiss: '0',
                },
                component: () => import(/* webpackChunkName: "dashboard" */ '../views/dashboard.vue'),
            },
            // {
            //     path: '/room-check',
            //     name: 'room-check',
            //     meta: {
            //         title: '查看排课课表',
            //         permiss: '0',
            //     },
            //     component: () => import(/* webpackChunkName: "dashboard" */ '../views/room.vue'),
            // },
            {
                path: '/checkList',
                name: 'check',
                meta: {
                    title: '体检报告列表',
                    permiss: '1',
                },
                component: () => import(/* webpackChunkName: "dashboard" */ '../views/doctor/index.vue'),
            },
            {
                path: '/data',
                name: 'data',
                meta: {
                    title: '数据管理',
                    permiss: '2',
                },
                component: () => import(/* webpackChunkName: "dashboard" */ '../views/chart/charts.vue'),
            },
            {
                path: '/account',
                name: 'account',
                meta: {
                    title: '医生账号管理',
                    permiss: '4',
                },
                component: () => import(/* webpackChunkName: "dashboard" */ '../views/admin/doctor-account.vue'),
            }
        ],
    },

];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    NProgress.start();
    const role = localStorage.getItem('token');
    if(!role&&to.path!='/login'&&to.name!='room'){next('/login')}
    if(to.path === '/'){
        if(role) {
            next('/dashboard');
        }
        else{
            next('/login');
        }
       
    }
    next();
});

router.afterEach(() => {
    const permiss = usePermissStore();
    permiss.setKey();
    NProgress.done()
})

export default router;
