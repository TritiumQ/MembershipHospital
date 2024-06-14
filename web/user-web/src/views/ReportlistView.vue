<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();
</script>
<template>

    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" onclick="history.go(-1)"></i>
            <p>健康档案</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <section>
            <img src="@/assets/img/report.png" />
            <ul>
                <li>
                    <div class="left">
                        <i class="fa fa-file-text-o"></i>
                        <div>
                            <p>2021年11月24日 体检报告</p>
                            <p>沈阳世贸熙康健康管理中心</p>
                        </div>
                    </div>
                    <div class="right" onclick="location.href='report.html'">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
                <li>
                    <div class="left">
                        <i class="fa fa-file-text-o"></i>
                        <div>
                            <p>2019年10月11日 体检报告</p>
                            <p>沈阳熙康云医院</p>
                        </div>
                    </div>
                    <div class="right" onclick="location.href='report.html'">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
            </ul>
        </section>
        <div class="bottom-ban"></div>
        <footer>
            <ul>
                <li onclick="location.href='index.html'">
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
                <li onclick="location.href='personal.html'">
                    <i class="fa fa-user"></i>
                    <p>我</p>
                </li>
            </ul>
        </footer>
    </div>

</template>
<style scoped>
@import '../assets/css/reportlist.css';
</style>