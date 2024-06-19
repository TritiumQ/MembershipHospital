<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router';
import { computed, onMounted, ref } from 'vue';
import type { DetailedReport, CheckReport } from '../model/check';
import { useUserStore } from '@/stores/userStore';

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const report = ref<CheckReport | null>(null);
const errorItems = computed(() => {
    let items: DetailedReport[] = [];
    report.value?.checkItemReports.forEach((item) => {
        item.detailedReports.forEach((detailed) => {
            if (detailed.error === 1) {
                items.push(detailed);
            }
        });
    });
    return items;
});

onMounted(() => {
    report.value = userStore.checkReport;
});

</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.back()"></i>
            <p>体检报告</p>
            <div></div>
        </header>
        <nav>
            <div class="active">总检结论</div>
            <div>报告详情</div>
        </nav>
        <div class="top-ban"></div>
        <p v-if="!report">
        <div class="error" @click="() => router.push('/appointment')">暂无报告, 点击前往预约</div>
        </p>
        <div v-else>
            <div class="nav-content-item">
                <div class="item">
                    <div class="title">异常项</div>
                    <ul v-for="i, idx in errorItems">
                        <li>
                            <div class="indications">
                                <div class="left">
                                    <div>异</div>
                                    <p>{{ i.checkItemDetailed.name }}</p>
                                </div>
                                <div class="right">
                                    <p>{{ i.value }} {{ i.checkItemDetailed.unit ?? '' }}</p>
                                    <p>正常值范围：{{ i.checkItemDetailed.normalValueDescription ?? '' }}</p>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="item">
                    <div class="title">一、尊敬的顾客，您本次体检结论如下：</div>
                    <ul v-for="c, idx in report?.overallResults">
                        <li class="conclusion-title">{{ idx + 1 }}、{{ c.title }}</li>
                    </ul>
                </div>
                <div class="item">
                    <div class="title">二、尊敬的顾客，您本次体检建议信息如下：</div>
                    <ul v-for="c, idx in report?.overallResults">
                        <li class="conclusion-content">
                            <h3>{{ idx + 1 }}、{{ c.title }}</h3>
                            <p>{{ c.content }}</p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="nav-content-item">
                <div v-for="item, idx in report?.checkItemReports">
                    <div class="item">
                        <div class="title">{{ item.checkItem.name }}</div>
                        <ul v-for="d, idx in item.detailedReports">
                            <li>
                                <div class="indications">
                                    <div class="left">
                                        <div v-if="d.error === 1">异</div>
                                        <p>{{ d.checkItemDetailed.name }}</p>
                                    </div>
                                    <div class="right">
                                        <p>{{ d.value }} {{ d.checkItemDetailed.unit ?? '' }}</p>
                                        <p v-if="d.checkItemDetailed.type !== 3 && d.checkItemDetailed.type !== 4">
                                            正常值范围：{{
                                                d.checkItemDetailed.normalValueDescription ?? '' }}</p>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="bottom-ban"></div>
    </div>

</template>
<style scoped>
@import '../assets/css/report.css';
.error {
    text-align: center;
    margin-top: 20px;
    color: blue;
}
</style>