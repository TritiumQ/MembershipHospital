<script setup lang="ts">
import type { Hospital } from '@/model/hospital';
import { apiService } from '@/util/request';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
const router = useRouter();
const userStore = useUserStore();

const hospitalList = ref<Hospital[]>([]);

const getHospitalList = async () => {
    await apiService.get<Hospital[]>('/hospital/list/1').then((res) => {
        if (res.isSuccess()) {
            hospitalList.value = res.data;
        }
        else {
            console.error(res.message);
        }
    }).catch((err) => {
        console.error(err);
    });
};

const toNext = (h: Hospital) => {
    userStore.hospital = h;
    router.push('/setmeal');
}

onMounted(() => {
    getHospitalList();
});
</script>
<template>

    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="()=>router.back()"></i>
            <p>请您选择体检机构</p>
            <div></div>
            <!-- <i class="fa fa-repeat" @click="getHospitalList"></i> -->

        </header>
        <div class="top-ban"></div>
        <ul class="hospital" v-for="item, idx in hospitalList">
            <li>
                <h3 @click="()=>toNext(item)">
                    {{ item.name }}
                    <i class="fa fa-angle-right"></i>
                </h3>
                <div class="hospita-info">
                    <img :src="item.picture" />
                    <table>
                        <tr>
                            <td>营业时间</td>
                            <td>{{ item.businessHours }}</td>
                        </tr>
                        <tr>
                            <td>采血截止</td>
                            <td>{{ item.deadline }}</td>
                        </tr>
                        <tr>
                            <td>电话</td>
                            <td>{{ item.telephone }}</td>
                        </tr>
                        <tr>
                            <td>地址</td>
                            <td>{{ item.address }}</td>
                        </tr>
                    </table>
                </div>
                <div class="about">
                    <p>
                        <i class="fa fa-phone"></i>
                        联系我们
                    </p>
                    <p>
                        <i class="fa fa-map-marker"></i>
                        查找位置
                    </p>
                </div>
            </li>
        </ul>
    </div>

</template>
<style scoped>
@import '../assets/css/hospital.css';
</style>