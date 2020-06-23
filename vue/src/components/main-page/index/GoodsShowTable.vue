<template>
    <div>
        <template v-for="({data},index) in storeList" v-if="goodsList.length !== 0">
            <div :class="{'no-padding-store-name': !enableSelect}" style="background-color: #e5e5e5;margin-top: 20px; padding: 10px 65px; color: #3c3c3c; font: 12px/1.5 tahoma,arial,'Hiragino Sans GB','\5b8b\4f53',sans-serif">店铺： {{ data.storeName }}</div>
            <el-table ref="buyCartTable" :data="goodsList[index].goodsList" @selection-change="e => $emit('selection-change', e, index)" :empty-text="emptyText">
                <el-table-column type="selection" width="55" v-if="enableSelect"> </el-table-column>

                <el-table-column label="商品信息" width="300px">
                    <template slot-scope="scope">
                        <div class="goods-info fill-box">
                            <div class="goods-image">
                                <img :src="scope.row.goodsInfo.goodsPic" alt="">
                            </div>
                            <div class="goods-name">
                                {{ scope.row.goodsInfo.goodsName }}
                            </div>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column label="单价">
                    <template slot-scope="scope">
                        <div class="fill-box">
                            <div style="color: black; font-weight: bolder; font-size: 15px;">
                                ¥ {{ scope.row.goodsInfo.goodsActualPrice | formatPrice }}
                            </div>
                            <div style="font-size: 12px; color: #9c9c9c;" v-if="scope.row.goodsInfo.goodsPrice != null && scope.row.goodsInfo.goodsActualPrice !== scope.row.goodsInfo.goodsPrice">
                                <s>¥  {{ scope.row.goodsInfo.goodsPrice | formatPrice }}</s>
                            </div>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column label="数量">
                    <template slot-scope="scope">
                        <div class="fill-box">
                            {{ scope.row.num }} 件
                        </div>
                    </template>
                </el-table-column>

                <el-table-column label="金额">
                    <template slot-scope="scope">
                        <div class="fill-box" style="color: #f40; font-weight: bolder; font-size: 15px;">
                            ¥ {{ scope.row.goodsInfo.goodsActualPrice*scope.row.num | formatPrice }}
                        </div>
                    </template>
                </el-table-column>

                <el-table-column label="操做" v-if="customOperating">
                    <template slot-scope="scope">
                        <div class="fill-box" style="font-weight: bolder; font-size: 15px;">
                            <slot name="operating" :scope="scope"></slot>
                        </div>
                    </template>
                </el-table-column>

            </el-table>
        </template>
        <el-table v-if="goodsList.length === 0" :empty-text="emptyText"></el-table>
    </div>
</template>

<script>
    export default {
        name: "GoodsShowTable",
        props: {
            goodsList: {required: true},
            enableSelect: {default: false},
            customOperating: {default: false},
            emptyText: {default: "暂无数据"}
        },
        methods: {
            toggleRowAll(e){
                if (e){
                    this.$store.state.buyCart.forEach((buyCartItem, i)=>{
                        buyCartItem.goodsList.forEach(goodsItem=>{
                            this.$refs.buyCartTable[i].toggleRowSelection(goodsItem, true);
                        })
                    });
                }else {
                    this.$refs.buyCartTable.forEach(item=>{
                        item.clearSelection();
                    })
                }
            },
        },
        computed: {
            storeList(){
                let ret = [];
                this.goodsList.forEach(item=>{
                    ret.push({data: item.store});
                });
                return ret;
            },
        }
    }
</script>

<style scoped lang="scss">
    .no-padding-store-name{
        padding: 10px 10px !important;
    }

    .goods-info{
        display: flex;
        .goods-image{
            &>img{
                 width: 70px;
                 height: 70px;
            }
        }
        .goods-name{
            overflow: hidden;
            text-overflow: ellipsis;
            max-height: 45px;
            margin-left: 20px;
            font-size: 12px;

        }
    }

    .fill-box{
        height: 70px;
        line-height: 15px;
    }

    .el-table{
        flex: unset;
    }
    .common.el-link{
        &:hover{
            color: #606266;
            border-bottom: 1px solid #606266;
        }
    }
</style>