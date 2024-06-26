<script setup lang="ts">
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';

const router = useRouter();
const userStore = useUserStore();
const userInfo = ref(userStore.user);


</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.back()"></i>
            <p>年度总结报告</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <section>
            
        </section>
    </div>
</template>
<style scoped>
@import '../assets/css/personal.css';
</style>