<template>
    <div class="store-card">
        <div>{{ store.storeName }}</div>
        <div class="item">
            <span>信誉: </span>
            <el-rate v-model="store.credit" disabled style="display: inline-block"></el-rate>
        </div>
        <div class="warp">
            <span>
                <span style="color: #666; font-size: 12px;">掌柜: </span>
                <span style="color: #3C3C3C; margin-left: 5px;">{{ storeOwnerName }}</span>
            </span>
        </div>

        <div class="warp">
            <span>
                <span style="color: #666; font-size: 12px;">店铺描述: </span>
                <span style="color: #3C3C3C; margin-left: 5px;">{{ store.storeDescribe }}</span>
            </span>
        </div>
        <div class="mask"></div>
    </div>
</template>

<script>
    export default {
        name: "StoreCard",
        props: {
            store: {required: true}
        },
        data(){
            return {
                storeOwnerName: ''
            }
        },
        created() {
            this.$http.get('store/get_owner_name', { params: {id: this.store.id} }).then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.storeOwnerName = data.data;
                }else {
                    this.storeOwnerName = "找不到店铺所有者";
                }
            })
        }
    }
</script>

<style scoped lang="scss">
    .store-card{
        border: 1px solid #ffe2a3;
        height: 100%;
        width: 100%;
        box-sizing: border-box;
        position: relative;
        padding: 50px 20px 20px;
        word-break: break-all;
    }

    .item{
        display: flex;
        margin-top: 10px;
        align-items: center;
        &>span:first-child{
            width: 35px;
            color: #666;
            font-size: 12px;
        }
    }
    .warp{
        display: flex;
        align-items: center;
        margin-top: 10px;
    }
    .mask{
        position: absolute;
        background: linear-gradient(rgba(255, 230, 144, 0.8), transparent);
        height: 70px;
        width: 100%;
        top: 0;
        left: 0;
        z-index: -1;
    }
</style>