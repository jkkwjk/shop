<template>
    <div class="order-table">
        <template v-for="{address,name,createTime,status,telephone,id,buyCartItemList} in orderData">
            <el-card class="order-item" shadow="hover">
                <div class="order-info">
                    <span>订单创建时间: {{createTime}}</span>
                    <span style="margin-left: 30px;">配送地址: {{address}}</span>
                    <span style="margin-left: 10px;">{{name + '('+telephone+ ')'}}收</span>
                </div>
                <goods-show-table empty-text="没有找到订单哦" :goods-list="buyCartItemList" custom-operating="true">
                    <template slot="operating" slot-scope="{scope}">
                        <el-link class="common" :underline="false"
                                 @click="$router.push({name: 'goods-snapshot', params: {goods: scope.row.goodsInfo, orderId: id, createTime: createTime, status: status}})">查看交易快照</el-link>
                    </template>
                </goods-show-table>
            </el-card>

        </template>
    </div>
</template>

<script>
    import GoodsShowTable from "../../../components/main-page/index/GoodsShowTable";
    export default {
        name: "OrderTable",
        components: {GoodsShowTable},
        props: {
            orderData: {required: true}
        },
        data(){
            return{

            }
        },
        created() {

        }
    }
</script>

<style scoped lang="scss">
    .order-info{
        height: 15px;
        line-height: 15px;
        display: flex;
        align-items: center;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    .order-item{
        margin-top: 15px;
    }
</style>