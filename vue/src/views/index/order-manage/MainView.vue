<template>
    <div class="main-view">
        <el-tabs v-model="tabActiveName" @tab-click="onClickTab">
            <el-tab-pane label="所有订单" name="all">
                <order-table :orderData="orderData"></order-table>

            </el-tab-pane>
            <el-tab-pane label="未付款" name="0"><el-table empty-text="没有找到订单哦"> </el-table></el-tab-pane>
            <el-tab-pane label="待发货" name="1"><el-table empty-text="没有找到订单哦"> </el-table></el-tab-pane>
            <el-tab-pane label="已发货" name="2"><el-table empty-text="没有找到订单哦"> </el-table></el-tab-pane>
            <el-tab-pane label="已完成" name="3">
                <order-table :orderData="orderData"></order-table>
            </el-tab-pane>
        </el-tabs>
<!--        <goods-show-table></goods-show-table>-->
    </div>
</template>

<script>
    import OrderTable from "./OrderTable";
    export default {
        name: "MainView",
        components: {OrderTable},
        methods: {
            onClickTab(){

            }
        },
        data(){
            return{
                tabActiveName: 'all',
                orderData: {}
            }
        },
        created() {
            this.orderData = this.$http.get('order', {params: {status: this.tabActiveName === 'all'?3:this.tabActiveName}}).then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.orderData = data.data;
                }else {
                    this.$message.error("获取订单信息失败");
                }
            })
        }
    }
</script>

<style scoped lang="scss">
    .main-view{
        margin: 0 auto;
        width: 900px;
        display: flex;
        flex-wrap: wrap;
        flex-direction: column;
    }
</style>

<style>
    .el-tabs__nav-wrap::after{
        z-index: 0;
    }
</style>