import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/index.vue'

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/',
        component: Layout,
        redirect: '/elderly',
        children: [
            {
                path: 'elderly',
                name: 'Elderly',
                component: () => import('../views/Elderly.vue'),
                meta: { title: '老人管理' }
            },
            {
                path: 'health',
                name: 'Health',
                component: () => import('../views/Health.vue'),
                meta: { title: '健康监测' }
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const user = localStorage.getItem('user')
    if (to.path !== '/login' && !user) {
        next('/login')
    } else {
        next()
    }
})

export default router
