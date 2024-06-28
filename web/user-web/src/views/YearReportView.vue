<script setup lang="ts">
import { useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { apiService } from '@/util/request';

const router = useRouter();
const userStore = useUserStore();
const yearReportList = ref<{year: number, outlay: number}[]>([]);

const spinning = ref(false);

const getYearReportList =() => {
    spinning.value = true;
    apiService.get<any[]>(`/year-report/list/${userStore.user?.id || 0}`).then((res) => {
        if (res.isSuccess()) {
            yearReportList.value = res.data;
        }
        else {
            console.error(res.message);
        }
    }).catch((err) => {
        console.error(err);
    }).finally(() => {
        spinning.value = false;
    });
};

onMounted(() => {
    getYearReportList();
});
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
        <a-spin :spinning="spinning">
            <section>
                <ul class="hospital" v-for="item, idx in yearReportList">
                    <li>
                        <div class="hospita-info">
                            <table>
                                <tr>
                                    <td>年份</td>
                                    <td>{{ item.year }}</td>
                                </tr>
                                <tr>
                                    <td>总支出</td>
                                    <td><i class="fa fa-cny"></i>{{ item.outlay }}</td>
                                </tr>
                            </table>
                        </div>
                    </li>
                </ul>
            </section>
        </a-spin>
    </div>
</template>
<style scoped>
@import '../assets/css/hospital.css';
</style>