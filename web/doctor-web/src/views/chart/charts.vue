<template>
  <div>
    <div class="form-container">
      <el-form :model="form" @submit.prevent="fetchStatistics">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="起始日期">
              <el-date-picker
                  v-model="form.start"
                  type="date"
                  placeholder="Select Start Date"
                  :picker-options="pickerOptions"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="结束日期">
              <el-date-picker
                  v-model="form.end"
                  type="date"
                  placeholder="Select End Date"
                  :picker-options="pickerOptions"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="统计粒度">
              <el-select v-model="form.granularity" placeholder="Select Granularity">
                <el-option label="Year" value="Y" />
                <el-option label="Month" value="M" />
                <el-option label="Day" value="D" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="套餐：">
          <el-checkbox-group v-model="form.requiredData">
            <el-checkbox
                v-for="pkg in packages"
                :label="pkg"
                :key="pkg.id"
                @change="pkg.isSelected = !pkg.isSelected"
            >
              {{ pkg.name }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-button type="primary" @click="fetchStatistics">Fetch Statistics</el-button>
      </el-form>
      <el-divider>数据统计</el-divider>
    </div>


    <div id="chart-container">
      <div id="main"></div>
    </div>
  </div>
</template>


<script setup>
import {ref, onMounted, reactive, computed, watch} from 'vue';
import * as echarts from 'echarts';
import { ElMessage } from 'element-plus';
import axios from 'axios';
import myAxios from "../../api/myAxios";

const form = reactive({
  stsType: 1,
  start: '',
  end: '',
  granularity: '',
  requiredData: [],
});

const packages = ref([]);
const chartDom = ref(null);
let myChart = null;

// 获取当前时间并设置日期选择器选项
const pickerOptions = {
  disabledDate(time) {
    return time.getTime() > Date.now();
  },
};

// 获取所有package
const fetchPackages = async () => {
  try {
    const response = await myAxios.get('/api/package/getAll');
    if (response.data.code === 200) {
      packages.value = response.data.data.map(pkg => ({ ...pkg, isSelected: false }));
    } else {
      ElMessage.error('Failed to fetch packages');
    }
  } catch (error) {
    ElMessage.error('Error fetching packages');
  }
};

// 监控选定的包裹列表
const selectedPackages = computed(() => {
  return packages.value
      .filter(pkg => pkg.isSelected)
      .map(pkg => ({ id: pkg.id, name: pkg.name, isRequired: true }));
});

// 设置统计数据请求函数
const fetchStatistics = async () => {
  const formatDateToISO = (date) => {
    const newDate = new Date(date);
    newDate.setTime(newDate.getTime() - newDate.getTimezoneOffset() * 60 * 1000);
    return newDate.toISOString().split('T')[0];
  };

  const requestData = {
    stsType: form.stsType,
    start: formatDateToISO(form.start),
    end: formatDateToISO(form.end),
    granularity: form.granularity,
    requiredData: selectedPackages.value,
  };

  try {
    const response = await myAxios.post('/api/statistics/getStatistics', requestData);
    if (response.data.code === 200) {
      updateChart(response.data.data);
    } else {
      ElMessage.error('Failed to fetch statistics');
    }
  } catch (error) {
    console.error(error);
    ElMessage.error('Error fetching statistics');
  }
};

// 更新ECharts图表
const updateChart = (statisticsData) => {
  if (!myChart) return;

  const sourceData = statisticsData.data.map((flow) => [flow.flowName, ...flow.flowData]);

  const option = {
    legend: {
      orient: 'vertical',
      left: 'right'
    },
    tooltip: {
      trigger: 'axis',
      showContent: false,
    },
    dataset: {
      source: sourceData,
    },
    xAxis: { type: 'category' },
    yAxis: { gridIndex: 0 },
    grid: { top: '60%' }, // Adjust grid layout to avoid overlap with the pie chart
    series: [
      ...Array(sourceData.length - 1).fill({
        type: 'line',
        smooth: true,
        seriesLayoutBy: 'row',
        emphasis: { focus: 'series' },
      }),
      {
        type: 'pie',
        id: 'pie',
        radius: '30%',
        center: ['50%', '30%'], // Position the pie chart above the line chart
        emphasis: { focus: 'self' },
        label: {
          formatter: '{b}: {@2012} ({d}%)',
        },
        encode: {
          itemName: 'product',
          value: '2012',
          tooltip: '2012',
        },
      },
    ],
  };

  myChart.setOption(option);
};
// const updateChart = (statisticsData) => {
//   if (!myChart) return;
//
//   const sourceData = statisticsData.data.map((flow) => [flow.flowName, ...flow.flowData]);
//
//   const option = {
//     legend: {},
//     tooltip: {
//       trigger: 'axis',
//       showContent: false,
//     },
//     dataset: {
//       source: sourceData,
//     },
//     xAxis: { type: 'category' },
//     yAxis: { gridIndex: 0 },
//     grid: { top: '55%' },
//     series: Array(sourceData.length - 1).fill({
//       type: 'line',
//       smooth: true,
//       seriesLayoutBy: 'row',
//       emphasis: { focus: 'series' },
//     }).concat({
//       type: 'pie',
//       id: 'pie',
//       radius: '30%',
//       center: ['50%', '25%'],
//       emphasis: { focus: 'self' },
//       label: {
//         formatter: '{b}: {@2012} ({d}%)',
//       },
//       encode: {
//         itemName: 'product',
//         value: '2012',
//         tooltip: '2012',
//       },
//     }),
//   };
//
//   myChart.setOption(option);
// };

// 初始化ECharts
onMounted(() => {
  chartDom.value = document.getElementById('main');
  myChart = echarts.init(chartDom.value);

  fetchPackages();

  myChart.on('updateAxisPointer', function (event) {
    const xAxisInfo = event.axesInfo[0];
    if (xAxisInfo) {
      const dimension = xAxisInfo.value + 1;
      myChart.setOption({
        series: {
          id: 'pie',
          label: {
            formatter: '{b}: {@[' + dimension + ']} ({d}%)',
          },
          encode: {
            value: dimension,
            tooltip: dimension,
          },
        },
      });
    }
  });
});
// 当选中的包裹列表变化时更新图表
watch(selectedPackages, fetchStatistics);
</script>

<style scoped>
#chart-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: white;
}

.form-container {
  background-color: white;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

#main {
  width: 100%;
  max-width: 1200px;
  height: 600px;
}
</style>









<!--<template>-->
<!--  <div id="main" style="width: 100%; height: 400px;"></div>-->
<!--</template>-->

<!--<script setup>-->
<!--import { onMounted } from 'vue';-->
<!--import * as echarts from 'echarts';-->

<!--onMounted(() => {-->
<!--  const chartDom = document.getElementById('main');-->
<!--  const myChart = echarts.init(chartDom);-->
<!--  let option;-->

<!--  setTimeout(() => {-->
<!--    option = {-->
<!--      legend: {},-->
<!--      tooltip: {-->
<!--        trigger: 'axis',-->
<!--        showContent: false,-->
<!--      },-->
<!--      dataset: {-->
<!--        source: [-->
<!--          ['product', '2012', '2013', '2014', '2015', '2016', '2017'],-->
<!--          ['Milk Tea', '56.5', '82.1', '88.7', '70.1', '53.4', '85.1'],-->
<!--          ['Matcha Latte', 51.1, 51.4, 55.1, 53.3, 73.8, 68.7],-->
<!--          ['Cheese Cocoa', 40.1, 62.2, 69.5, 36.4, 45.2, 32.5],-->
<!--          ['Walnut Brownie', 25.2, 37.1, 41.2, 18, 33.9, 49.1],-->
<!--        ],-->
<!--      },-->
<!--      xAxis: { type: 'category' },-->
<!--      yAxis: { gridIndex: 0 },-->
<!--      grid: { top: '55%' },-->
<!--      series: [-->
<!--        {-->
<!--          type: 'line',-->
<!--          smooth: true,-->
<!--          seriesLayoutBy: 'row',-->
<!--          emphasis: { focus: 'series' },-->
<!--        },-->
<!--        {-->
<!--          type: 'line',-->
<!--          smooth: true,-->
<!--          seriesLayoutBy: 'row',-->
<!--          emphasis: { focus: 'series' },-->
<!--        },-->
<!--        {-->
<!--          type: 'line',-->
<!--          smooth: true,-->
<!--          seriesLayoutBy: 'row',-->
<!--          emphasis: { focus: 'series' },-->
<!--        },-->
<!--        {-->
<!--          type: 'line',-->
<!--          smooth: true,-->
<!--          seriesLayoutBy: 'row',-->
<!--          emphasis: { focus: 'series' },-->
<!--        },-->
<!--        {-->
<!--          type: 'pie',-->
<!--          id: 'pie',-->
<!--          radius: '30%',-->
<!--          center: ['50%', '25%'],-->
<!--          emphasis: {-->
<!--            focus: 'self',-->
<!--          },-->
<!--          label: {-->
<!--            formatter: '{b}: {@2012} ({d}%)',-->
<!--          },-->
<!--          encode: {-->
<!--            itemName: 'product',-->
<!--            value: '2012',-->
<!--            tooltip: '2012',-->
<!--          },-->
<!--        },-->
<!--      ],-->
<!--    };-->

<!--    myChart.on('updateAxisPointer', function (event) {-->
<!--      const xAxisInfo = event.axesInfo[0];-->
<!--      if (xAxisInfo) {-->
<!--        const dimension = xAxisInfo.value + 1;-->
<!--        myChart.setOption({-->
<!--          series: {-->
<!--            id: 'pie',-->
<!--            label: {-->
<!--              formatter: '{b}: {@[' + dimension + ']} ({d}%)',-->
<!--            },-->
<!--            encode: {-->
<!--              value: dimension,-->
<!--              tooltip: dimension,-->
<!--            },-->
<!--          },-->
<!--        });-->
<!--      }-->
<!--    });-->

<!--    myChart.setOption(option);-->
<!--  });-->

<!--  option && myChart.setOption(option);-->
<!--});-->
<!--</script>-->

<!--<style scoped>-->
<!--#main {-->
<!--  width: 100%;-->
<!--  height: 400px;-->
<!--}-->
<!--</style>-->