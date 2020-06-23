import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

import animate from 'animate.css' //动画库
// import "bootstrap"
// import "bootstrap/dist/css/bootstrap.css"

//全局变量
import store from './store'
import instance from '@/http/http'
import router from './router'
Vue.prototype.$store = store;
Vue.prototype.$http = instance;

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor);
Vue.prototype.$markdownIt = mavonEditor.markdownIt;

import '../../assets/css/public.scss' //全局样式

// 价格解析
Vue.filter('formatPrice', function(val) {
    return Number(val).toFixed(2);
});

new Vue({
    store,
    router,
    render: h => h(App)
}).$mount('#app');
