<template>
    <div class="register">
        <div class="show-tip">
            <p><span>立刻注册</span></p>
        </div>

        <el-form :model="form" :rules="rules" ref="form" label-width="auto"
                 class="form" size="small" >

            <el-form-item label="真实姓名" prop="name">
                <div slot="error" slot-scope="data" class="error-tip">{{data.error}}</div>
                <el-input v-model="form.name"></el-input>
            </el-form-item>

            <el-form-item label="昵称" prop="nickName">
                <div slot="error" slot-scope="data" class="error-tip">{{data.error}}</div>
                <el-input v-model="form.nickName"></el-input>
            </el-form-item>

            <el-form-item label="性别" prop="sex">
                <div slot="error" slot-scope="data" class="error-tip">{{data.error}}</div>
                <el-radio-group v-model="form.sex">
                    <el-radio :label="true">男</el-radio>
                    <el-radio :label="false">女</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="电话" prop="telephone">
                <div slot="error" slot-scope="data" class="error-tip">{{data.error}}</div>
                <el-input v-model="form.telephone"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="pwd">
                <div slot="error" slot-scope="data" class="error-tip">{{data.error}}</div>
                <el-input v-model="form.pwd" type="password"></el-input>
            </el-form-item>

            <el-form-item label="确认密码" prop="confirmPwd">
                <div slot="error" slot-scope="data" class="error-tip">{{data.error}}</div>
                <el-input v-model="form.confirmPwd" type="password"></el-input>
            </el-form-item>

            <el-form-item label="邮箱" prop="email">
                <div slot="error" slot-scope="data" class="error-tip">{{data.error}}</div>
                <el-input v-model="form.email"></el-input>
            </el-form-item>

            <el-button type="success" class="btn" @click="submitForm('form')">立刻注册</el-button>

            <el-button type="danger" class="btn" style="margin-left: 0; margin-top: 10px;"
                       @click="$store.commit('GO_TO_OTHER')">已有账号，前往登录 ←</el-button>
        </el-form>


    </div>
</template>

<script>
    import CheckValue from '../../util/CheckValue'
    import {deepCopy} from '../../util/MemoryUtil'
    export default {
        name: "Register",
        data(){
            const checkTelephone = (rule, value, callback) => {
                if (!value) {
                    callback(new Error('请输入手机号码'));
                }
                if (!(CheckValue.checkPhone(value))) {
                    callback(new Error('请输入正确的手机号码'));
                } else {
                    callback();
                }
            };
            const checkPass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else if (value.length < 8) {
                    callback(new Error('请输入至少8位的密码'));
                } else if (!CheckValue.checkPwd(value)) {
                    callback(new Error('密码必须包含英文和数字'));
                } else {
                    if (this.form.confirmPwd !== '') {
                        this.$refs.form.validateField('confirmPwd');
                    }
                    callback();
                }
            };
            const checkPass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.form.pwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            const checkEmail = (rule, value, callback) => {
                if (value !== '' && !CheckValue.checkEmail(value)) {
                    callback(new Error('请输入正确的邮箱'));
                } else {
                    callback();
                }
            };
            return {
                form: {
                    name: '',
                    nickName: '',
                    sex: '',
                    telephone: '',
                    pwd: '',
                    confirmPwd: '',
                    email: ''
                },
                rules: {
                    name: [
                        { required: true, message: '请输入真实姓名', trigger: 'blur' },
                        { max: 7, message: '超过7位最大长度', trigger: 'blur' }
                    ],
                    nickName: [
                        { required: true, message: '请输入真实姓名', trigger: 'blur' },
                        { min: 4, max: 13, message: '昵称应在4-13位', trigger: 'blur' }
                    ],
                    sex: [{ required: true, message: '请选择性别', trigger: 'blur' }],
                    telephone: [{ required:true, validator: checkTelephone, trigger: 'blur' }],
                    pwd: [{ required:true, validator: checkPass, trigger: 'blur' }],
                    confirmPwd: [{ required:true, validator: checkPass2, trigger: 'blur' }],
                    email: [{ validator: checkEmail, trigger: 'blur' }]

                },

            }
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let form = deepCopy(this.form);
                        delete (form.confirmPwd);
                        this.$http.post('/user/register',form).then(res=>{
                            const data = res.data;
                            if (data.code === 505){
                                this.$message.error(data.msg);
                            }else {
                                window.location.href = 'index.html';
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
        }
    }
</script>

<style scoped>
    .register{
        padding: 10px;
        width: 100%;
        box-sizing: border-box;
    }

    .show-tip{
        padding-left: 10px;
        font-size: 16px;
        color: #3c3c3c;
        font-weight: 700;
    }

    .btn{
        margin-top: 10px;
        width: 100%;
    }

    .error-tip{
        position: absolute;
        right: 10px;
        top: 1px;
        color: #F56C6C;
        font-size: 12px;
    }

    .el-form-item--mini.el-form-item, .el-form-item--small.el-form-item{
        margin-bottom: 3px;
    }
</style>