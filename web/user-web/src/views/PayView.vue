<script setup lang="ts">
import { onBeforeRouteLeave, useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import { onMounted, ref } from 'vue';
import type { Order, OrderCreate, OrderInfo } from '@/model/order';
import { message } from 'ant-design-vue';
import { apiService } from '@/util/request';
const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const orderId = ref(route.query.orderId as string || '0');

const expired = ref(false);
const restTime = ref(5);

const codeFrame = ref('');

const toMMSS = (time: number) => {
    const minutes = Math.floor(time / 60);
    const seconds = time % 60;
    return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
};

const timer = ref(0);

const payTimer = ref(0);

const reset = () => {
    apiService.get<string>(`order/paycode/${orderId.value}`).then((res) => {
        if (res.isSuccess())
        {
            codeFrame.value = res.data;
        }
        else
        {
            console.error(res.message);
        }
    }).catch((err) => {

        console.error(err);
    });
    restTime.value = 300;
    expired.value = false;
    timer.value = setInterval(() => {
        if (restTime.value <= 0) {
            expired.value = true;
            clearInterval(timer.value);
            clearInterval(payTimer.value);
        }
        else {
            restTime.value--;
        }
    }, 1000);
    payTimer.value = setInterval(() => {
        apiService.get<number>(`order/paystatus/${orderId.value}`).then((res) => {
            if (res.isSuccess())
            {
                if (res.data === 1)
                {
                    clearInterval(payTimer.value);
                    clearInterval(timer.value);
                    message.success('支付成功');
                    setTimeout(() => {
                        router.push('/appointmentsuccess');
                    }, 3000);
                }
            }
            else
            {
                console.error(res.message);
            }
        }).catch((err) => {
            console.error(err);
        });
    }, 5000);
}; 



onMounted(() => {
    reset();
});

onBeforeRouteLeave(() => {
    console.log('leave and destroy timer');
    clearInterval(timer.value);
    clearInterval(payTimer.value);
});

</script>
<template>
    <div v-if="expired">
        <p class="msg">支付超时，请重新下单</p>
        <div class="bottom-btn">
            <div class="last" @click="reset">重新下单</div>
        </div>
    </div>
    <div v-else>
        <iframe class="paycode" :srcdoc="codeFrame" width="200" height="200" style="overflow: hidden" frameborder="no" marginwidth="0" marginheight="0" scrolling="no"></iframe>
        <p class="msg">请在5分钟内使用支付宝扫码支付</p>
        <p class="timer">{{ toMMSS(restTime) }}</p>
        <p class="msg">支付成功后若未自动跳转，请点击按钮继续</p>
        <div class="bottom-btn">
            <div class="last">完成支付</div>
        </div>
    </div>
</template>
<style scoped>
.timer {
    text-align: center;
    font-size: 5vw;
    margin-top: 10vw;
}

.msg {
    text-align: center;
    font-size: 5vw;
    margin-top: 10vw;

}

.paycode {
    margin: 0 auto;
    display: block;
}

.bottom-btn {
    width: 100%;
    height: 12vw;
    background-color: #FFF;

    position: fixed;
    left: 0;
    bottom: 14.2vw;

    display: flex;
}

.bottom-btn .last {
    flex: 1;
    background-color: #117C94;
    line-height: 12vw;
    text-align: center;
    font-size: 5vw;
    color: #FFF;

    user-select: none;
    cursor: pointer;
}
</style>