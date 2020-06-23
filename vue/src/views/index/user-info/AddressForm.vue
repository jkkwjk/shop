<template>
    <div class="address-form">
        <el-divider content-position="left" style="margin-left: 10px;">新增收货地址</el-divider>
        <el-form :model="form" size="small" :rules="rules" label-width="120px" ref="form">
            <el-form-item label="地址信息:" prop="addressNum">
                <el-cascader :props="addressProps" v-model="form.addressNum" placeholder="请选择省/市/区"></el-cascader>
            </el-form-item>
            <el-form-item label="详细地址:" prop="addressDetail">
                <el-input v-model="form.addressDetail" type="textarea" maxlength="100" :rows="2" placeholder="请输入详细的地址"></el-input>
            </el-form-item>
            <el-form-item label="邮政编码:" prop="postcode">
                <el-input v-model="form.postcode" type="number" placeholder="请填写邮编"></el-input>
            </el-form-item>
            <el-form-item label="收货人姓名:" prop="name">
                <el-input v-model="form.name" maxlength="25" placeholder="长度不超过25个字符"></el-input>
            </el-form-item>
            <el-form-item label="手机号码:" prop="telephone">
                <el-input v-model="form.telephone" type="number" placeholder="必填"></el-input>
            </el-form-item>
            <el-form-item label="是否为默认地址:" >
                <el-switch v-model="form.defaultStatus"></el-switch>
            </el-form-item>
            <el-form-item style="margin-bottom: 0;">
                <el-button type="primary" size="medium" @click="submitForm('form')" style="float: right; width: 100px;" :disabled="remainLength == 0">添加</el-button>
            </el-form-item>
            <el-form-item style="margin-bottom: 0;">
                <span style="float: right;font-size: 6px;">已保存{{saveLength}}了条地址，还能保存{{remainLength}}条地址</span>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import CheckValue from "../../../util/CheckValue";
    export default {
        name: "AddressForm",
        props: {
            saveLength: {required: true, type: Number},
            remainLength: {required: true, type: Number},
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$emit('submit', this.form);
                    } else {
                        return false;
                    }
                });
            }
        },
        data(){
            const checkTelephone = (rule, value, callback) => {
                if (!value) {
                    callback(new Error('必填'));
                }
                if (!(CheckValue.checkPhone(value))) {
                    callback(new Error('请输入正确的手机号码'));
                } else {
                    callback();
                }
            };
            return {
                form: {
                    addressNum: '',
                    addressDetail: '',
                    name: '',
                    telephone: '',
                    defaultStatus: false,
                    postcode: '',
                },
                rules:{
                    addressNum: [
                        {required: true, message: '必填', trigger: 'blur'}
                    ],
                    addressDetail: [
                        {required: true, message: '必填', trigger: 'blur'},
                        {min: 4, max: 100, message: '长度为4-100', trigger: 'blur'}
                    ],
                    postcode: [
                        {required: false, message: '必填', trigger: 'blur'},
                        { len:6 ,message: '邮政编码错误', trigger: 'blur' }
                    ],
                    name: [
                        { required: true, message: '必填', trigger: 'blur' },
                        { max: 7, message: '超过7位最大长度', trigger: 'blur' }
                    ],
                    telephone: [{required: true, validator: checkTelephone, trigger: 'blur'}],
                },
                addressProps: {
                    lazy: true,
                    lazyLoad: (node, resolve)=>{
                        let newNode = [];
                        let payload = node.path === undefined? '':node.path.join('-');
                        this.$http.get('http://localhost:9090/', {params: {a: payload}}).then(res => {
                            res.data.forEach( (item)=>{
                                newNode.push({
                                    value: item.code,
                                    label: item.name,
                                    leaf: node.level >= 2
                                });
                            } );
                        }).then(()=>resolve(newNode));
                    }
                },
            }
        }
    }
</script>

<style scoped>

</style>