<template>
    <div class="main-view">
        <goods-show-table :goods-list="$store.state.buyCart" enable-select="true" custom-operating="true"
                          @selection-change="(e,i) => onSelectionChange(e,i)" ref="goodsShowTable">
            <template slot="operating" slot-scope="{scope}">
                <el-link class="common" :underline="false" @click="deleteBuyCartItem(scope.row.goodsInfo.id)">删除</el-link>
            </template>
        </goods-show-table>

        <div class="tool-bar" v-if="$store.state.buyCart.length !== 0">
            <div style="flex: 1">
                <el-checkbox @change="selectALL"></el-checkbox> <span style="margin-left: 5px;color: #3c3c3c">全选</span>
            </div>
            <div>
                <div>已选商品 <span style="color: #f40; font-size: 18px; font-weight: bolder">{{ selectGoodsNum }}</span> 件</div>
                <div style="margin-left: 50px">合计： <span style="color: #f40; font-size: 18px; font-weight: bolder">{{ selectGoodsMoney | formatPrice }}</span> </div>
                <el-button class="submit" style="margin-left: 10px;" :disabled="selectGoodsAll.length === 0" @click="createOrder">结算</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    import GoodsShowTable from "../../../components/main-page/index/GoodsShowTable";
    import {deepCopy} from "../../../util/MemoryUtil";
    export default {
        name: "MainView",
        components: {GoodsShowTable},
        methods: {
            createOrder(){
                let order = [];
                this.selectGoodsList.forEach(store=>{
                    let goodsList = [];
                    store.goodsList.forEach(goods=>{
                        goodsList.push(deepCopy(goods));
                    });
                    if (goodsList.length !== 0){
                        order.push({
                            store: this.$store.state.buyCart[store.i].store,
                            goodsList: goodsList
                        });
                    }
                });
                if (order.length !== 0){
                    this.$router.push({ name: 'order', params: { goods: order , fromBuyCart: true}})
                }
            },
            onSelectionChange(e,i){
                let item = this.selectGoodsList.find(_=>{return _.i === i});
                if (item === undefined){
                    this.selectGoodsList.push({i: i, goodsList: e});
                    this.selectGoodsList.sort((a,b)=>{
                        return a.i-b.i;
                    });
                }else {
                    this.$set(item, 'goodsList', e);
                }
            },
            selectALL(e){
                this.$refs.goodsShowTable.toggleRowAll(e);
            },

            deleteBuyCartItem(e){
                this.$http.delete('buy_cart', {params: {goodsId: e}}).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$store.commit('updateBuyCart', data.data);
                        this.$message.success("操作成功");
                    }else {
                        this.$message.error("删除失败");
                    }
                });
            }

        },
        created(){
            this.$http.get('buy_cart').then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.$store.commit('updateBuyCart', data.data);
                }else {
                    this.$message.error("获得购物车信息失败");
                }
            });
        },
        data(){
            return{
                selectGoodsList: [],
                selectGoodsAll: []
            }
        },
        computed: {
            selectGoodsNum(){
                let num = 0;
                this.selectGoodsList.map(_=>{
                    _.goodsList.map(item=>{
                        num += item.num;
                    });
                });
                return num;
            },
            selectGoodsMoney(){
                let money = 0;
                this.selectGoodsList.map(_=>{
                    _.goodsList.map(item=>{
                        money += item.num*item.goodsInfo.goodsActualPrice;
                    });
                });
                return money;
            },
        },
        watch: {
            selectGoodsList: {
                deep: true,
                handler(val){
                    let ret = [];
                    val.forEach(item=>{
                        item.goodsList.forEach(goodsItem=>{
                            ret.push(goodsItem);
                        })
                    });
                    this.selectGoodsAll = ret;
                }
            }
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

    .tool-bar{
        font: 12px/1.5 tahoma,arial,'Hiragino Sans GB','\5b8b\4f53',sans-serif;
        background-color: #e5e5e5;
        height: 50px;
        margin-top: 20px;

        display: flex;
        align-items: center;
        padding: 0 0 0 10px;

        &>div{
            display: flex;
            align-items: center;
            height: 100%;
        }
        button.submit{
            height: 100%;
            width: 150px;
            background-color: #f40;
            color: #fff;
            border-radius: 0;
            font-family: 'Lantinghei SC','Microsoft Yahei',serif;
            font-size: 20px;
            border: 0;
            &:hover{
                background-color: #f22d00;
            }
            &:disabled{
                background-color: #B0B0B0;
            }
        }
    }

</style>

<style>
    .el-table--border::after, .el-table--group::after, .el-table::before{
        z-index: 0;
    }
</style>