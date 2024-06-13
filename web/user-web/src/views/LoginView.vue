<script setup lang="ts">
</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <h1>体检预约-登录</h1>
        <section>
            <div class="input-box">
                <i class="fa fa-user-o"></i>
                <input placeholder="输入手机号" type="text" />
            </div>
            <div class="input-box">
                <i class="fa fa-lock"></i>
                <input placeholder="输入登录密码" type="password" />
            </div>
            <div class="reg-box">
                <p onclick="location.href='register.html'">注册</p>
                <p>忘记密码？</p>
            </div>
            <div class="button-box" onclick="location.href='index.html'">登录</div>
        </section>
        <footer>
            <div>
                <div class="line"></div>
                <p>有疑问请联系客服</p>
                <div class="line"></div>
            </div>
            <p>4008-XXX-XXX</p>
        </footer>
    </div>
</template>
<style scoped>
@import '../assets/css/login.css';
</style>