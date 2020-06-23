<template>
    <div>
        <el-dialog class="add-good" :visible.sync="visible__inter" title="新增商品">
            <el-form :model="form" :rules="rules" ref="form" label-width="80px">
                <el-form-item label="商品名称" prop="goodsName">
                    <el-input v-model="form.goodsName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="商品描述" prop="goodsDescribe">
                    <el-button size="mini" @click="OnEditDetail">点我编辑</el-button>
                </el-form-item>
                <el-form-item label="商品类型" prop="dicCode">
                    <el-cascader :props="lazyLoadType" v-model="form.dicCode" placeholder="请选择商品类型" style="display: block"></el-cascader>
                </el-form-item>
                <el-form-item label="商品封面" class="uploader" prop="goodsPic">
                    <el-upload
                            action="http://localhost:9091/upload"
                            :show-file-list="false"
                            :on-success="handleUploadSuccess"
                            :before-upload="beforeUpload">
                        <img v-if="form.goodsPic" :src="form.goodsPic" alt="">
                        <i v-else class="el-icon-plus avatar-uploader-icon" style="font-size: 30px;"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="商品库存" prop="goodsStock">
                    <el-input v-model="form.goodsStock" autocomplete="off" type="number"></el-input>
                </el-form-item>
                <el-form-item label="商品价格" prop="goodsActualPrice">
                    <el-input v-model="form.goodsActualPrice" autocomplete="off" type="number"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="visible__inter = false">取 消</el-button>
                <el-button type="primary" @click="onSubmit">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="请输入商品属性(所有属性都必填)" :visible.sync="fieldValueDialogVisible" width="40%"
                    @open="formField.reset = true" @close="onFieldClose" :before-close="beforeCloseFieldDialog">
            <el-form label-position="top" :model="formField" ref="formField" inline>
                <el-row>
                    <el-col :span="12" v-for="(field, index) in formField.fieldItem" >
                        <el-form-item :label="field.prop" :key="field.key"
                                      :prop="'fieldItem[' + index + '].value'"
                                      :rules="{required: true, message: field.prop + '不能为空', trigger: 'blur'}">
                            <el-input v-model="formField.fieldItem[index].value"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="fieldValueDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="onSubmitField">确 定</el-button>
            </div>
        </el-dialog>
        <mavon-editor-with-dialog v-if="mavonEditor" :auto-visible.sync="mavonEditor" :originValue="form.goodsDescribe" @save="(text)=>{$message.success('保存成功');form.goodsDescribe = text}"></mavon-editor-with-dialog>
    </div>

</template>

<script>
    import MavonEditorWithDialog from "../../../components/MavonEditorWithDialog";
    import {deepCopy} from "../../../util/MemoryUtil";
    export default {
        name: "AddGood",
        components: {MavonEditorWithDialog},
        props: {
            visible: {type: Boolean, required: true}
        },
        methods: {
            OnEditDetail(){
                this.mavonEditor = true;
            },
            onSubmit(){
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        let payload = deepCopy(this.form);
                        payload['goodsPrice'] = payload.goodsActualPrice;
                        payload.dicCode = payload.dicCode[payload.dicCode.length - 1];
                        this.$http.put('goods/self',payload).then(res=>{
                            const data = res.data;
                            if (data.code === 200){
                                this.$refs.form.resetFields();
                                this.$emit('add-goods', data.data);
                                this.visible__inter = false;
                            }else {
                                this.$message.error("新增商品失败");
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            onSubmitField(){
                this.$refs.formField.validate((valid) => {
                    if (valid) {
                        this.formField.reset = false;
                        this.fieldValueDialogVisible = false;
                    } else {
                        return false;
                    }
                });
            },
            beforeCloseFieldDialog(done){
                this.$confirm('确认关闭？').then(_ => {
                    done();
                }).catch(_ => {});
            },
            onFieldClose(){
                if (this.formField.reset){
                    this.form.dicCode = [];
                    this.form.prop = {};
                    this.$refs.formField.clearValidate();
                }else {
                    this.form.prop = {};
                    //console.log(this.formField.fieldItem);
                    this.formField.fieldItem.map(_=>{
                        this.form.prop[_.prop] = _.value;
                    });
                }
            },
            handleUploadSuccess(res, file) {
                this.form.goodsPic = res.data;
            },
            beforeUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            }
        },
        data(){
            return {
                visible__inter: this.visible,
                fieldValueDialogVisible: false,
                mavonEditor: false,
                fieldValueCache: [],
                formField: {
                    fieldItem: [
                        {prop: '保质期', value: ''},
                        {prop: '生产日期', value: ''},
                        {prop: '厂家', value: ''},
                        {prop: '厂家', value: ''},
                        {prop: '厂家', value: ''},
                        {prop: '厂家', value: ''},
                        {prop: '厂家', value: ''},
                        {prop: '厂家', value: ''},
                    ],
                    reset: true
                },
                rules: {
                    goodsName: [
                        { required: true, message: '请输入商品名称', trigger: 'blur' },
                        { min: 5, max: 80, message: '商品名称需在5-80位', trigger: 'blur' }
                    ],
                    dicCode: [
                        { required: true, message: '请选择商品类型', trigger: 'blur' },
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
                    ],
                    goodsPic: [
                        { required: true, message: '请上传商品封面'},
                    ],
                    goodsDescribe: [
                        { required: true, message: '请输入商品详情'},
                    ]
                },
                form: {
                    goodsName: '',
                    dicCode: [],
                    goodsActualPrice: '',
                    goodsStock: '',
                    goodsPic: '',
                    goodsDescribe: '',
                    prop: {}
                },
                lazyLoadType: {
                    lazy: true,
                    lazyLoad: (node, resolve)=>{
                        let newNode = [];
                        let payload = node.value === undefined? '':node.value;
                        this.$http.get('goods/get_next_variety', {params: {nowCode: payload}}).then(res => {
                            res.data.data.forEach( (item)=>{
                                if (item.fieldValue !== ''){
                                    this.fieldValueCache.push({code: item.code, value: item.fieldValue});
                                }
                                newNode.push({
                                    value: item.code,
                                    label: item.fieldName,
                                    leaf: item.fieldValue !== ''
                                });
                            } );
                        }).then(()=>resolve(newNode));
                    }
                },
            }
        },

        watch: {
            visible__inter(){
                this.$emit('update:visible', this.visible__inter);
            },
            visible(){
                this.visible__inter = this.visible;
            },

            'form.dicCode': {
                handler(val){
                    if (val === undefined || val.length === 0){
                        return;
                    }
                    const fieldValue = this.fieldValueCache.find(_=>{return val[val.length-1] === _.code}).value;
                    let props = fieldValue.split("|");

                    this.formField.fieldItem = [];
                    props.map(_=>{
                        this.formField.fieldItem.push({prop: _, value: ''})
                    });

                    this.fieldValueDialogVisible = true;
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .uploader{
        height: 100px;
        display: flex;
        align-items: center;
        img {
            height: 100px;
        }
    }
</style>

<style>
    .uploader /deep/ .el-form-item__content{
        margin-left: 10px !important;
        width: 80%;
    }
</style>