<template>
    <div class="main-view">
        <el-row :gutter="20" v-for="(item,i) in goodsInfo__draw" :key="i">
            <el-col :span="6" v-for="{goodsActualPrice,goodsName,store,goodsPic,id} in item" :key="id">
                <GoodsCard :price=goodsActualPrice :name=goodsName :store-name=store.storeName :pic=goodsPic :linkId="id"></GoodsCard>
            </el-col>
        </el-row>

        <el-pagination
                background
                layout="prev, pager, next"
                :total=maxCount
                :current-page.sync="nowPage" @current-change="onPageChange"
        style="margin: 50px auto 0;">
        </el-pagination>
    </div>

</template>

<script>
    import GoodsCard from "../../components/main-page/index/GoodsCard";
    export default {
        name: "MainView",
        components: {GoodsCard},
        methods: {
            onPageChange(){
                this.$http.get('goods', {params: {pageNum: this.pageNum, page:this.nowPage-1}}).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.goodsInfo = data.data;
                    }else {
                        this.$message.error("查找商品失败");
                    }
                });
            }

        },
        data(){
            return {
                goodsInfo: [],
                pageNum: 12,
                nowPage: 1,
                maxCount: 0,
            };
        },
        created() {
            this.$http.get('goods/count').then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.maxCount = data.data - 2;
                }else {
                    this.$message.error("查找商品总数失败");
                }
            }).then(()=>{
                this.$http.get('goods', {params: {pageNum: this.pageNum, page:this.nowPage-1}}).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.goodsInfo = data.data;
                    }else {
                        this.$message.error("查找商品失败");
                    }
                });
            })
        },
        computed: {
            goodsInfo__draw(){
                let array = [];
                let len = this.goodsInfo.length / 4;
                let i;
                for (i=1; i<=len; ++i){
                    array.push(this.goodsInfo.slice((i-1)*4,i*4));
                }

                array.push(this.goodsInfo.slice((i-1)*4));
                return array;
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

        &>.el-row:not(:first-child) {
            margin-top: 20px;
        }
    }
    .el-menu-main{
        height: 100%;
    }
    .el-menu-item.is-active{
        border-left: 2px rgba(16, 115, 255, 0.63) solid;
        outline: 0;
        background-color: #ecf5ff;
    }


    .el-row{
        width: 100%;
    }
</style>