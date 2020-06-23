<template>
    <el-table
            :data="tableData"
            :row-class-name="tableRowClassName"
            v-el-table-infinite-scroll="load? load:null"
            infinite-scroll-distance="200"
            @sort-change="willSort__inter"
            :height="subHeight? `calc(100% - ${subHeight})`:'100%'" :class="{'table':true,'no-scroll':noScroll}">
        <el-table-column type="expand" fixed v-if="hiddenColumn">
            <template slot-scope="props">
                <el-form label-position="left" inline class="table-expand">
                    <el-form-item :label="title+':'" v-for="{title,prop} in column" :key="prop">
                        <span>{{ props.row[prop] }}</span>
                    </el-form-item>
                    <el-form-item :label="title+':'" v-for="{title,prop} in hiddenColumn" :key="prop">
                        <span>{{ props.row[prop] }}</span>
                    </el-form-item>
                </el-form>
            </template>
        </el-table-column>

        <el-table-column :label="title" :prop="prop"
                         v-for="{title, prop, columnWidth} in column"
                         :key="prop" :sortable="willSort && unSortList.some((item)=>item === prop)? 'custom': undefined"
                         :min-width="customColumnMinWidth? (columnWidth === undefined? columnMinWidth:columnWidth):columnMinWidth">
        </el-table-column>

        <el-table-column fixed="right" label="操作" prop="showWhat" :sortable="willSort? 'custom': undefined" :width="customButtonWidth" v-if="buttonClick || customButtonClick">
            <template slot-scope="scope">
                <div class="btn-group" v-if="!customButtonClick">
                    <el-button type="text" size="small" v-if="scope.row.showWhat === 'button'" @click="buttonClick(scope.row,'allow')">同意</el-button>
                    <el-button type="text" size="small" style="color: red" v-if="scope.row.showWhat === 'button'" @click="buttonClick(scope.row,'reject')">拒绝</el-button> <!--拒绝原因-->
                    <span v-if="scope.row.showWhat === 'reject'" style="color: red;">已拒绝</span>
                    <span v-if="scope.row.showWhat === 'allow'" style="color: darkgreen;">已同意</span>
                </div>
                <div class="btn-group" v-else>
                    <slot name="button" :data="scope"></slot>
                </div>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
    import elTableInfiniteScroll from 'el-table-infinite-scroll';
    export default {
        name: "TableMain",
        directives: {
            'el-table-infinite-scroll': elTableInfiniteScroll
        },
        props: {
            tableData: {type: Array, require: true},
            column: {type: Array, require: true},
            hiddenColumn: {type: Array}, //可以下拉的
            willSort: {type: Function},
            unSortList: {type: Array, default: ()=>[]}, //不排序的字段
            subHeight: {type: String}, //fix 全屏问题
            noScroll: {type: Boolean},
            load: {type: Function},
            buttonClick: {type: Function},
            customButtonClick: {type: Boolean},
            customButtonWidth: {type: String, default: '100px'},
            columnMinWidth: {type: String, default: '110px'},
            customColumnMinWidth: {type: Boolean},
            bgcAuto: {type: Boolean, default: true}
        },
        methods: {
            willSort__inter({ prop, order }){
                this.willSort({ prop, order });
            },
            tableRowClassName({row}){
                if (this.bgcAuto){
                    switch (row.showWhat) {
                        case "reject":
                            return 'reject-row';
                        case "allow":
                            return 'allow-row';
                        case "wait":
                            return 'wait-row';
                        default:
                            return '';
                    }
                }

            }
        }
    }
</script>
<style scoped>
    .el-table /deep/ .reject-row{
        background-color: #FEF0F0;
    }
    .el-table /deep/ .allow-row{
        background-color: #f0f9eb;
    }
    .el-table /deep/ .wait-row{
        background-color: #fff0de;
    }
</style>
<style scoped lang="scss">
    .table{
        width: 100%;
    }
    .table-expand {
        font-size: 0;
    }
    .table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .btn-group{
        button{
            padding: 0;
        }
    }
</style>
<style scoped>
    .no-scroll /deep/ .el-table__body-wrapper{
        height: 100% !important;
    }
</style>