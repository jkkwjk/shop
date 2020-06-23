import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        userInfo: {},
        buyCart: []
    },
    mutations: {
        update(state, [key, value]){
            state[key]=value;
        },
        updateUserInfo(state, userInfo){
            state.userInfo = userInfo;
        },
        updateBuyCart(state, buyCart){
            state.buyCart = buyCart;
        },

        addGoods2buyCart(state, goods){
            let item = state.buyCart.find(_=>{return _.store.id === goods.store.id});

            if (item === undefined){
                // 没有店铺
                state.buyCart.push({store: goods.store, goodsList: [goods]});
            }else {
                let goodsInBuyCart = item.goodsList.find(_=>{return _.id === goods.id});
                if (goodsInBuyCart === undefined){
                    //没有商品
                    item.goodsList.push(goods); // warn: 此处会将goods对象与外部对象绑定
                }
            }
        }
    }
});

export default store