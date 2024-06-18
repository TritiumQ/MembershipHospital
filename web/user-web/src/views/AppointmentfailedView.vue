<script setup lang="ts">
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

const errorMsg = ref(route.query.errorMsg as string || '预约失败');
</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="()=>router.push('/')"></i>
            <p>预约失败</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <section>
            <div class="success">
                <div class="icon-box">
                    <div class="icon">
                        <i class="fa fa-close"></i>
                    </div>
                </div>
                <h1>恭喜预约失败！</h1>
                <p>{{ errorMsg }}</p>
            </div>
            <div class="order-btn" onclick="location.href='appointmentlist.html'">查看订单</div>
            <div class="continue" @click="() => router.push('/appointment')">返回</div>
            <!-- <div class="info">
                <p>您的信息已经发送至体检机构</p>
                <p>预约成功后会发送短信至您的手机</p>
                <p>客服电话<span>4008-XXX-XXX</span></p>
            </div> -->
        </section>
    </div>
</template>
<style scoped>
@import '../assets/css/appointmentsuccess.css';
</style>