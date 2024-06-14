<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();

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
                        <p>黄药师</p>
                        <p>账号: 156****4589</p>
                    </div>
                </div>
            </div>
            <ul>
                <li>
                    <div class="left">
                        <i class="fa fa-user-plus"></i>
                        <p>我的预约</p>
                    </div>
                    <div class="right" onclick="location.href='appointmentlist.html'">
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
                <li>
                    <div class="left">
                        <i class="fa fa-sign-out"></i>
                        <p>退出登录</p>
                    </div>
                    <div class="right" onclick="location.href='login.html'">
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