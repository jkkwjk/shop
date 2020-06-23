<template>
    <div class="address">
        <el-card shadow="hover">
            <address-form :save-length="address.length" :remain-length="maxAddressNum-address.length"
                            @submit="addAddress"></address-form>
        </el-card>

        <el-card shadow="hover">
            <table-main :table-data="address" :column="tableResolve" customButtonClick custom-button-width="120"
                        customColumnMinWidth noScroll>
                <div slot="button" slot-scope="scope">
                    <el-button type="text" size="small" @click="handleSetDefault(scope.data.row)" :disabled="scope.data.row.defaultStatus">
                        {{scope.data.row.defaultStatus? "已为默认":"设为默认"}}
                    </el-button>
                    <el-button type="text" size="small" @click="handleDelete(scope.data.row)">删除</el-button>
                </div>
            </table-main>
        </el-card>
    </div>
</template>

<script>

    import AddressForm from "./AddressForm";
    import TableMain from "../../../components/TableMain";
    import {deepcopy, deepCopy} from "../../../util/MemoryUtil"
    export default {
        name: "Address",
        components: {TableMain, AddressForm},
        methods: {
            parseAllAddressNum(){
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
            },
            flush(){
                this.$http.get('/address').then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.address = data.data;
                        this.parseAllAddressNum();
                    }
                })
            },
            addAddress(form){
                let payload = deepCopy(form);
                payload.addressNum = form.addressNum.join('-');
                this.$http.post('/address',payload).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("添加成功");
                        this.flush();
                    }else {
                        this.$message.error(data.msg);
                    }
                })
            },
            handleSetDefault({id}){
                this.$http.put('/address',{
                    id: id,
                    status: true
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("修改成功");
                        this.flush();
                    }else {
                        this.$message.error(data.msg);
                    }
                })
            },
            handleDelete({id}){
                this.$http.delete('/address',{
                    params:{ id: id }
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("删除成功");
                        this.flush();
                    }else {
                        this.$message.error(data.msg);
                    }
                })
            }
        },
        created(){
            this.flush();
        },
        data(){
            return {
                maxAddressNum: '20',
                tableResolve: [
                    {title: '收货人',prop: 'name'}, {title: '所在地区',prop: 'addressNum',columnWidth:'150px'},{title: '详细地址',prop: 'addressDetail',columnWidth: '200px'},
                    {title: '邮编',prop: 'postcode',columnWidth:'70px'}, {title: '电话',prop: 'telephone'}
                ],
                address: []
            }
        }
    }
</script>

<style>
</style>