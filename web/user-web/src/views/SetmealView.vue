<script setup lang="ts">
import { useUserStore } from '@/stores/userStore';
import { apiService } from '@/util/request';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import type {Package, CheckItem} from '@/model/package';
const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const currentSex = ref<number>(userStore.hasFamily ? userStore.family?.sex! : userStore.user?.sex!);
const hospitalName = ref<string>(userStore.hospital?.name!);

const setmealList = ref<Package[]>([]);

const getPackage = () => {
    if (userStore.isLogin && userStore.hasHospital)
    {
        apiService.get<Package[]>(`/package/list/${currentSex.value}`).then((res) => {
            if (res.isSuccess()) {
                setmealList.value = res.data;
            }
            else {
                console.log(res.message);
            }
        }).catch((err) => {
            console.log(err);
        });
    }
}

const open = ref<boolean[]>([])
const showDrawer = (idx: number) => {
    open.value[idx] = true;
};
const onClose = (idx: number) => {
    open.value[idx] = false;
};

function toNext(p: Package) {
    userStore.packageInfo = p;
    router.push('/selectdate');
}

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
                    <div class="item-left" @click="()=>toNext(item)">
                        <h3>{{ currentSex === 0 ? "女士" : "男士" }}套餐: ￥{{ item.price }}</h3>
                        <p>{{ item.name }}</p>
                    </div>
                    <div class="item-right" @click="() => showDrawer(idx)">
                        <p>详情</p>
                        <i class="fa fa-list"></i>
                    </div>
                </div>
                <a-drawer :title="item.name" placement="bottom" :closable="false" :open="open[idx]"
                    @close="() => onClose(idx)">
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