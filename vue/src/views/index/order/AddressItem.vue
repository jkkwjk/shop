<template>
    <div :class="{'address-item':true, 'address-item-active':selected}">
        <div style="color: #666; font-size: 12px">{{ address.addressNum }}</div>
        <el-divider content-position="left"></el-divider>
        <div style="font-size: 13px; word-break: break-all">{{ address.addressDetail }}
            <span style="font-weight: bolder">({{ address.name }}收) {{ address.telephone }}</span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AddressItem",
        props: {
            address: {required: true, type: Object},
            selected: {default: false}
        }
    }
</script>

<style scoped lang="scss">
    .address-item{
        height: 100px;
        overflow: hidden;
        text-overflow: ellipsis;
        padding: 10px 15px;
        box-sizing: border-box;
        background: url("/img/address-no-active-bg.png");
        background-size: 100% 100%;
    }

    .address-item-active{
        background: url("/img/address-active-bg.png");
        background-size: 100% 100%;
    }

    .el-divider{
        margin: 5px 0;
    }
</style>