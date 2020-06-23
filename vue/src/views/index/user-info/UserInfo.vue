<template>
    <div class="user-info">
        <el-card shadow="hover">
            <el-divider content-position="left" style="margin-left: 10px;">基本信息</el-divider>
            <el-form style="padding-left: 20px">
                <el-form-item label="真实姓名:">
                    <span>{{ form.name }}</span>
                </el-form-item>
                <el-form-item label="昵称:">
                    <editable-span :text=form.nickName @edit="(t, callback)=>editCallback('nickName',t,callback)"></editable-span>
                </el-form-item>
                <el-form-item label="电话:">
                    <editable-span type="number" :text=form.telephone @edit="(t, callback)=>editCallback('telephone',t,callback)"></editable-span>
                </el-form-item>
                <el-form-item label="电子邮件:">
                    <editable-span :text=form.email @edit="(t, callback)=>editCallback('email',t,callback)"></editable-span>
                </el-form-item>

                <el-form-item label="性别:">
                    <el-radio-group v-model="form.sex">
                        <el-radio :label="true">男</el-radio>
                        <el-radio :label="false">女</el-radio>
                    </el-radio-group>
                </el-form-item>

            </el-form>
        </el-card>
        <el-card shadow="hover" style="margin-top: 10px">
            <el-divider content-position="left" class="margin-left: 10px;">密码修改</el-divider>
            <el-form :model="pwd" :rules="pwdRules" ref="pwdForm">
                <el-form-item label="密码:" prop="one">
                    <el-input type="password" v-model="pwd.one" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码:" prop="two" style="margin-top:30px;">
                    <el-input type="password" v-model="pwd.two" placeholder="确认密码"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="medium" @click="submitForm('pwdForm')" style="float: right; width: 100px;">修改密码</el-button>
                </el-form-item>
            </el-form>
        </el-card>

    </div>
</template>

<script>
    import EditableSpan from "../../../components/main-page/index/EditableSpan";
    import CheckValue from "../../../util/CheckValue";
    import {deepCopy} from "../../../util/MemoryUtil"
    export default {
        name: "UserInfo",
        components: {EditableSpan},
        data(){
            const checkPass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else if (value.length < 8) {
                    callback(new Error('请输入至少8位的密码'));
                } else if (!CheckValue.checkPwd(value)) {
                    callback(new Error('密码必须包含英文和数字'));
                } else {
                    if (this.pwd.two !== '') {
                        this.$refs.pwdForm.validateField('two');
                    }
                    callback();
                }
            };
            const checkPass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.pwd.one) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                form: {
                    name: undefined,
                    nickName: undefined,
                    sex: undefined,
                    icon: undefined,
                    telephone: undefined,
                    email: undefined
                },
                pwd: {
                    one: '',
                    two: ''
                },
                pwdRules: {
                    one: [{ required:true, validator: checkPass, trigger: 'blur' }],
                    two: [{ required:true, validator: checkPass2, trigger: 'blur' }]
                }
            }
        },
        created(){
            this.form = deepCopy(this.$store.state.userInfo);
            this.$watch('form.sex', ()=>{this.modifySubmit('sex',this.form.sex)});
        },
        methods: {
            modifySubmit(prop, value, callback){
                let payload = {};
                payload[prop] = value;
                this.$http.post('/user/modify',payload).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("修改成功");
                        if(prop === 'pwd') {
                            this.pwd.one = '';
                            this.pwd.two = '';
                        }else {
                            this.form[prop] = value;
                        }
                        
                        this.$store.commit("updateUserInfo",this.form);
                    }else {
                        callback(this.form[prop]);
                        this.$message.error(data.msg);
                    }
                });
            },
            editCallback(value, text, callback){
                let canEdit = true;
                switch (value) {
                    case 'telephone':
                        canEdit = CheckValue.checkPhone(text);
                        if (!canEdit) this.$message.error("请输入正确的手机号码");
                        break;
                    case 'nickName':
                        canEdit = CheckValue.checkNickName(text);
                        if (!canEdit) this.$message.error("昵称应在4-13位");
                        break;
                    case 'email':
                        canEdit = CheckValue.checkEmail(text);
                        if (!canEdit) this.$message.error("请输入正确的邮箱");
                        break;
                }
                if (canEdit){
                    this.modifySubmit(value, text, callback);
                }else{
                    // 将新的值传给子组件
                    callback(this.form[value]);
                }
                
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.modifySubmit('pwd', this.pwd.one);
                    } else {
                        return false;
                    }
                });
            },
        }
    }
</script>

<style scoped>
    .user-info{

    }

    .el-form-item{
        display: flex;
    }

    .el-form-item /deep/ .el-form-item__content{
        flex: 1;
    }
</style>