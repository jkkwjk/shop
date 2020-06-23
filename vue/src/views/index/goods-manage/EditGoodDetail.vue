<template>
    <el-dialog title="编辑详情" :visible.sync="visible__inter">
        <el-form :model="form__inter" :rules="rules" ref="form" label-width="100px">
            <el-form-item label="商店名称" prop="goodsName">
                <el-input v-model="form__inter.goodsName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="商品现价" prop="goodsActualPrice">
                <el-input v-model="form__inter.goodsActualPrice" autocomplete="off" type="number"></el-input>
            </el-form-item>
            <el-form-item label="商品库存" prop="goodsStock">
                <el-input v-model="form__inter.goodsStock" autocomplete="off" type="number"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="onCancel">取 消</el-button>
            <el-button type="primary" @click="onSubmit">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {deepCopy} from "../../../util/MemoryUtil"
    export default {
        name: "EditGoodDetail",
        props: {
            visible: {type: Boolean, required: true},
            form: {required: true, default: ()=>{return {goodsName: '', goodsActualPrice: '', goodsStock: ''}}}
        },
        data(){
            return {
                visible__inter: this.visible,
                form__inter: { },
                rules: {
                    goodsName: [
                        { required: true, message: '请输入商品名称', trigger: 'blur' },
                        { min: 5, max: 80, message: '商品名称需在5-80位', trigger: 'blur' }
                    ],
                    goodsStock: [
                        { required: true, message: '请输入商品库存', trigger: 'blur' },
                        { min: 1, max: 999999999, type: 'number', transform(value) {return Number(value)},
                            message: '商品库存应为1-999999999件', trigger: 'blur'},
                        { transform(value) {return Number(value)%1 === 0?0:''}, type: 'number', message: '请输入一个整数', trigger: 'blur'}
                    ],
                    goodsActualPrice: [
                        { required: true, message: '请输入商品价格', trigger: 'blur' },
                        { min: 0.01, max: 999999999, type: 'number', transform(value) {return Number(value)},
                            message: '商品价格应为0.01-999999999元', trigger: 'blur'},
                    ]
                }
            }
        },
        methods: {
            onCancel(){
                this.visible__inter = false;
            },
            onSubmit(){
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.$emit('submit',this.form__inter);
                        this.visible__inter = false;
                    } else {
                        return false;
                    }
                });
            }
        },
        watch: {
            visible__inter(){
                this.$emit('update:visible',this.visible__inter);
                if (this.visible__inter === false) {
                    this.$refs.form.clearValidate();
                }
            },
            visible(){
                this.visible__inter = this.visible;
            },
            form: {
                deep: true,
                handler(){
                    this.form__inter = deepCopy(this.form);
                }
            }
        }

    }
</script>

<style scoped>

</style>