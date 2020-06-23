<template>
    <el-popover
            placement="bottom"
            width="300"
            trigger="hover"
            transition="fade-small"
            :open-delay="300"
            :close-delay="50">
        <slot class="tip"></slot>
        <a :href="link" slot="reference"> <div>{{ title }}</div> </a>
    </el-popover>
</template>

<script>
    export default {
        name: "NavLink",
        props: {
            title: {type: String, required: true},
            link: {type: String, required: true}
        }
    }
</script>

<style scoped lang="scss">
    .animated{
        animation-duration: 200ms;
    }
</style>

<style lang="scss">
    @import "../../../assets/css/custom-animate";
    .fade-small-enter-active {
        animation-duration: 200ms;
        animation-name: fadeInDownSmall;
    }

    .fade-small-leave-active {
        animation-duration: 200ms;
        animation-name: fadeOutUpSmall;
    }
</style>

<style>
    .el-popover{
        max-height: 200px;
        overflow: scroll;
    }
</style>