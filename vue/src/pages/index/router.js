import Vue from 'vue'
import VueRouter from 'vue-router'

//解决点击同一个路由报错
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
};

Vue.use(VueRouter);

const routes = [
    {
        path: '/user-info',
        component: () => import('../../views/index/user-info/MainView'),
        children: [
            {path: '', component: () => import('../../views/index/user-info/UserInfo')},
            {path: 'address', component: () => import('../../views/index/user-info/Address')}
        ]
    },
    {
        path: '/',
        component: () => import('../../views/index/MainView'),
    },
    {
        path: '/goods-manage',
        component: () => import('../../views/index/goods-manage/MainView'),
    },
    {
        path: '/detail/:id',
        component: () => import('../../views/index/detail/MainView'),
    },
    {
        path: '/buy-cart',
        component: () => import('../../views/index/buy-cart/MainView'),
    },
    {
        path: '/order',
        name: 'order',
        component: () => import('../../views/index/order/MainView'),
    },
    {
        path: '/order-manage',
        component: () => import('../../views/index/order-manage/MainView'),
    },
    {
        path: '/goods-snapshot',
        name: 'goods-snapshot',
        component: () => import('../../views/index/goods-snapshot/MainView'),
    },
];

const router = new VueRouter({
    routes
});

export default router
