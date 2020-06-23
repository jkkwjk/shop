<template>
    <div id="app">
        <Header></Header>
        <router-view style="flex: 1"></router-view>
    </div>
</template>

<script>
    import Header from "../../components/main-page/header/Header";
    export default {
        name: "App",
        components: {Header},
        data() {
            return {

            }
        },
        methods: {

        },
        created() {
            this.$http.get('/user/is_login').then(res=>{
                if(res.data.code === 200){
                    this.$http.post("/user/get_info").then(res=>{
                        this.$store.commit('updateUserInfo', res.data.data);
                    });
                    this.$http.get("/buy_cart").then(res=>{
                        this.$store.commit('updateBuyCart', res.data.data);
                    });
                }
            });
        }
    }
</script>

<style>
    #app{
        min-height: 100%;
        position: absolute;
        display: flex;
        flex-direction: column;
        background-color: rgba(0,0,0,0.02);
    }
</style>