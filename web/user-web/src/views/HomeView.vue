<script setup lang="ts">

import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
const router = useRouter();
const userStore = useUserStore();
const logout = () => {
    userStore.logout();
    router.push('/login');
};
</script>

<template>
  <h1>测试主页面</h1>
    <p>欢迎来到会员制医院，本医院有丰富的体检套餐可供选择</p>
    <button @click="logout">Logout</button>

</template>
