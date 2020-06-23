<template>
    <el-row class="main-view">
        <el-col :span="6">
            <el-menu
                    :default-active="$route.path"
                    class="el-menu-main"
                    router
                    >
                <el-menu-item index="/user-info">
                    <i class="el-icon-user-solid"></i>
                    <span slot="title">用户信息</span>
                </el-menu-item>
                <el-menu-item index="/user-info/address">
                    <i class="el-icon-position"></i>
                    <span slot="title">地址管理</span>
                </el-menu-item>
            </el-menu>
        </el-col>
        <el-col :span="18">
            <router-view style="padding: 5px;"></router-view>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "MainView"
    }
</script>

<style scoped lang="scss">
    .main-view{
        margin: 0 auto;
        width: 1000px;
        display: flex;
        flex-wrap: wrap;
    }
    .el-menu-main{
        height: 100%;
    }
    .el-menu-item.is-active{
        border-left: 2px rgba(16, 115, 255, 0.63) solid;
        outline: 0;
        background-color: #ecf5ff;
    }
</style>