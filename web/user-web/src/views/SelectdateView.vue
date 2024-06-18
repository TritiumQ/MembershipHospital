<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import dayjs, { Dayjs } from 'dayjs';
import 'dayjs/locale/zh-cn';
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN';
dayjs.locale('zh-cn');
const router = useRouter();


const selectDate = ref<Dayjs>(dayjs("2022-01-18"));
const disabledDate = (current: Dayjs) => {
    // Can not select days before today and today
    return current && current < dayjs().endOf('day');
};
</script>
<template>

    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.back()"></i>
            <p>选择体检日期</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <a-date-picker :locale="locale" v-model:value="selectDate" format="YYYY-MM-DD" :disabled-date="disabledDate" />
        <!-- <section>
            <div class="date-box">
                <i class="fa fa-caret-left"></i>
                <p>2022年1月</p>
                <i class="fa fa-caret-right"></i>
            </div>
            <table>
                <tr>
                    <th>日</th>
                    <th>一</th>
                    <th>二</th>
                    <th>三</th>
                    <th>四</th>
                    <th>五</th>
                    <th>六</th>
                </tr>
            </table>
            <ul>
                <li>
                    <p></p>
                    <p></p>
                </li>
                <li>
                    <p></p>
                    <p></p>
                </li>
                <li>
                    <p></p>
                    <p></p>
                </li>
                <li>
                    <p></p>
                    <p></p>
                </li>
                <li>
                    <p></p>
                    <p></p>
                </li>
                <li>
                    <p></p>
                    <p></p>
                </li>
                <li>
                    <p>1</p>
                    <p></p>
                </li>
                <li>
                    <p>2</p>
                    <p></p>
                </li>
                <li>
                    <p>3</p>
                    <p></p>
                </li>
                <li>
                    <p>4</p>
                    <p></p>
                </li>
                <li>
                    <p>5</p>
                    <p></p>
                </li>
                <li>
                    <p>6</p>
                    <p></p>
                </li>
                <li>
                    <p>7</p>
                    <p></p>
                </li>
                <li>
                    <p>8</p>
                    <p></p>
                </li>
                <li>
                    <p>9</p>
                    <p></p>
                </li>
                <li>
                    <p>10</p>
                    <p></p>
                </li>
                <li>
                    <p>11</p>
                    <p></p>
                </li>
                <li>
                    <p>12</p>
                    <p></p>
                </li>
                <li>
                    <p>13</p>
                    <p></p>
                </li>
                <li>
                    <p>14</p>
                    <p></p>
                </li>
                <li>
                    <p>15</p>
                    <p></p>
                </li>
                <li>
                    <p>16</p>
                    <p></p>
                </li>
                <li>
                    <p>17</p>
                    <p></p>
                </li>
                <li>
                    <p class="fontcolor pselect">18</p>
                    <p>余56</p>
                </li>
                <li>
                    <p class="fontcolor">19</p>
                    <p>余66</p>
                </li>
                <li>
                    <p class="fontcolor">20</p>
                    <p>余123</p>
                </li>
                <li>
                    <p class="fontcolor">21</p>
                    <p>余178</p>
                </li>
                <li>
                    <p class="fontcolor">22</p>
                    <p>余200</p>
                </li>
                <li>
                    <p class="fontcolor">23</p>
                    <p>余200</p>
                </li>
                <li>
                    <p class="fontcolor">24</p>
                    <p>余200</p>
                </li>
                <li>
                    <p class="fontcolor">25</p>
                    <p>余200</p>
                </li>
                <li>
                    <p class="fontcolor">26</p>
                    <p>余200</p>
                </li>
                <li>
                    <p class="fontcolor">27</p>
                    <p>余200</p>
                </li>
                <li>
                    <p class="fontcolor">28</p>
                    <p>余200</p>
                </li>
                <li>
                    <p class="fontcolor">29</p>
                    <p>余200</p>
                </li>
                <li>
                    <p class="fontcolor">30</p>
                    <p>余200</p>
                </li>
                <li>
                    <p class="fontcolor">31</p>
                    <p>余200</p>
                </li>
            </ul>
        </section> -->
        <div class="bottom-btn">
            <div></div>
            <div onclick="location.href='confirmorder.html'">下一步</div>
        </div>
    </div>

</template>
<style scoped>
@import '../assets/css/selectdate.css';
</style>