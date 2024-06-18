<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import type { Calender } from '@/model/calender';
import { apiService } from '@/util/request';
import { useUserStore } from '@/stores/userStore';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const hospitalId = ref<number>(userStore.hospital?.id!);

const calender = ref<Calender | null>(null);
// tomorrow
const today = new Date();
const selectDate = ref<Date>(new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1));

const errorMsg = ref<string>('');
const toNext = (date: string) => {
    if((new Date(date) <= today))
    {
        errorMsg.value = '请选择正确的日期';
        return;
    }
    userStore.appointmentDate = date;
    router.push('/confirmorder');
}

const getCount = (date: Date) => {
    return computed(() => {
        return calender.value?.appointmentCounts?.find((item) => compareDate(date, new Date(item.date)))?.count ?? 0;
    })
}

const compareDate = (date1: Date, date2: Date) => {
    return (date1.getFullYear() === date2.getFullYear())
        && (date1.getMonth() === date2.getMonth())
        && (date1.getDate() === date2.getDate());
}

const allowedDates = computed(() => {
    return calender.value?.appointmentCounts?.map((item) => new Date(item.date));
});

const formatDate = (date: Date) => {
    // 月份设置为两位数
    // const year = date.getFullYear();
    // const month = (date.getMonth() + 1).toString().padStart(2, '0');
    // const day = date.getDate().toString().padStart(2, '0');
    // return `${year}-${month}-${day}`;
    return date.toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' }).replace(/\//g, '-');
}

const getCalender = async () => {
    if (userStore.hasHospital)
    {
        await apiService.get<Calender>(`/calender/get/${hospitalId.value}`).then((res) => {
            if (res.isSuccess()) {
                calender.value = res.data;
            }
            else {
                console.log(res.message);
            }
        }).catch((err) => {
            console.log(err);
        });
    }
}

onMounted(async () => {
    await getCalender();
});

</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.back()"></i>
            <p>选择体检日期</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <p class="error" v-if="calender == null">暂无日期信息</p>
        <VueDatePicker v-else v-model="selectDate" inline auto-apply class="custom-date-picker"
            locale="zh-CN" :enable-time-picker="false" :allowed-dates="allowedDates">
            <template #day="{ date, day }">
                <div>
                    {{ day }}
                    <br />
                    <p class="count-text">余{{getCount(date)}}</p>
                </div>
            </template>
        </VueDatePicker>
        <p class="message">当前选择: {{ formatDate(selectDate) }}</p>
        <p class="error">{{ errorMsg }}</p>
        <div class="bottom-btn">
            <div></div>
            <div @click="() => toNext(formatDate(selectDate))">下一步</div>
        </div>
    </div>

</template>
<style scoped>
@import '../assets/css/selectdate.css';

.error {
    text-align: center;
    margin-top: 20px;
    color: red
}

.count-text {
    text-align: center;
    font-size: 8px;
    color: red;
}

.slot-icon {
    height: 20px;
    width: auto;
}

.custom-date-picker {
    width: 100%;
    height: 100%;
}
</style>