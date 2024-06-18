<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();

</script>
<template>
    <div class="wrapper">
        <header>
            <div class="header-content">
                <div class="header-content-left">
                    <h1>沈阳云医院</h1>
                    <i class="fa fa-angle-down"></i>
                </div>
                <i class="fa fa-envelope-o"></i>
            </div>
            <div class="header-bottom"></div>
        </header>
        <div class="top-ban"></div>
        <nav>
            <div class="logo-img">
                <img src="@/assets/img/logo.png" />
            </div>
            <ul>
                <li>
                    <img src="@/assets/img/menu01.png" />
                    <div class="nav-item-text">
                        <h3>免费咨询</h3>
                        <p>新型冠状病毒肺炎</p>
                    </div>
                </li>
                <li>
                    <img src="@/assets/img/menu02.png" />
                    <div class="nav-item-text">
                        <h3>网络问诊</h3>
                        <p>图文视频网络咨询</p>
                    </div>
                </li>
                <li>
                    <img src="@/assets/img/menu03.png" />
                    <div class="nav-item-text">
                        <h3><span class="menu03-h3-span">e</span>心门诊</h3>
                        <p>复旦医科大学专家</p>
                    </div>
                </li>
                <li>
                    <img src="@/assets/img/menu04.png" />
                    <div class="nav-item-text">
                        <h3>慢病管理</h3>
                        <p>血压血糖健康管理</p>
                    </div>
                </li>
                <li>
                    <img src="@/assets/img/menu05.png" />
                    <div class="nav-item-text">
                        <h3>上门护理</h3>
                        <p>网上购买上门服务</p>
                    </div>
                </li>
                <li @click="()=>router.push('appointment')">
                    <img src="@/assets/img/menu06.png" />
                    <div class="nav-item-text">
                        <h3>团检预约</h3>
                        <p>团体体检套餐定制</p>
                    </div>
                    </li>
            </ul>
        </nav>
        <!-- 我的健康报告 -->
        <div class="report">
            <div class="item-title">
                <p>我的健康报告</p>
            </div>
            <div class="report-content">
                <p>随时随地查看体检报告</p>
                <div @click="()=>router.push('reportlist')">立即查看</div>
            </div>
        </div>
        <!-- 推荐医生 -->
        <div class="doctor">
            <div class="item-title">
                <p>推荐医生</p>
            </div>
            <ul>
                <li>
                    <img src="@/assets/img/doctor1.jpg" />
                    <h3>淳平</h3>
                    <p>无执照医师</p>
                </li>
                <li>
                    <img src="@/assets/img/doctor2.jpg" />
                    <h3>德川</h3>
                    <p>主任医师</p>
                </li>
                <li>
                    <img src="@/assets/img/doctor3.jpg" />
                    <h3>我修院</h3>
                    <p>主任医师</p>
                </li>
                <li>
                    <img src="@/assets/img/doctor4.jpg" />
                    <h3>田所浩二</h3>
                    <p>主任医师</p>
                </li>
                <li>
                    <img src="@/assets/img/doctor5.jpg" />
                    <h3>先辈</h3>
                    <p>主任医师</p>
                </li>
                <li>
                    <img src="@/assets/img/doctor6.jpg" />
                    <h3>远野</h3>
                    <p>副主任医师</p>
                </li>
            </ul>
        </div>
        <!-- 健康评估 -->
        <div class="assess">
            <div class="item-title">
                <p>健康评估</p>
                <span>更多</span>
            </div>
            <div class="assess-content">
                <div class="scroll-box" id="scrollBox">
                    <ul id="scrollBar">
                        <li><img src="@/assets/img/assess1.png" /></li>
                        <li><img src="@/assets/img/assess2.png" /></li>
                        <li><img src="@/assets/img/assess3.png" /></li>
                        <li><img src="@/assets/img/assess4.png" /></li>
                        <li><img src="@/assets/img/assess5.png" /></li>
                        <li><img src="@/assets/img/assess6.png" /></li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- 健康咨询 -->
        <div class="info">
            <div class="item-title">
                <p>健康咨询</p>
            </div>
            <ul>
                <li>
                    <img src="@/assets/img/info1.png" />
                    <div>
                        <h3>食雪之后，总是呕吐，我是不是变homo了？</h3>
                        <span>食雪一定会导致homo吗？</span>
                        <p><i class="fa fa-commenting-o"></i>1145</p>
                    </div>
                </li>
                <div class="middle-ban"></div>
                <li>
                    <img src="@/assets/img/info2.png" />
                    <div>
                        <h3>体检结果不好，要被淳平惩罚，怎么办？</h3>
                        <span>撅什么？</span>
                        <p><i class="fa fa-commenting-o"></i>1919</p>
                    </div>
                </li>
            </ul>
        </div>
    </div>

</template>
<style scoped>
@import '../assets/css/index.css';
</style>