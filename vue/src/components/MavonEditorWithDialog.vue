<template>
    <el-dialog :visible.sync="visible" :before-close="onCloseDialog">
        <mavon-editor :ishljs="false" :externalLink="false" ref="md" @imgAdd="onImgAdd" v-model="value" @save="(p1,p2)=>$emit('save',p1,p2)"></mavon-editor>
    </el-dialog>
</template>

<script>
    export default {
        name: "MavonEditorWithDialog",
        props: {
            originValue: {type: String, default: ''},
            autoVisible: {type: Boolean}
        },
        methods: {
            onCloseDialog(done){
                this.$confirm('确认关闭？').then(_ => {
                    done();
                }).catch(_ => {});
            },
            onImgAdd(pos, file){
                let formData = new FormData();
                formData.append('file', file);
                this.$http.post('http://localhost:9091/upload',formData, {
                    headers: { 'Content-Type': 'multipart/form-data' },
                }).then(res => {
                    const data = res.data;
                    if (data.code === 200){
                        this.$refs.md.$img2Url(pos, data.data);
                    }else {
                        this.$message.error(data.msg);
                    }

                })
            }
        },
        data(){
            return{
                visible: true,
                value: this.originValue
            }
        },
        watch: {
            visible(){
                this.$emit('update:autoVisible', this.visible);
            }
        }
    }
</script>

<style scoped>

</style>