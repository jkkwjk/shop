<template>
    <div class="main-view">
        <div class="brief" v-if="goods !== undefined">
            <div class="main">
                <div class="pic">
                    <img :src="goods.goodsPic" alt="">
                </div>
                <div class="info">
                    <div style="font-size: 16px">{{ goods.goodsName }}</div>
                    <div class="item price">
                        <div class="actual-price">
                            <span>价格</span>
                            ¥ {{ goods.goodsActualPrice | formatPrice }}
                        </div>
                    </div>
                    <div class="item" style="align-items: center">
                        <span>商品类别</span>
                        <span class="good-type">{{ goods.dicCode }}</span>
                    </div>
                    <div class="item" style="align-items: center">
                        <span>快照创建时间</span>
                        <span class="good-type">{{ createTime }}</span>
                    </div>
                    <div class="item" style="align-items: center">
                        <span>订单状态</span>
                        <span class="good-type">{{ status }}</span>
                    </div>
                </div>
                <div class="store">

                </div>
            </div>
        </div>

        <div class="detail">
            <el-form label-position="left" inline class="goodsProp" v-if="goods !== undefined && goods.prop !== undefined">
                <el-form-item :label="prop+':'" v-for="([prop, value],i) in Object.entries(goods.prop)" :key="i" style="width: 50%; margin: 0">
                    <span>{{ value }}</span>
                </el-form-item>
            </el-form>
            <div class="markdown" v-html="compiledMarkdown"></div>
        </div>
    </div>

</template>

<script>
    export default {
        name: "MainView",
        methods: {
        },
        data(){
            return {
                goods: undefined,
                goodsDescribe: '',
                createTime: '',
                status: ''
            }
        },
        created() {
            this.goods = this.$route.params.goods;
            this.createTime = this.$route.params.createTime;
            this.status = this.$route.params.status;

            if (this.createTime !== undefined){
                this.$http.get('order/get_describe_in_snapshot', {params: {
                        orderId: this.$route.params.orderId,
                        goodsId: this.goods.id
                    }}).then(res=>{
                        const data = res.data;
                        if (data.code === 200){
                            this.goodsDescribe = data.data;
                        }else {
                            this.$message.error("获取商品信息出错");
                        }
                });
            }

        },
        computed: {
            compiledMarkdown(){
                return this.$markdownIt.render(this.goodsDescribe);
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

    .brief{
        display: flex;
        /*height: 600px;*/
        .main{
            display: flex;
            flex: 1;
            padding: 20px;
            border: 1px solid #E8E8E8;
            .pic{
                flex: 1;
                img{
                    height: 300px;
                    width: 100%;
                }
            }
            .info{
                flex: 1;
                padding: 0 20px;
                .item{
                    margin-top: 10px;
                    padding: 5px 10px;
                    display: flex;
                    &>*{
                        display: flex;
                        align-items: center;
                    }
                    & span:first-child{
                        color: #6c6c6c; width: 100px; font-size: 12px; display: inline-block
                    }
                }
                .price{
                    background-color: #FFF2E8;
                    .actual-price{
                        font-size: 24px;
                        font-weight: bolder;
                        color: #F40;
                    }

                    .old-price{
                        font-size: 12px;
                        align-items: flex-end;
                        margin-left: 5px;
                        color: #9c9c9c;
                    }
                }
                .good-type{
                    font-size: 15px;
                }
            }
        }
        .store{
            width: 200px;
        }
    }

    .detail{
        .goodsProp{
            margin-right: 0;
            margin-bottom: 0;

        }

        margin-top: 30px;
        border: 1px solid #E8E8E8;
        padding: 10px;
    }
</style>