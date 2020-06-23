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
                        <div class="old-price" v-if="goods.goodsActualPrice !== goods.goodsPrice">
                            <s>¥ {{ goods.goodsPrice | formatPrice }}</s>
                        </div>
                    </div>
                    <div class="item" style="align-items: center">
                        <span>商品类别</span>
                        <span class="good-type">{{ goods.dicCode }}</span>
                    </div>
                    <div class="item" style="height: 32px;">
                        <div>
                            <span>数量</span>
                            <el-input-number size="small" v-model="form.num" :min="0" :max="goods.goodsStock - numInBuyCart" label="描述文字"></el-input-number>
                        </div>
                        <div style="font-size: 10px; align-items: flex-end; margin-left: 10px;">
                            件 (库存{{goods.goodsStock}}件)
                        </div>
                    </div>

                    <div class="item">
                        <el-button style="background-color:#FFD9BC; border-color: #F0CAB6; color: #E5511D; font-size: 15px"
                                    @click="createOrder">立即购买</el-button>
                        <el-button icon="el-icon-shopping-cart-2" style="background-color:#F40; border-color: #F40; color: white; font-size: 15px"
                                    @click="add2BuyCart">加入购物车</el-button>
                    </div>
                </div>
            </div>
            <div class="store">
                <store-card :store="goods.store"></store-card>
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
    import StoreCard from "./StoreCard";
    export default {
        name: "MainView",
        components: {StoreCard},
        methods: {
            async add2BuyCart(){
                if (await this.checkLogin()){
                    if (this.form.num === 0){
                        this.$message.error("请输入商品的数量");
                    }else {
                        // this.$set(this.goods, 'num', this.goods.num === undefined? this.form.num:this.goods.num+this.form.num);
                        // this.$store.commit('addGoods2buyCart', this.goods);
                        this.$http.put("buy_cart", {
                            goodsId:this.goods.id,
                            num: this.form.num
                        }).then(res=>{
                            const data = res.data;
                            if (data.code === 200){
                                this.$store.commit('updateBuyCart',data.data);
                                this.$message.success("加入成功");
                            }else {
                                this.$message.error("加入购物车失败");
                            }
                        });

                        this.form.num = 0; // 清空计数器

                    }
                }
            },
            async createOrder(){
                if (await this.checkLogin()){
                    if (this.form.num === 0){
                        this.$message.error("请输入商品的数量");
                    }else {
                        let order = [
                            {
                                goodsList: [{goodsInfo: this.goods, num: this.form.num}],
                                store: this.goods.store
                            }
                        ];
                        this.$router.push({ name: 'order', params: { goods: order }})
                    }
                }
            },
            async checkLogin(){
                return new Promise((r,j)=>{
                    this.$http.get('/user/is_login').then(res=>{
                        const data = res.data;
                        if (data.code === 200){
                            r(true);
                        }else {
                            window.location.href = "login.html";
                        }
                    }).catch(err=>j(err));
                });
            }
        },
        data(){
            return {
                goods: undefined,
                goodsDescribe: '',
                // numInBuyCart: undefined,
                form: {
                    num: 0
                },
            }
        },
        created() {
            this.$http.get('goods/' + this.$route.params.id).then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.goods = data.data;
                }else {
                    this.$message.error("获取商品信息失败");
                }
            }).then(()=>{
                this.$http.get('goods/get_describe', {params: {id: this.goods.id}}).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.goodsDescribe = data.data;
                    }else {
                        this.goodsDescribe = "<span style='margin: 0 auto; font-size: 30px'>找不到商品描述</span>";
                    }
                });
            }).then(()=>{
                this.$http.get('goods/get_type', {params: {dicCode: this.goods.dicCode}}).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.goods.dicCode = data.data;
                    }else {
                        this.goods.dicCode = "获取商品类型失败";
                    }
                });
            })

        },
        computed: {
            numInBuyCart(){
                const item = this.$store.state.buyCart.find(_=>{return _.store.id === this.goods.store.id});
                if (item !== undefined){
                    const goods = item.goodsList.find(_=>{return _.goodsInfo.id === this.goods.id});
                    return goods === undefined? 0:goods.num;
                }else {
                    return 0;
                }
            },
            compiledMarkdown(){
                return this.$markdownIt.render(this.goodsDescribe);
            }
        },
        watch:{
            'form.num': {
                handler(val){
                    if (val === undefined){
                        this.form.num = 0;
                    }
                }
            },
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
                        color: #6c6c6c; width: 60px; font-size: 12px; display: inline-block
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