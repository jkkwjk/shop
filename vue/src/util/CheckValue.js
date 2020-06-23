export default {
    checkEmail(value){
        if(value === '')
            return true;
        return /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/.test(value);
    },

    checkPwd(value) {
        return /^(?![^a-zA-Z]+$)(?!\D+$)/.test(value);
    },

    checkPhone(value) {
        return /^1[3456789]\d{9}$/.test(value);
    },

    checkNickName(value) {
        return 4 <= value.length && value.length <= 13;
    }

}