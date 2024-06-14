<script setup lang="ts">
import { useRouter } from 'vue-router';
import DirectToLogin from '@/components/DirectToLogin.vue';
import { useUserStore } from '@/stores/userStore';

const router = useRouter();
const userStore = useUserStore();


</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper" v-if="userStore.isLogin">
        <div class="children">
            <RouterView />
        </div>
        <div class="bottom-ban"></div>
        <footer>
            <ul>
                <li @click="() => { router.push('/'); }">
                    <i class="fa fa-home"></i>
                    <p>云医院</p>
                </li>
                <li>
                    <i class="fa fa-opencart"></i>
                    <p>商城</p>
                </li>
                <li>
                    <i class="fa fa-compass"></i>
                    <p>发现</p>
                </li>
                <li @click="()=> { router.push('/personal'); }">
                    <i class="fa fa-user"></i>
                    <p>我</p>
                </li>
            </ul>
        </footer>
    </div>
    <DirectToLogin v-else />
</template>

<style scoped>
/*********************** 总容器 ***********************/
.wrapper {
    width: 100%;
    background-color: #F9F9F9;
}

/*********************** footer ***********************/
footer {
    width: 100%;
    height: 14.2vw;
    box-sizing: border-box;
    border-top: solid 1px #E9E9E9;
    background-color: #FFF;

    position: fixed;
    left: 0;
    bottom: 0;
}

footer ul {
    width: 100%;
    height: 14.2vw;
    display: flex;
    align-items: center;
    justify-content: space-around;
}

footer ul li {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    font-size: 3vw;
    color: #999;

    user-select: none;
    cursor: pointer;
}

footer ul li .fa {
    font-size: 5vw;
}


/*********************** common样式 ***********************/
.top-ban {
    width: 100%;
    height: 15.7vw;
}

.middle-ban {
    width: 100%;
    height: 1px;
    background-color: #E9E9E9;
}

.bottom-ban {
    width: 100%;
    height: 14.2vw;
}

.item-title {
    width: 100%;
    height: 13vw;
    box-sizing: border-box;
    padding: 0 3.6vw;
    border-bottom: solid 2px #F9F9F9;

    display: flex;
    align-items: center;
    justify-content: space-between;
}

.item-title p {
    height: 4.3vw;
    line-height: 4.3vw;

    box-sizing: border-box;
    padding-left: 3vw;

    font-size: 4.3vw;
    border-left: solid 2px #127A90;
    letter-spacing: 0.2vw;
}

.item-title span {
    font-size: 3vw;
    color: #666;
    user-select: none;
    cursor: pointer;
}
</style>