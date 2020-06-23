<template>
    <div class="main-view" v-if="goodsList.length !== 0">
        <el-card shadow="never">
            <div class="address">
                <el-divider content-position="left" style="margin-left: 10px;">确认收货地址</el-divider>
                <el-row :gutter="20" v-for="(item,i) in address__draw" :key="i">
                    <el-col :span="6" v-for="address in item" :key="address.id">
                        <address-item :address="address" :selected="addressActiveNum[address.id]" @click.native="selectAddress(address.id)"></address-item>
                    </el-col>
                </el-row>

            </div>
            <div class="goods-info">
                <el-divider content-position="left" style="margin-left: 10px;">确认商品信息</el-divider>
                <goods-show-table :goods-list="goodsList"></goods-show-table>
            </div>
        </el-card>
        <div class="confirm">
            <div class="info">
                <div>
                    <span>实付款:</span>
                    <span style="color: #999; font-size: 26px">¥</span>
                    <span style="color: rgb(255, 80, 0); font-size: 26px; font-weight: bolder;margin-left: 5px;">
                        {{moneyAll | formatPrice}}
                    </span>
                </div>
                <div>
                    <span>寄送至:</span>
                    <span style="font-size: 12px; color: rgb(102, 102, 102)">
                        {{activeAddress.addressNum}} {{activeAddress.addressDetail}}
                    </span>
                </div>
                <div>
                    <span>收货人:</span>
                    <span style="font-size: 12px; color: rgb(102, 102, 102)">
                        {{activeAddress.name}} {{activeAddress.telephone}}
                    </span>
                </div>
            </div>
            <div>
                <el-button class="submit" style="margin-left: 10px;" @click="submit">提交订单</el-button>
            </div>
        </div>

    </div>
</template>

<script>
    import GoodsShowTable from "../../../components/main-page/index/GoodsShowTable";
    import AddressItem from "./AddressItem";

    export default {
        name: "MainView",
        components: {AddressItem, GoodsShowTable},
        methods: {
            submit(){
                let goodsInOrder = [];
                this.goodsList.forEach(store=>{
                    store.goodsList.forEach(_=>{
                        goodsInOrder.push({id: _.goodsInfo.id,num: _.num,totalMoney: _.goodsInfo.goodsActualPrice*_.num})
                    })
                });
                this.$http.post('order', {
                    address: this.activeAddress.addressNum + this.activeAddress.addressDetail,
                    name: this.activeAddress.name,
                    telephone: this.activeAddress.telephone,
                    goodsInOrder: goodsInOrder,
                    fromBuyCart: this.fromBuyCart
                },{headers: {'Content-Type': 'application/json'}}).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        if (this.fromBuyCart) {
                            this.$http.get("/buy_cart").then(res=>{
                                this.$store.commit('updateBuyCart', res.data.data);
                            });
                        }
                        this.$router.push('/order-manage');
                    }else {
                        this.$message.error("创建订单失败, 请重试");
                    }
                });
            },
            selectAddress(id){
                const trueId = Object.entries(this.addressActiveNum).find(_=>{return _[1]})[0];
                this.addressActiveNum[trueId] = false;
                this.addressActiveNum[id] = true;
            }
        },
        created(){
            this.goodsList = this.$route.params.goods === undefined? []:this.$route.params.goods;
            this.fromBuyCart = this.$route.params.fromBuyCart !== undefined;
            this.$http.get('address').then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.address = data.data;
                }else {
                    this.$message.error("获取地址错误");
                }
            }).then(()=>{
                let payload = [];
                this.address.forEach(item => {
                    payload.push(item.addressNum);
                });
                this.$http.post('http://localhost:9090/mget_info', {
                    a: payload
                }, {headers: {'Content-Type': 'application/json'}} ).then(res=>{
                    this.address.forEach((item, i)=> {
                        item.addressNum = res.data[i];
                    });
                })
            }).then(()=>{
                let ret = {};
                this.address.map(_=>{
                    ret[_.id] = _.defaultStatus;
                });
                this.addressActiveNum = ret;
            });

        },
        data(){
            return{
                goodsList: [],
                address: [],
                addressActiveNum: {},
                fromBuyCart: false,
            }
        },
        computed: {
            address__draw(){
                let array = [];
                let len = this.address.length / 4;
                let i;
                for (i=1; i<=len; ++i){
                    array.push(this.address.slice((i-1)*4,i*4));
                }

                array.push(this.address.slice((i-1)*4));
                return array;
            },
            moneyAll(){
                let money = 0;
                this.goodsList.map(_=>{
                    _.goodsList.map(item=>{
                        money += item.num*item.goodsInfo.goodsActualPrice;
                    });
                });
                return money;
            },
            activeAddress(){
                let array = Object.entries(this.addressActiveNum);
                if (array.length !== 0){
                    const trueId = array.find(_=>{return _[1]})[0];
                    return this.address.find(_=>{return _.id === parseInt(trueId)});
                }else {
                    return {}
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
    .address{
        &>.el-row:not(:nth-child(2)){
            margin-top: 20px;
        }
    }
    .confirm{
        display: flex;
        justify-items: flex-end;
        flex-direction: column;
        margin-left: auto;
        margin-top: 10px;
        margin-bottom: 50px;
        &>div{
            display: flex;
            flex-direction: column;
            justify-items: flex-end;
            margin-left: auto;
        }
        .info{
            display: flex;
            flex-direction: column;
            border: 1px solid #ff0036;
            box-shadow: 0 0 0 5px #fff0e8 inset;
            padding: 10px 10px 10px 20px;
            min-width: 350px;
            &>div>span:first-child{
                color: #333;
                margin-right: 5px;
                font-size: 13px;
            }
            &>div{
                display: flex;
                justify-items: flex-end;
                margin-left: auto;
                align-items: center;
            }
            &>div:not(:first-child){
                margin-top: 5px;
            }
        }
    }

    button.submit{
        height: 100%;
        width: 180px;
        background-color: rgb(255, 80, 0);
        color: #fff;
        border-radius: 0;
        font-family: 'Lantinghei SC','Microsoft Yahei',serif;
        font-size: 14px;
        border: 0;
        &:disabled{
            background-color: #B0B0B0;
        }
    }
</style>