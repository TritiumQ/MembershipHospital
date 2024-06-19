<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router';
const router = useRouter();
const route = useRoute();
</script>
<template>

    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.push('/')"></i>
            <p>取消预约</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <section>
            <div class="success">
                <div class="icon-box">
                    <div class="icon">
                        <i class="fa fa-check"></i>
                    </div>
                </div>
                <h1>取消预约成功！</h1>
                <p>退款将在24小时之内到账</p>
            </div>
            <div class="order-btn" @click="() => router.push('/appointmentlist')">查看订单</div>
            <div class="continue" @click="() => router.push('/appointment')">继续为家人预约</div>
            <div class="info">
                <p>您的信息已经发送至体检机构</p>
                <p>取消预约后会发送短信至您的手机</p>
                <p>客服电话<span>114-514-1919-810</span></p>
            </div>
        </section>
        <div class="bottom-ban"></div>
    </div>
</template>
<style scoped>
@import '../assets/css/appointmentsuccess.css';
</style>