<script setup lang="ts">
import type { CheckReport } from '@/model/check';
import { useUserStore } from '@/stores/userStore';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();
const userStore = useUserStore();

const reports = ref<CheckReport[]>([]);

const toDetailView = (report: CheckReport) => {
    userStore.checkReport = report;
    router.push('/report');
}

</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.back()"></i>
            <p>健康档案</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <section>
            <img src="@/assets/img/report.png" />
            <p v-if="reports.length === 0" class="error" @click="()=>router.push('/appointment')"> 暂无报告, 点击前往预约 </p>
            <ul v-for="item, idx in reports">
                <li>
                    <div class="left">
                        <i class="fa fa-file-text-o"></i>
                        <div>
                            <p>{{ item.date }} 体检报告</p>
                            <p>{{ item.hospital.name }}</p>
                        </div>
                    </div>
                    <div class="right" @click="()=>toDetailView(item)">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
            </ul>
        </section>
        <div class="bottom-ban"></div>
    </div>
</template>
<style scoped>
@import '../assets/css/reportlist.css';
.error {
    text-align: center;
    margin-top: 20px;
    color: blue;
}
</style>