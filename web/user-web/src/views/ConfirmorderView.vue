<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import { ref } from 'vue';
import type { Order, OrderCreate, OrderInfo } from '@/model/order';
import { apiService } from '@/util/request';
const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const user = ref(userStore.user!);
const hospital = ref(userStore.hospital!);
const packageInfo = ref(userStore.packageInfo!);
const appointmentDate = ref(userStore.appointmentDate!);

const open = ref<boolean>(false);
const showDrawer = () => {
    open.value = true;
};
const onClose = () => {
    open.value = false;
};

const confirm = () => {
    if (userStore.appointmentCompleted)
    {
        const orderCreate: OrderCreate = {
            date: appointmentDate.value,
            hospitalId: hospital.value.id,
            packageId: packageInfo.value.id,
            userId: user.value.id
        };
        apiService.post<OrderInfo>('order/create', orderCreate).then((res) => {
            if (res.isSuccess())
            {
                router.push('/appointmentsuccess')
            }
            else
            {
                router.push({ path: '/appointmentfailed', query: { errorMsg: res.message } })
            }
        }).catch((err) => {
            console.error(err);
            router.push({ path: '/appointmentfailed', query: { errorMsg: err?.response?.data } })
        });
    }
};

</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.back()"></i>
            <p>确认您的订单</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <section>
            <div class="title">
                <p>体检人信息</p>
            </div>
            <table>
                <tr>
                    <td>姓名:</td>
                    <td>{{ user?.realName }}</td>
                </tr>
                <tr>
                    <td>证件号码:</td>
                    <td>{{ user?.idCard }}</td>
                </tr>
                <tr>
                    <td>出生日期:</td>
                    <td>{{ user?.birthday}}</td>
                </tr>
                <tr>
                    <td>手机号码:</td>
                    <td>{{ user?.id }}</td>
                </tr>
            </table>
            <div class="title">
                <p>体检日期</p>
            </div>
            <table>
                <tr>
                    <td>{{ appointmentDate }}</td>
                </tr>
            </table>
            <div class="title">
                <p>体检机构</p>
            </div>
            <table>
                <tr>
                    <td colspan="2">{{ hospital?.name }}</td>
                </tr>
                <tr>
                    <td>营业时间:</td>
                    <td>{{ hospital?.businessHours }}</td>
                </tr>
                <tr>
                    <td>采血截止:</td>
                    <td>{{ hospital?.deadline }}</td>
                </tr>
                <tr>
                    <td>机构电话:</td>
                    <td>{{ hospital?.telephone }}</td>
                </tr>
                <tr>
                    <td>机构地址:</td>
                    <td>{{ hospital?.address }}</td>
                </tr>
            </table>
            <div class="title">
                <p>套餐类型</p>
            </div>
            <div class="item">
                <div class="item-left">
                    <h3>{{ user?.sex == 0 ? "女士" : "男士" }}套餐: ￥{{ packageInfo?.price }}</h3>
                    <p>{{ packageInfo?.name }}</p>
                </div>
                <div class="item-right" @click="showDrawer">
                    <p>详情</p>
                    <i class="fa fa-list"></i>
                </div>
            </div>

            <a-drawer :title="packageInfo?.name" placement="bottom" :closable="false" :open="open" @close="onClose">
                <div class="item-content">
                    <p class="error" v-if="!packageInfo?.checkItems || packageInfo?.checkItems.length == 0">
                        没有检查项目
                    </p>
                    <table>
                        <tr v-for="checkitem, cidx in packageInfo?.checkItems">
                            <td>{{ checkitem.name }}</td>
                            <td>{{ checkitem.content }}</td>
                            <td>{{ checkitem.meaning }}</td>
                        </tr>
                    </table>
                </div>
            </a-drawer>
            <div><br><br><br></div>
        </section>
        <div class="bottom-btn">
            <div class="first">实付款: <span>{{ packageInfo?.price.toFixed(2) }}</span></div>
            <div class="last" @click="confirm">确认支付</div>
        </div>
    </div>

</template>
<style scoped>
@import '../assets/css/confirmorder.css';
.item {
    width: 100%;
    height: 20vw;
    color: #555;

    display: flex;
    align-items: center;
}
.item .item-left h3 {
    font-size: 4.3vw;
    font-weight: 600;
}
.item .item-left p {
    font-size: 4vw;
    margin-top: 1vw;
}
.item .item-right {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;

    user-select: none;
    cursor: pointer;
}
.item .item-right p {
    font-size: 4vw;
    margin-right: 2vw;
}
.item-content {
    /*display: none;*/
    width: 100%;
    background-color: #FFF;
}
.item-content table {
    width: 100%;
    border-collapse: collapse;
    font-size: 4vw;
    color: #555;
}
.item-content table tr {
    display: flex;
}
.item-content table tr td,
.setmeal li .item-content table tr th {
    flex: 1;
}
.item-content table tr th {
    text-align: center;
    background-color: #EEE;
    height: 10vw;
    line-height: 10vw;
}
.item-content table tr td {
    border: solid 1px #EFEFEF;
    box-sizing: border-box;
    padding: 2vw;
}

</style>