<template>
    <div id="app">
        <div class="contain">
            <Login class="box" :style="{'left':!showLogin? '-400px':'0px'}"></Login>
            <Register class="box" :style="{'left':showLogin? '400px':'0px'}"></Register>
        </div>
    </div>
</template>

<script>
    import Login from "../../views/login/Login";
    import Register from "../../views/login/Register";
    export default {
        name: "App",
        components: {
            Register,
            Login
        },
        computed: {
            showLogin(){
                return this.$store.state.showLogin
            }
        }
    }
</script>

<style scoped lang="scss">
    #app{
        width: 100%;
        height: 100%;
        background: url('/img/login-bg.jpg') no-repeat center;
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

    .contain{
        height: 450px;
        width: 400px;
        background-color: rgba(255,255,255,.9);
        display: flex;
        flex-direction: column;
        margin-right: 350px;
        position: relative;
        overflow: hidden;
    }

    .box{
        position: absolute;
        transition: all 0.7s ease;
    }
</style>