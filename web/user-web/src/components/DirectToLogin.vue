<script setup  lang="ts">
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue';
const router = useRouter();
onMounted(() => {
    message.info('尚未登录，即将跳转到登录页面');
    setTimeout(() => {
        router.push('login');
    }, 3000);
});
</script>
<template>
    <div class="wrapper">
        <div class="button-box">尚未登录，即将跳转到登录页面</div>
    </div>
</template>
<style scoped>
/*********************** 总容器 ***********************/
.wrapper {
    width: 100%;
    background-color: #F9F9F9;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    
}
.button-box {
    width: 100%;
    height: 13vw;
    border-radius: 2vw;
    background-color: #70B0BC;

    text-align: center;
    line-height: 13vw;
    font-size: 4.6vw;
    color: #FFF;
    letter-spacing: 1vw;

    user-select: none;
    cursor: pointer;
}

</style>