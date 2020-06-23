<template>
    <div class="editable-span">
        <span @click="edit = !edit">{{ text===''? '无':text }}</span>
        <el-input v-if="edit" v-model="input_text" :type="type" v-focus="true" @blur="edit = false">
            <i class="el-icon-check el-input__icon" slot="suffix" @mousedown.prevent="handleClick"> </i>
        </el-input>
    </div>
</template>

<script>
    export default {
        name: "EditableSpan",
        props: {
            text: {required: true},
            type: {required: false, type: String, default: 'text'}
        },
        data(){
            return {
                edit: false,
                input_text: '',
            }

        },
        methods: {
            handleClick(){
                this.edit = false;
                this.$emit("edit", this.input_text, (text)=>this.input_text=text);
            }
        },
        directives: {
            focus: {
                inserted: function(el, { value }) {
                    if (value) {
                        el.children[0].focus();
                    }
                }
            }
        },
        created() {
            this.input_text = this.text;
        }
    }
</script>

<style scoped>
    span{
        height: 40px;
        line-height: 40px;
        padding: 0 15px;
    }

    .editable-span{
        position: relative;
    }

    .el-input{
        position: absolute;
        top: 0;
        left: 0;
    }
</style>