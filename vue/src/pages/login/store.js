import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        showLogin: true
    },
    mutations: {
        GO_TO_OTHER(state){
            state['showLogin'] = !state['showLogin'];
        },
        update(state,[key,value]){
            state[key]=value;
        },
    }
});

export default store