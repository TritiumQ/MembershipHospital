<script setup lang="ts">
import { useUserStore } from '@/stores/userStore';
import { apiService } from '@/util/request';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import type { UserInfoWithToken, UserLogin } from '@/model/user';

const userStore = useUserStore();

const userLoginParams = ref<UserLogin>({
    id: '',
    password: ''
});
const router = useRouter();

const errorMsg = ref('');

function login()
{
    console.log(JSON.stringify(userLoginParams.value));
    if(userLoginParams.value.id === '' || userLoginParams.value.password === '') {
        return;
    }
    apiService.post<UserInfoWithToken>('/signin', userLoginParams.value)
        .then(res => {
        if(res.isSuccess()) {
            userStore.login(res.data);
            router.push('/');
        }
        else {
            errorMsg.value = res.message;
        }
    }).catch(err => {
        console.log(err);
        errorMsg.value = '网络错误: ' + err.response.data;
    });
};

const toRegister = () => {
    router.push('/register');
};

</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <h1>体检预约-登录</h1>
        <section>
            <div class="input-box">
                <i class="fa fa-user-o"></i>
                <input placeholder="输入手机号" type="text" v-model="userLoginParams.id" />
            </div>
            <div class="input-box">
                <i class="fa fa-lock"></i>
                <input placeholder="输入登录密码" type="password" v-model="userLoginParams.password" />
            </div>
            <p class="error-msg" v-if="errorMsg">{{errorMsg}}</p>
            <div class="reg-box">
                <p @click="toRegister">注册</p>
                <p>忘记密码？</p>
            </div>
            <div class="button-box" @click=login>登录</div>
        </section>
        <footer>
            <div>
                <div class="line"></div>
                <p>池沼联系方式（确信）</p>
                <div class="line"></div>
            </div>
            <p>114-514-1919-810</p>
        </footer>
    </div>
</template>
<style scoped>
@import '../assets/css/login.css';
.error-msg {
    color: red;
    font-size: 12px;
}
</style>