<script setup lang="ts">
import type { Order, OrderQuery, OrderInfo } from '@/model/order';
import { useUserStore } from '@/stores/userStore';
import { apiService } from '@/util/request';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Modal } from 'ant-design-vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { createVNode } from 'vue';
import { message } from 'ant-design-vue';
const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const orders = ref<OrderInfo[]>([]);
const query = ref<OrderQuery>({
    userId: userStore.user?.id,
});

const getOrders = async () => {
    loading.value = true;
    await apiService.post<OrderInfo[]>('/order/list', query.value).then((res) => {
        if (res.isSuccess()) {
            orders.value = res.data;
        }
        else {
            console.log(res.message);
        }
    }).catch((err) => {
        console.log(err);
    }).finally(() => {
        loading.value = false;
    });
}

const loading = ref(false);

const confirm = async (orderId: number) => {
    Modal.confirm({
        title: '确定取消预约?',
        icon: createVNode(ExclamationCircleOutlined),
        onOk() {
            loading.value = true;
            apiService.post<any>(`/order/cancel/${orderId}`, {}).then(async (res) => {
                if (res.isSuccess()) {
                    message.success('取消成功');
                    await getOrders();
                }
                else {
                    message.error(res.message);
                }
            }).catch((err) => {
                console.log(err);

            }).finally(() => {
                setTimeout(() => {
                    loading.value = false;
                }, 1000);
            });
        },
        // eslint-disable-next-line @typescript-eslint/no-empty-function
        onCancel() {
        },
    });
}

onMounted(async () => {
    await getOrders();
});
const currentOrderInfo = ref<OrderInfo | null>(null)
const open = ref<boolean>(false)
const showDrawer = (idx: number) => {
    open.value = true;
    currentOrderInfo.value = orders.value[idx];
};
const onClose = () => {
    open.value = false;
    currentOrderInfo.value = null;
};

</script>
<template>
    <a-spin :spinning="loading">
        <!-- 总容器 -->
        <div class="wrapper">
            <header>
                <i class="fa fa-angle-left" @click="() => router.push('/')"></i>
                <p>我的预约</p>
                <div></div>
            </header>
            <div class="top-ban"></div>
            <ul v-for="order, idx in orders">
                <li>
                    <div class="left" @click="() => showDrawer(idx)">
                        <p>{{ order.date }}</p>
                        <p>{{ order.packageName }}</p>
                    </div>
                    <div v-if="order.state === 1 && !order.deprecated" class="right" @click="() => confirm(order.id)">
                        <p>取消预约<i class="fa fa-close"></i></p>

                    </div>
                    <div v-else-if="order.deprecated" class="right-cancel">
                        已取消
                    </div>
                    <div v-else-if="order.state === 2 && !order.deprecated" class="right-complete">
                        已完成
                    </div>
                </li>
            </ul>
            <a-drawer v-model="currentOrderInfo" :title="'订单详情'" placement="bottom" :closable="false" :open="open"
                @close="onClose">
                <div class="title">
                    <p>体检人信息</p>
                </div>
                <div class="detail">
                    <table>
                        <tr>
                            <td>姓名:</td>
                            <td>{{ currentOrderInfo?.user?.realName }}</td>
                        </tr>
                        <tr>
                            <td>证件号码:</td>
                            <td>{{ currentOrderInfo?.user?.idCard }}</td>
                        </tr>
                        <tr>
                            <td>出生日期:</td>
                            <td>{{ currentOrderInfo?.user?.birthday }}</td>
                        </tr>
                        <tr>
                            <td>手机号码:</td>
                            <td>{{ currentOrderInfo?.user?.id }}</td>
                        </tr>
                    </table>
                    <div class="title">
                        <p>体检日期</p>
                    </div>
                    <table>
                        <tr>
                            <td>{{ currentOrderInfo?.date }}</td>
                        </tr>
                    </table>
                    <div class="title">
                        <p>体检机构</p>
                    </div>
                    <table>
                        <tr>
                            <td colspan="2">{{ currentOrderInfo?.hospital?.name }}</td>
                        </tr>
                        <tr>
                            <td>营业时间:</td>
                            <td>{{ currentOrderInfo?.hospital?.businessHours }}</td>
                        </tr>
                        <tr>
                            <td>采血截止:</td>
                            <td>{{ currentOrderInfo?.hospital?.deadline }}</td>
                        </tr>
                        <tr>
                            <td>机构电话:</td>
                            <td>{{ currentOrderInfo?.hospital?.telephone }}</td>
                        </tr>
                        <tr>
                            <td>机构地址:</td>
                            <td>{{ currentOrderInfo?.hospital?.address }}</td>
                        </tr>
                    </table>
                    <div class="title">
                        <p>套餐类型</p>
                    </div>
                    <div class="item">
                        <div class="item-left">
                            <h3>{{ currentOrderInfo?.user?.sex == 0 ? "女士" : "男士" }}套餐: ￥{{
                                currentOrderInfo?.packageField?.price }}</h3>
                            <p>{{ currentOrderInfo?.packageField?.name }}</p>
                        </div>
                        <!-- <div class="item-right" @click="showDrawer">
                        <p>详情</p>
                        <i class="fa fa-list"></i>
                    </div> -->
                    </div>
                </div>
            </a-drawer>

            <div class="bottom-ban"></div>
        </div>
    </a-spin>
</template>
<style scoped>
@import '../assets/css/appointmentlist.css';

ul li .right {
    font-size: 4vw;
    color: #E6A23C;
}

ul li .right-cancel {
    font-size: 4vw;
    color: #FF0000;
}

ul li .right-complete {
    font-size: 4vw;
    color: #00921d;
}

.detail .item {
    width: 100%;
    height: 20vw;
    color: #555;

    display: flex;
    align-items: center;
}

.detail .item .item-left h3 {
    font-size: 4.3vw;
    font-weight: 600;
}

.detail.item .item-left p {
    font-size: 4vw;
    margin-top: 1vw;
}

.detail.item .item-right {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;

    user-select: none;
    cursor: pointer;
}

.detail.item .item-right p {
    font-size: 4vw;
    margin-right: 2vw;
}

.detail.title {
    width: 100%;
    height: 12vw;
    border-bottom: solid 1px #EEE;

    display: flex;
    align-items: center;
}

.detail.title p {
    height: 3.4vw;
    line-height: 3.4vw;
    font-size: 4.2vw;
    font-weight: 600;
    box-sizing: border-box;
    padding-left: 3vw;
    border-left: solid 2px #127A90;
}

.detail table {
    font-size: 3.6vw;
    color: #555;
    margin-top: 2vw;
}

.detail table tr {
    line-height: 8vw;
}

.detail table tr td:first-child {
    width: 22%;
}


</style>