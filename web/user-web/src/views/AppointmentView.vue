<script setup lang="ts">
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
const router = useRouter();
const userStore = useUserStore();
</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.back()"></i>
            <p>用户体检预约</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <section>
            <img src="@/assets/img/yuyue.png" />
            <p>
                <!-- 用户预约 -->
                <img @click="() => { userStore.clearAppointment(); router.push('hospital') }"
                    src="@/assets/img/benrenyuyue.png" />
                <!-- 家属预约 -->
                <img @click="() => { userStore.clearAppointment(); router.push('familylist') }"
                    src="@/assets/img/jiashuyuyue.png" />
            </p>
        </section>
    </div>

</template>
<style scoped>
@import '../assets/css/appointment.css';
</style>