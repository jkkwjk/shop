<template>
    <div class="avatar" @mouseleave="showDetail = false">

        <a href="#" style="display: flex;" @mousemove="showDetail = true"
           :class="{'nav-avatar':true, 'nav-avatar-hover':showDetail}">
            <el-avatar src="/dev/noface.gif" style="background-color:white;"></el-avatar>
        </a>
        <transition enter-active-class="animated fadeInDownSmall" leave-active-class="animated fadeOutUpSmall">
            <el-card class="detail" v-show="showDetail">
                <div style="text-align: center; margin-top: 10px;">{{ name }}</div>
                <div class="btn-list">
                    <el-dropdown-item :icon="icon" v-for="{icon,text, index} in btnList"
                        @click.native="$router.push(index)" :key="text">{{ text }}</el-dropdown-item>
                </div>
            </el-card>
        </transition>
    </div>
</template>

<script>
    export default {
        name: "UserAvatar",
        props: {
            name: {required: true},
            summary: {type: Object, required: true},
        },
        data(){
            return {
                showDetail: false,
                btnList: [{icon: 'el-icon-user-solid',text: '个人中心', index: '/user-info'},
                    {icon: 'el-icon-s-order',text: '订单中心', index: '/order-manage'},
                    {icon: 'el-icon-s-cooperation',text: '商品管理', index: '/goods-manage'}]
            }
        },
        methods: {

        }
    }
</script>

<style scoped lang="scss">
    @import '../../../assets/css/custom-animate';
    .avatar{
        position: relative;
        width: 40px;
        height: 40px;
    }
    .animated{
        animation-duration: 200ms;
    }
    .detail{
        position: absolute;
        top: 40px;
        left: -130px;
        width: 300px;
        background-color: #fff;
        color: #212121;
        .btn-list{
            font-weight: normal;
            padding-top: 10px;
        }
        .summary{
            border-top: 1px solid rgba(0,0,0,.1);
            border-bottom: 1px solid rgba(0,0,0,.1);
            display: flex;
            flex-direction: row;
            margin-top: 30px;
            padding: 10px 20px;
            .item{
                display: flex;
                align-items: center;
                justify-items: center;
                flex-direction: column;
                flex: 1;
                height: 30px;
                .text{
                    font-weight: normal;
                    font-size: 12px;
                    color: #aaaaaa;
                }
                .number{
                    font-size: 16px;
                }
            }
        }
    }
    .nav-avatar{
        position: absolute;
        transition: all 200ms;
        z-index: 2;
    }
    .nav-avatar-hover{
        top: 10px;
        transform:scale(1.5);
    }

    .el-dropdown-menu__item{
        height: 50px;
        line-height: 50px;
        transition: background-color 0.2s;
        &:hover{
            background-color: #f4f4f4 !important;
            color: #212121 !important;
        }
        &:focus{
            background-color: #f4f4f4 !important;
            color: #212121 !important;
        }
    }
</style>

<style scoped>
    .detail /deep/ .el-card__body{
        padding: 20px 0;
    }
</style>