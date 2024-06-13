
<script setup lang="ts">
</script>
<template>

<!-- 总容器 -->
<div class="wrapper">
<header>
<i class="fa fa-angle-left" onclick="history.go(-1)"></i>
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
<div class="order-btn" onclick="location.href='appointmentlist.html'">查看订单</div>
<div class="continue" onclick="location.href='appointment.html'">继续为家人预约</div>
<div class="info">
<p>您的信息已经发送至体检机构</p>
<p>取消预约后会发送短信至您的手机</p>
<p>客服电话<span>4008-XXX-XXX</span></p>
</div>
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
@import '../assets/css/appointmentcancel.css';
</style>
        