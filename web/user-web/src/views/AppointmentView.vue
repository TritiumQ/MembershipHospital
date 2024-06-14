<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();
</script>
<template>

<!-- 总容器 -->
<div class="wrapper">
<header>
<i class="fa fa-angle-left" onclick="history.go(-1)"></i>
<p>用户体检预约</p>
<div></div>
</header>
<div class="top-ban"></div>
<section>
<img src="@/assets/img/yuyue.png"/>
<p>
<img onclick="location.href='hospital.html'" src="@/assets/img/benrenyuyue.png"/>
<img src="@/assets/img/jiashuyuyue.png"/>
</p>
</section>
<div class="bottom-ban"></div>
<footer>
<ul>
<li onclick="location.href='index.html'">
<i class="fa fa-home"></i>
<p>云医院</p>
</li>
<li>
<i class="fa fa-opencart"></i>
<p>商城</p>
</li>
<li>
<i class="fa fa-compass"></i>
<p>发现</p>
</li>
<li onclick="location.href='personal.html'">
<i class="fa fa-user"></i>
<p>我</p>
</li>
</ul>
</footer>
</div>

</template>
<style scoped>
@import '../assets/css/appointment.css';
</style>
        