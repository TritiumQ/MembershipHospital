
<script setup lang="ts">
</script>
<template>

<!-- 总容器 -->
<div class="wrapper">
<header>
<i class="fa fa-angle-left" onclick="history.go(-1)"></i>
<p>确认您的订单</p>
<div></div>
</header>
<div class="top-ban"></div>
<section>
<div class="title">
<p>体检人信息</p>
</div>
<table>
<tr>
<td>姓名:</td>
<td>黄药师</td>
</tr>
<tr>
<td>证件号码:</td>
<td>210106587452156897</td>
</tr>
<tr>
<td>出生日期:</td>
<td>1999-10-23</td>
</tr>
<tr>
<td>手机号码:</td>
<td>15698745214</td>
</tr>
</table>
<div class="title">
<p>体检日期</p>
</div>
<table>
<tr>
<td>2021年11月28日</td>
</tr>
</table>
<div class="title">
<p>体检机构</p>
</div>
<table>
<tr>
<td colspan="2">沈阳熙康云医院-和平院区</td>
</tr>
<tr>
<td>营业时间:</td>
<td>周一至周五 7:30-15:30 （周六截止12:00）</td>
</tr>
<tr>
<td>采血截止:</td>
<td>采血截止时间10:30</td>
</tr>
<tr>
<td>机构电话:</td>
<td>98745214</td>
</tr>
<tr>
<td>机构地址:</td>
<td>文体路7号世贸商都（五里河茶城）四楼西区</td>
</tr>
</table>
<div class="title">
<p>套餐类型</p>
</div>
<table>
<tr>
<td>普通男士客户-基础套餐</td>
</tr>
</table>
</section>
<div class="bottom-btn">
<div class="first">实付款: <span>￥350</span></div>
<div class="last" onclick="location.href='appointmentsuccess.html'">确认支付</div>
</div>
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
@import '../assets/css/confirmorder.css';
</style>
        