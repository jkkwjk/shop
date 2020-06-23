<template>
    <div id="header">
        <div class="contain">
            <div class="home">
                <a href="#" @click.prevent="toIndex">
<!--                    <i class="bilifont bili-icon_dingdao_zhuzhan"></i>-->
                    <span style="margin-left: 5px;">主站</span>
                </a>
            </div>

            <div class="search-box" v-show="$route.path==='/'">
                <el-input placeholder="搜索..." v-model="searchText">
                    <el-button slot="append" icon="el-icon-search"
                               @click="searchClick" class="nav-search-btn"></el-button>

                </el-input>
            </div>

            <div class="user-con" style="margin-right: 100px;">
                <template v-if="$store.state.userInfo.name !== undefined">
                    <user-avatar style="margin-right: 30px;" :name="$store.state.userInfo.nickName"
                                :summary="{sold: 1, selling: 0, buy: 3}"></user-avatar>

                    <nav-link title="购物车" link="#/buy-cart" v-if="$store.state.buyCart.length !== 0">
                        <el-table :data="goodsInBuyCart" style="font-size: 13px;">
                            <el-table-column width="150px" label="商品名称">
                                <template slot-scope="scope">
                                    <div class="goods-info">
                                        {{ scope.row.goodsInfo.goodsName }}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column min-width="70px" property="goodsInfo.goodsActualPrice" label="价格"></el-table-column>
                            <el-table-column min-width="70px" property="num" label="数量"></el-table-column>
                        </el-table>
                    </nav-link>
                    <a href="#/buy-cart" v-else> <div>购物车</div> </a>
                </template>
               <el-button class="custom-btn" v-else @click="toLogin">登录</el-button>
            </div>
        </div>

        <div class="mask"></div>
    </div>
</template>

<script>
    import UserAvatar from "./UserAvatar";
    import NavLink from "./NavLink";
    export default {
        name: "Header",
        components: {NavLink, UserAvatar},
        data(){
            return {
                searchText: ''
            }
        },
        methods: {
            toIndex(){
                this.$router.push('/');
            },
            toLogin(){
                window.location.href = 'login.html';
            },
            searchClick(){
                alert("1");
            }
        },
        computed: {
            goodsInBuyCart(){
                let goodsAll = [];

                this.$store.state.buyCart.forEach(item=>{
                    item.goodsList.forEach(goods=>{
                        goodsAll.push(goods);
                    })
                });

                return goodsAll;
            }
        }
    }
</script>

<style scoped lang="scss">
    .custom-btn{
        font-weight: bolder;
        background-color: #fb7299;
        color: white;
        border: 0;
        margin-left: 30px;
        &:hover{
            background-color: #fb7299;
            color: white;
        }
        &:focus{
            background-color: #fb7299;
            color: white;
        }
    }

    .goods-info{
        max-height: 40px;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    #header{
        position: relative;
        .contain{
            padding: 20px 20px 20px 30px;
            display: flex;
            align-items: center;
            position: relative;
            z-index: 1;
            height: 110px;
            box-sizing: border-box;
            .home{

                font-weight: bolder;
                flex: 1;
            }

            .search-box{
                display: flex;
                width: 50%;
                max-width: 600px;
                flex: 1;
            }

            .user-con{
                flex: 1;
                display: flex;
                justify-content: flex-end;
                font-weight: bolder;
                align-items: center;
                color: white;
            }
        }
    }


    .nav-search-btn:hover{
        color: #00a1d6;
    }

    .mask{
        position: absolute;
        height: 100px;
        width: 100%;
        top: 0;
        left: 0;
        background: linear-gradient(rgba(0,0,0,.3),transparent);
    }
</style>