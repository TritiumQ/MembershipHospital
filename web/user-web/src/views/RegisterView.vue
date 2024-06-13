
<script setup lang="ts">
</script>
<template>

<!-- 总容器 -->
<div class="wrapper">
<header>
<i class="fa fa-angle-left" onclick="history.go(-1)"></i>
<p>注册</p>
<div></div>
</header>
<div class="top-ban"></div>
<h1>欢迎注册</h1>
<table>
<tr>
<td>手机号码</td>
<td><input placeholder="请输入手机号码" type="text"/></td>
</tr>
<tr>
<td>真实姓名</td>
<td><input placeholder="真实姓名便于查看体检报告" type="text"/></td>
</tr>
<tr>
<td>生日</td>
<td><input type="date"/></td>
</tr>
<tr>
<td>性别</td>
<td>
<input checked="" type="radio"/>男
                    <input type="radio"/>女
                </td>
</tr>
<tr>
<td>身份证号</td>
<td><input placeholder="请输入身份证号" type="text"/></td>
</tr>
<tr>
<td>密码</td>
<td><input placeholder="请输入密码" type="password"/></td>
</tr>
<tr>
<td>确认密码</td>
<td><input placeholder="请再次输入密码" type="password"/></td>
</tr>
</table>
<div class="btn" onclick="location.href='login.html'">完成</div>
</div>

</template>
<style scoped>
@import '../assets/css/register.css';
</style>
        