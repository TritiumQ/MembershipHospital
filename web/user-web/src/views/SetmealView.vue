<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import { apiService } from '@/util/request';
const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const userSex = ref<number>(userStore.user?.sex!);
const hospitalId = ref<number>(Number.parseInt(route.query.id as string));
const hospitalName = ref<string>(route.query.name as string);

const setmealList = ref<any[]>([]);

const getPackage = () => {
    apiService.get<any[]>(`/package/list/${userSex.value}`).then((res) => {
        if (res.isSuccess())
        {
            setmealList.value = res.data;
        }
        else
        {
            console.log(res.message);
        }
    }).catch((err) => {
        console.log(err);
    });
}

const open = ref<boolean[]>([])
const showDrawer = (idx: number) => {
    open.value[idx] = true;
};
const onClose = (idx: number) => {
    open.value[idx] = false;
};


onMounted(() => {
    getPackage();
});
</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.back()"></i>
            <p>选择体检套餐: {{ hospitalName }}</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <p class="error" v-if="setmealList.length == 0">暂无套餐信息</p>
        <ul class="setmeal" v-for="item, idx in setmealList">
            <li>
                <div class="item">
                    <div class="item-left" @click="() => router.push('/selectdate')">
                        <h3>{{ userSex == 0 ? "女士" : "男士" }}套餐: ￥{{ item.price }}</h3>
                        <p>{{ item.name }}</p>
                    </div>
                    <div class="item-right" @click="() => showDrawer(idx)">
                        <p>详情</p>
                        <i class="fa fa-list"></i>
                    </div>
                </div>
                <a-drawer :title="item.name" placement="bottom" :closable="false" :open="open[idx]"
                    @close="()=>onClose(idx)">
                    <div class="item-content">
                        <p class="error" v-if="!item.checkItems || item.checkItems.length == 0">
                            没有检查项目
                        </p>
                        <table>
                            <tr>
                                <th>检查项目</th>
                                <th>检查内容</th>
                                <th>检查意义</th>
                            </tr>
                            <tr v-for="checkitem, cidx in item.checkItems">
                                <td>{{ checkitem.name }}</td>
                                <td>{{ checkitem.content }}</td>
                                <td>{{ checkitem.meaning }}</td>
                            </tr>
                        </table>
                    </div>
                </a-drawer>

            </li>
        </ul>

        <!-- <ul class="setmeal">
            <li>
                <div class="item">
                    <div class="item-left" onclick="location.href='selectdate.html'">
                        <h3>男士套餐</h3>
                        <p>普通男士客户-基础套餐</p>
                    </div>
                    <div class="item-right">
                        <p>详情</p>
                        <i class="fa fa-angle-down"></i>
                    </div>
                </div>
                <div class="item-content">
                    <table>
                        <tr>
                            <th>检查项目</th>
                            <th>检查内容</th>
                            <th>检查意义</th>
                        </tr>
                        <tr>
                            <td>一般检查</td>
                            <td>血压、身高、体重、腰围、臀围。</td>
                            <td>通过仪器测定人体基本健康指标。</td>
                        </tr>
                        <tr>
                            <td>血常规</td>
                            <td>
                                血常规24项: 中性粒细胞计数绝对值、红细胞压值、血小板比容、单核细胞计数百分比、平均血小板体积、
                                大血小板比例、嗜碱性粒细胞计数百分比、平均血红蛋白含量等。
                            </td>
                            <td>临床三大常规检测之一，是最基本的血液检验。通过观察血细胞的数量变化及形态分布，从而判断血液状况及相关疾病。</td>
                        </tr>
                        <tr>
                            <td>尿常规</td>
                            <td>
                                尿维生素C测定、尿比重测定、尿液酮体测定、尿胆红素测定、尿亚硝酸盐测定、尿液颜色、尿白细胞计数、尿蛋白定性、
                                尿液镜检、尿葡萄糖测定、尿液隐血、尿液酸碱度、尿液清晰度、尿胆原。
                            </td>
                            <td>临床三大常规检测之一，可反映机体的代谢状况，是很多疾病诊断的重要指标。</td>
                        </tr>
                    </table>
                </div>
            </li>
            <li>
                <div class="item">
                    <div class="item-left" onclick="location.href='selectdate.html'">
                        <h3>男士套餐</h3>
                        <p>普通男士客户-脑血管系统</p>
                    </div>
                    <div class="item-right">
                        <p>详情</p>
                        <i class="fa fa-angle-down"></i>
                    </div>
                </div>
                <div class="item-content">
                    <table>
                        <tr>
                            <th>检查项目</th>
                            <th>检查内容</th>
                            <th>检查意义</th>
                        </tr>
                        <tr>
                            <td>一般检查</td>
                            <td>血压、身高、体重、腰围、臀围。</td>
                            <td>通过仪器测定人体基本健康指标。</td>
                        </tr>
                        <tr>
                            <td>血常规</td>
                            <td>
                                血常规24项: 中性粒细胞计数绝对值、红细胞压值、血小板比容、单核细胞计数百分比、平均血小板体积、
                                大血小板比例、嗜碱性粒细胞计数百分比、平均血红蛋白含量等。
                            </td>
                            <td>临床三大常规检测之一，是最基本的血液检验。通过观察血细胞的数量变化及形态分布，从而判断血液状况及相关疾病。</td>
                        </tr>
                        <tr>
                            <td>尿常规</td>
                            <td>
                                尿维生素C测定、尿比重测定、尿液酮体测定、尿胆红素测定、尿亚硝酸盐测定、尿液颜色、尿白细胞计数、尿蛋白定性、
                                尿液镜检、尿葡萄糖测定、尿液隐血、尿液酸碱度、尿液清晰度、尿胆原。
                            </td>
                            <td>临床三大常规检测之一，可反映机体的代谢状况，是很多疾病诊断的重要指标。</td>
                        </tr>
                    </table>
                </div>
            </li>
            <li>
                <div class="item">
                    <div class="item-left" onclick="location.href='selectdate.html'">
                        <h3>男士套餐</h3>
                        <p>普通男士客户-肝病检查</p>
                    </div>
                    <div class="item-right">
                        <p>详情</p>
                        <i class="fa fa-angle-down"></i>
                    </div>
                </div>
                <div class="item-content">
                    <table>
                        <tr>
                            <th>检查项目</th>
                            <th>检查内容</th>
                            <th>检查意义</th>
                        </tr>
                        <tr>
                            <td>一般检查</td>
                            <td>血压、身高、体重、腰围、臀围。</td>
                            <td>通过仪器测定人体基本健康指标。</td>
                        </tr>
                        <tr>
                            <td>血常规</td>
                            <td>
                                血常规24项: 中性粒细胞计数绝对值、红细胞压值、血小板比容、单核细胞计数百分比、平均血小板体积、
                                大血小板比例、嗜碱性粒细胞计数百分比、平均血红蛋白含量等。
                            </td>
                            <td>临床三大常规检测之一，是最基本的血液检验。通过观察血细胞的数量变化及形态分布，从而判断血液状况及相关疾病。</td>
                        </tr>
                        <tr>
                            <td>尿常规</td>
                            <td>
                                尿维生素C测定、尿比重测定、尿液酮体测定、尿胆红素测定、尿亚硝酸盐测定、尿液颜色、尿白细胞计数、尿蛋白定性、
                                尿液镜检、尿葡萄糖测定、尿液隐血、尿液酸碱度、尿液清晰度、尿胆原。
                            </td>
                            <td>临床三大常规检测之一，可反映机体的代谢状况，是很多疾病诊断的重要指标。</td>
                        </tr>
                    </table>
                </div>
            </li>
        </ul> -->
    </div>
</template>
<style scoped>
@import '../assets/css/setmeal.css';

/* 设置边框 */
table {
    border-right: 1px solid #000000;
    border-bottom: 1px solid #000000;
    text-align: center;
}

table th {
    border-left: 1px solid #000000;
    border-top: 1px solid #000000;
}

table td {
    border-left: 1px solid #000000;
    border-top: 1px solid #000000;
}

.error {
    text-align: center;
    color: #ff0000;
}
</style>