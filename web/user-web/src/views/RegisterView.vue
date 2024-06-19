<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import type { UserRegister } from '@/model/user';
import { apiService } from '@/util/request';
const router = useRouter();

const userRegisterParams = ref<UserRegister>({
    id: '',
    password: '',
    realName: '',
    birthday: '',
    sex: 1,
    idCard: '',
    type: 1,
    code: 0,
});

const passwordRepeat = ref('');

const register = async () => {
    //console.log(JSON.stringify(userRegisterParams.value));
    if (!check()) return;
    apiService.post('/signup', userRegisterParams.value).then((res) => {
        if (res.isSuccess()) {
            regSuccess.value = true;
        } else {
            errorMsg.value = res.message;
        }
    }).catch((err) => {
        console.log(err);
        errorMsg.value = '网络错误: ' + err.response.data;
    })
}

const check = (): boolean => {
    if (passwordRepeat.value !== userRegisterParams.value.password) {
        return false;
    }
    if (!userRegisterParams.value.id || !userRegisterParams.value.password || !userRegisterParams.value.realName || !userRegisterParams.value.birthday || !userRegisterParams.value.idCard) {
        errorMsg.value = '请填写完整信息';
        return false;
    }
    if (!/^\d{11}$/.test(userRegisterParams.value.id)) {
        errorMsg.value = '手机号码格式错误';
        return false;
    }
    if (!/^\d{18}$/.test(userRegisterParams.value.idCard)) {
        errorMsg.value = '身份证号格式错误';
        return false;
    }
    // 密码要求6-32位，包含数字和字母
    if (!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$/.test(userRegisterParams.value.password)) {
        errorMsg.value = '密码要求6-32位，包含数字和字母';
        return false;
    }
    return true;
}

const errorMsg = ref('');

const regSuccess = ref(false);

</script>

<template>
    <!-- 总容器 -->
    <div class="wrapper" :hidden="regSuccess">
        <header>
            <i class="fa fa-angle-left" @click="() => { router.back() }"></i>
            <p>注册</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <h1>欢迎注册</h1>
        <table>
            <tr>
                <td>手机号码</td>
                <td><input placeholder="请输入手机号码" type="text" v-model="userRegisterParams.id" /></td>
            </tr>
            <tr>
                <td>验证码</td>
                <td><input placeholder="请输入验证码" type="text" v-model="userRegisterParams.code"/></td>
            </tr>
            <tr>
                <td>真实姓名</td>
                <td><input placeholder="真实姓名便于查看体检报告" type="text" v-model="userRegisterParams.realName" /></td>
            </tr>
            <tr>
                <td>生日</td>
                <td><input type="date" v-model="userRegisterParams.birthday" /></td>
            </tr>
            <tr>
                <td>性别</td>
                <td>
                    <input type="radio" value="1" v-model="userRegisterParams.sex" />男
                    <input type="radio" value="0" v-model="userRegisterParams.sex" />女
                </td>
            </tr>
            <tr>
                <td>身份证号</td>
                <td><input placeholder="请输入身份证号" type="text" v-model="userRegisterParams.idCard" /></td>
            </tr>
            <tr>
                <td>密码</td>
                <td><input placeholder="请输入密码" type="password" v-model="userRegisterParams.password" /></td>
            </tr>
            <tr>
                <td>确认密码</td>
                <td><input placeholder="请再次输入密码" type="password" v-model="passwordRepeat" /></td>
            </tr>
            <tr>
                <p v-if="passwordRepeat !== userRegisterParams.password" class="error-msg">两次密码不一致</p>
            </tr>
            <tr>
                <p v-if="errorMsg" class="error-msg">注册失败: {{ errorMsg }}</p>
            </tr>
        </table>
        <div class="btn" @click="register">完成</div>
    </div>
    <div class="wrapper" :hidden="!regSuccess">
        <div class="success">
            <i class="fa fa-check-circle"></i>
            <p>注册成功</p>
            <div class="btn" @click="() => { router.push('/login') }">去登录</div>
        </div>
    </div>
</template>
<style scoped>
@import '../assets/css/register.css';

.error-msg {
    color: red;
    font-size: 12px;
}
.success {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
}
</style>