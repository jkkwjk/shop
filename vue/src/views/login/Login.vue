<template>
    <div class="login">
        <div class="show-tip">
            <p><span>密码登录</span></p>
        </div>

        <el-row>
            <el-col :span="24">
                <el-input class="user" v-model="user" placeholder="请输入账号" type="text" @keyup.enter.native="bindKeyUp"></el-input>
            </el-col>
        </el-row>
        <el-divider class="line"></el-divider>
        <el-row>
            <el-col :span="24">
                <el-input class="pwd" v-model="pwd" placeholder="请输入密码" type="password" @keyup.enter.native="bindKeyUp"></el-input>
            </el-col>
        </el-row>

        <el-button type="success" class="btn" @click="login(user, pwd)">登录</el-button>
        <el-button type="danger" class="btn"
                   style="margin-left: 0;"
                   @click="$store.commit('GO_TO_OTHER')">没有账号，前往注册 →</el-button>
    </div>

</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                user: 'bbbbb',
                pwd: 'jkk112233'
            }
        },
        methods: {
            bindKeyUp(){
                this.login(this.user, this.pwd);
            },

            login(user, pwd){
                if (user === ''){
                    this.$message({
                        type: 'error',
                        message: '请输入您的账号',
                        showClose: true
                    });
                }else if (pwd === ''){
                    this.$message({
                        type: 'error',
                        message: '请输入您的密码',
                        showClose: true
                    });
                }else {
                    this.$http.post("/user/login",{
                            nickName: user,
                            pwd: pwd
                    }).then(res=>{
                        const data = res.data;
                        if (data.code === 200){
                            if (document.referrer !== ""){
                                if (window.location.href.indexOf(document.referrer) !== -1){
                                    window.history.back();
                                }
                            }else {
                                window.location.href = 'index.html'
                            }
                        }else {
                            this.$message.error(data.msg);
                        }
                    });
                }
            }
        }
    }

</script>

<style scoped>
    .login{
        padding: 10px;
        width: 100%;
        box-sizing: border-box;
    }
    .user>>>.el-input__inner{
        border-radius: 5px 5px 5px 5px;
    }
    .pwd>>>.el-input__inner{
        border-radius: 5px 5px 5px 5px;
    }
    .btn{
        width: 100%;
        margin-top: 20px;
    }
    .line{
        margin: 1px;
    }
    .show-tip{
        padding-left: 10px;
        font-size: 16px;
        color: #3c3c3c;
        font-weight: 700;
    }
</style>