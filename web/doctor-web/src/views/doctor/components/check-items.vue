<template>
  <div  class="component-container">
    <div class="component-content">
      <h2 class="component-header">
        检查项
        <el-select v-model="selectedCheckItem" placeholder="请选择检查项" @change="filterReports" class="check-item-select">
          <el-option label="全部" value="全部" />
          <el-option v-for="(report, index) in checkItemOptions" :key="index" :label="report.name" :value="report.name" />
        </el-select>
      </h2>
      <div v-for="(report, index) in filteredReports" :key="index" class="check-item">
        <h3>{{ report.name }}</h3>
        <el-table :data="report.checkItemDetailedReports" border>
          <el-table-column prop="detailName" label="检查项详情">
            <template #default="scope">
              {{ scope.row.detailName }}<span v-if="scope.row.unit">（{{ scope.row.unit }}）</span><template v-if="scope.row.error === '异常'"> ⚠</template>
            </template>
          </el-table-column>
          <el-table-column label="输入值">
            <template #default="scope">
              <el-input v-model="scope.row.value" placeholder="请输入值" @input="validate(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column prop="error" label="是否异常">
            <template #default="scope">
              <el-select v-model="scope.row.error" placeholder="请选择错误">
                <el-option v-for="option in errorOptions" :key="option.value" :label="option.label" :value="option.value" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="normalValueDescription" label="正常范围">
            <template #default="scope">
              <el-input v-model="scope.row.normalValueDescription" disabled />
            </template>
          </el-table-column>
        </el-table>
        <el-row :gutter="20">
          <el-col :span="24">
            评价：<el-input type="textarea" v-model="report.review" placeholder="请输入评价（100字以内）" />
          </el-col>
        </el-row>
      </div>
    </div>

  </div>
</template>

<script setup>
import { watch } from 'vue';
import { ref, computed, defineProps, defineEmits } from 'vue';

const props = defineProps({
  modelValue: Array
});

const emits = defineEmits(['update:modelValue']);

const errorOptions = [
  { label: '无异常', value: '无异常' },
  { label: '异常', value: '异常' }
];

const reports = ref([...props.modelValue]);
const selectedCheckItem = ref('全部');
const filteredReports = ref([...reports.value]);

const checkItemOptions = computed(() => {
  return reports.value.map(report => ({
    name: report.name
  }));
});

// Filter reports based on selected check item
const filterReports = () => {
  if (selectedCheckItem.value === '全部') {
    filteredReports.value = [...reports.value];
  } else {
    filteredReports.value = reports.value.filter(report => report.name === selectedCheckItem.value);
  }
};

// watch for changes in props.modelValue and update reports
watch(() => props.modelValue, (newVal) => {
  if (JSON.stringify(newVal) !== JSON.stringify(reports.value)) {
    reports.value = [...newVal];
    filterReports();
  }
}, { immediate: true });

// watch for changes in reports and emit update
watch(reports, (newVal) => {
  if (JSON.stringify(newVal) !== JSON.stringify(props.modelValue)) {
    emits('update:modelValue', newVal);
    filterReports();
  }
});



// Input validation function
const validate = (row) => {
  if (row.type === 1) {
    const value = parseFloat(row.value);
    if (!isNaN(value)) {
      if (value < row.minRange || value > row.maxRange) {
        row.error = '异常';
        row.errorType = '1';
      } else {
        row.error = '无异常';
        row.errorType = '0';
      }
    } else {
      row.error = '异常';
      row.errorType = '1';
    }
  }
  if (row.error === '异常') {
    row.errorType = '1';
  } else {
    row.errorType = '0';
  }
};
</script>

<style scoped>
.check-item {
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.el-table .el-table__header-wrapper,
.el-table .el-table__body-wrapper {
  display: flex;
  flex-direction: column;
}
.el-table .el-table__header,
.el-table .el-table__body {
  display: flex;
  flex: 1;
}

.check-item-select {
  width: 150px;
  background-color: #e0f7da;
}
.component-container {
  border: 2px solid #f5e79e;
  border-radius: 10px;
  padding: 20px;
  background-color: #fffaf0;
}
.component-header {
  //border: 2px solid #f5e79e;
  border-radius: 5px;
  padding: 10px;
  background-color: #fffaf0;
}
.component-content {
  padding: 1px;
  background-color: #fff;
}
</style>

