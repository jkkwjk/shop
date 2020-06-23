<template>
    <el-dialog title="新增店铺" :visible.sync="visible">
        <el-form :model="form" :rules="rules" ref="form" label-width="80px">
            <el-form-item label="商店名称" prop="storeName">
                <el-input v-model="form.storeName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="商店描述" prop="storeDescribe">
                <el-input v-model="form.storeDescribe" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="onCancel">取 消</el-button>
            <el-button type="primary" @click="onSubmit">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "CreateStore",
        data(){
            return {
                visible: true,
                form: {
                    storeName: '',
                    storeDescribe: '',
                },
                rules: {
                    storeName: [
                        { required: true, message: '请输入店铺名称', trigger: 'blur' },
                        { min: 2, max: 10, message: '店铺名称需在2-10位', trigger: 'blur' }
                    ],
                    storeDescribe: [
                        { required: true, message: '请输入店铺描述', trigger: 'blur' },
                        { min: 5, max: 40, message: '店铺描述需在5-40位', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            onCancel(){
                this.$emit('cancel');
                this.visible = false;
            },
            onSubmit(){
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.$emit('submit',this.form);
                        this.visible = false;
                    } else {
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>