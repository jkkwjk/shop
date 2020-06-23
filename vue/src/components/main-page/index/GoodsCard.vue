<template>
    <div class="goods-card" @click="toLink">
        <el-card shadow="hover">
            <div class="pic">
                <img :src="pic" alt=""/>
            </div>
            <div class="text">
                <div class="price">¥<span style="margin-left: 5px;">{{ price | formatPrice }}</span></div>
                <div class="name">{{ name }}</div>
                <div class="store">{{ storeName }}</div>
            </div>
        </el-card>
    </div>

</template>

<script>
    export default {
        name: "GoodsCard",
        props: {
            pic: {type: String, default: "/dev/1.jpg"},
            price: {type: [String,Number]},
            name: {type: String},
            storeName: {type: String},
            linkId: {type: Number, required: true}
        },
        methods: {
            toLink(){
                window.open("#/detail/" + this.linkId);
            },
        }
    }
</script>

<style scoped lang="scss">
    .goods-card{
        display: flex;
        flex-direction: column;
        height: 100%;
        width: 100%;
    }

    .pic{
        img{
            height: 200px;
            width: 100%;
        }
    }

    .text{
        flex: 1;
        padding: 5px 10px;
        .price{
            color: #F40;
            font-weight: bolder;
            font-size: 16px;
        }
        .name{
            font-weight: lighter;
            font-size: 10px;
            height: 32px;
            overflow: hidden;
        }
        .store{
            color: #888;
            font-size: 10px;
            margin-top: 10px;
        }
    }
</style>

<style scoped>
    .el-card /deep/ .el-card__body{
        padding: 0;
    }
</style>