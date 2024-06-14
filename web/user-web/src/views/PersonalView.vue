<script setup lang="ts">
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';

const router = useRouter();
const userStore = useUserStore();

const userInfo = ref(userStore.user);

const logout = () => {
    userStore.logout();
    router.push('/login');
};

</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="()=>router.back()"></i>
            <p>个人中心</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <section>
            <div class="info">
                <div class="content">
                    <img src="@/assets/img/user.png" />
                    <div>
                        <p>{{ userInfo?.realName }}</p>
                        <p>账号: {{ userInfo?.id }}</p>
                    </div>
                </div>
            </div>
            <ul>
                <li @click="() => router.push('appointmentlist') ">
                    <div class="left">
                        <i class="fa fa-user-plus"></i>
                        <p>我的预约</p>
                    </div>
                    <div class="right">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
                <li>
                    <div class="left">
                        <i class="fa fa-volume-control-phone"></i>
                        <p>我的服务</p>
                    </div>
                    <div class="right">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
                <li>
                    <div class="left">
                        <i class="fa fa-bed"></i>
                        <p>我的医生</p>
                    </div>
                    <div class="right">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
                <li>
                    <div class="left">
                        <i class="fa fa-sticky-note"></i>
                        <p>问诊订单</p>
                    </div>
                    <div class="right">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
                <li>
                    <div class="left">
                        <i class="fa fa-cart-plus"></i>
                        <p>商城订单</p>
                    </div>
                    <div class="right">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
                <li @click="logout">
                    <div class="left">
                        <i class="fa fa-sign-out"></i>
                        <p>退出登录</p>
                    </div>
                    <div class="right">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
            </ul>
        </section>
    </div>

</template>
<style scoped>
@import '../assets/css/personal.css';
</style>