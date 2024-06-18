<script setup lang="ts">

</script>
<template>

    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" onclick="history.go(-1)"></i>
            <p>我的预约</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <ul>
            <li>
                <div class="left">
                    <p>2022-03-22</p>
                    <p>普通男士客户-基础套餐</p>
                </div>
                <div class="right" onclick="location.href='appointmentcancel.html'">
                    取消预约
                </div>
            </li>
            <li>
                <div class="left">
                    <p>2021-11-22</p>
                    <p>普通男士客户-脑血管系统</p>
                </div>
            </li>
        </ul>
        <div class="bottom-ban"></div>
    </div>

</template>
<style scoped>
@import '../assets/css/appointmentlist.css';
</style>