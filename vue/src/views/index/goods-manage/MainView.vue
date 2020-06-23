<template>
    <div class="main-view">
        <create-store v-if="store === undefined" @cancel="$router.push('/')" @submit="createStore"></create-store>
        <template v-else>
            <div class="store">
                <div class="name">{{ store.storeName }}</div>
                <div class="describe">{{ store.storeDescribe }}</div>
                <el-rate v-model="store.credit" disabled class="rate"></el-rate>
            </div>
            <add-good :visible.sync="addGoodVisible" @add-goods="t => goods.push(t)"></add-good>
            <el-button type="warning" circle class="add-btn" icon="el-icon-plus" @click="addGoodVisible = !addGoodVisible"></el-button>
            <el-table :data="goods" style="width: 100%">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="table-expand">
                            <el-form-item :label="prop+':'" v-for="([prop, value],i) in Object.entries(props.row.prop)" :key="i">
                                <span>{{ value }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column label="商品名称" prop="goodsName"> </el-table-column>
                <el-table-column label="商品类别" prop="dicCode" min-width="30px"> </el-table-column>
                <el-table-column label="商品现价" prop="goodsActualPrice" min-width="30px"> </el-table-column>
                <el-table-column label="商品原价" prop="goodsPrice" min-width="30px"> </el-table-column>
                <el-table-column label="商品库存" prop="goodsStock" min-width="30px"> </el-table-column>
                <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" @click="onEditDetail(scope.row)">编辑详情</el-button>
                    <el-button size="mini" @click="onEditDescribe(scope.row)">编辑描述</el-button>
                    <el-popconfirm confirmButtonText='确定' cancelButtonText='不用了' icon="el-icon-info" iconColor="red"
                                   title="确定下架该商品吗？" @onConfirm="onDeleteGood(scope.row)">
                        <el-button size="mini" type="danger" slot="reference" style="margin-left: 10px;">下架</el-button>
                    </el-popconfirm>

                </template>
                </el-table-column>
            </el-table>

            <template v-if="goods.length !== 0">
                <edit-good-detail :visible.sync="detailEditVisible" :form="editDetail" @submit="modifyDetail"></edit-good-detail>

                <mavon-editor-with-dialog @save="saveDescribe" v-if="md.visible" :origin-value="md.value" :auto-visible.sync="md.visible"></mavon-editor-with-dialog>
            </template>

        </template>
    </div>
</template>

<script>
    import {deepCopy} from "../../../util/MemoryUtil"

    import CreateStore from "./CreateStore";
    import TableMain from "../../../components/TableMain";
    import EditGoodDetail from "./EditGoodDetail";
    import MavonEditorWithDialog from "../../../components/MavonEditorWithDialog";
    import AddGood from "./AddGood";
import func from '../../../../vue-temp/vue-editor-bridge';

    export default {
        name: "MainView",
        components: {AddGood, MavonEditorWithDialog, EditGoodDetail, TableMain, CreateStore},
        methods: {
            test(e){
                console.log(e);
            },
            createStore(e){
                this.$http.post('store', e).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.store = data;
                        this.$message.success("创建成功");
                    }else {
                        this.$message.error(data.msg);
                    }
                });
            },
            modifyDetail(e){
                this.$http.post('goods/self', {
                    id: e.id,
                    goodsName: e.goodsName,
                    goodsActualPrice: e.goodsActualPrice,
                    goodsStock: e.goodsStock
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("修改成功");
                        this.$set(this.goods, this.goods.findIndex(_=>{return _.id === e.id}), e);
                    }else {
                        this.$message.error("修改失败");
                    }
                });
            },
            onEditDetail(row){
                this.detailEditVisible = true;
                this.editDetail = deepCopy(row);
            },
            onEditDescribe({id}){
                // 从服务器获得描述
                this.$http.get('goods/get_describe',{
                    params: {id: id}
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.md.value = data.data;
                        this.md.visible = true;
                        this.md.id = id;
                    }else {
                        this.$message.error("获取商品描述出错");
                    }
                });

            },

            saveDescribe(text, html){
                this.$http.post('goods/self',{
                    id: this.md.id,
                    goodsDescribe: text
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("保存成功");
                    }else {
                        this.$message.error("保存失败");
                    }
                });
            },

            onDeleteGood({id}){
                this.$http.post('goods/self',{
                    id: id,
                    goodsStatus: false
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("下架成功");
                        this.goods.splice(this.goods.findIndex(_=>_.id === id),1);
                    }else {
                        this.$message.error("下架失败");
                    }
                });

            }
        },
        created(){
            this.$http.get('store').then(res=>{
                const data = res.data;
                if (data.code === 505){
                    this.store = undefined;
                }else {
                    this.store = data.data;
                    this.$http.get('goods/self').then(res=>{
                        const data = res.data;
                        if (data.code === 200){
                            this.goods = data.data;
                        }else {
                            this.$message.error("获取商品信息失败");
                        }

                    })
                }
            })
        },
        data(){
            return {
                store: {},
                detailEditVisible: false,
                addGoodVisible: false,
                editDetail: undefined,
                dicCodeCache: [],

                md: {
                    id: '',
                    visible: false,
                    value: ''
                },

                goods: []
            };
        },
        watch: {
            goods: {
                deep: true,
                handler(){
                    // 吐了吐了 FIXME 如果直接更新会导致异步请求会发送多次(应该有更好的写法)
                    let modifyGoods = deepCopy(this.goods);
                    let modify = false;
                    let requestTask = [];
                    modifyGoods.map(_=>{
                        if (!isNaN(Number(_.dicCode))){
                            requestTask.push(new Promise((r, j)=>{
                                this.$http.get("goods/get_type", {
                                    params: {
                                        dicCode: _.dicCode
                                    }
                                }).then(res=>{
                                    const data = res.data;
                                    r({data: data, id: _.id});
                                }).catch(err=>j(err));
                            }));
                        }
                    });

                    if (requestTask.length === 0){
                        return; //fix 第二次更新
                    }

                    Promise.all(requestTask).then(res=>{
                        res.map(_=>{
                            const data = modifyGoods.find(t=>{return _.id === t.id});
                            if (_.data.data !== data.dicCode){
                                data.dicCode = _.data.code === 200? _.data.data: "获取商品类型失败";
                                modify = true;
                            }

                        });
                    }).then(()=>{
                        if (modify) {
                            this.goods = modifyGoods;
                        }
                    });

                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .main-view{
        margin: 0 auto;
        width: 1000px;
        display: flex;
        flex-wrap: wrap;
        position: relative;
        flex-direction: column;
    }
    .table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 25%;
    }
    .add-btn{
        position: absolute;
        z-index: 2000;
        height: 40px;
        width: 40px;
        right: 10px;
        bottom: 10px;
    }
    .store{
        display: flex;
        align-items: center;
        flex-direction: column;
        .name{
            font-size: 17px;
        }
        .describe{
            font-size: 10px;
            color: rgba(0, 0, 0, .7);
        }
        .rate{
            margin-bottom: 15px;
        }
    }
</style>