<template>
  <div ref="tableContainer">
    <h2>总评</h2>
    <el-table :data="results" border>
      <el-table-column prop="title" label="标题" :width="titleWidth">
        <template #default="scope">
          <el-input v-model="scope.row.title" placeholder="请输入标题" />
        </template>
      </el-table-column>
      <el-table-column prop="content" label="内容" :width="contentWidth">
        <template #default="scope">
          <el-input type="textarea" v-model="scope.row.content" placeholder="请输入内容（不多于200字）" />
        </template>
      </el-table-column>
    </el-table>
    <el-button @click="addOverallResult" type="primary" class="add-button">新增项</el-button>
    <el-button @click="deleteEmptyRows" type="danger" class="add-button">删除空行</el-button>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, onBeforeUnmount, computed } from "vue";
import { defineProps, defineEmits } from "vue";
import {ElMessage} from "element-plus";

const props = defineProps({
  modelValue: Array
});

const emits = defineEmits(['update:modelValue']);

const results = ref(props.modelValue);

// watch for changes and emit update
watch(results, (newVal) => {
  emits('update:modelValue', newVal);
});

const addOverallResult = () => {
  if (results.value.length === 3) {
    ElMessage.warning('最多只能添加3项总评');
  }
  else results.value.push({ title: '', content: '' });
}

const tableContainer = ref(null);
const tableWidth = ref(0);

const titleWidth = computed(() => `${(2 / 10) * tableWidth.value}px`);
const contentWidth = computed(() => `${(8 / 10) * tableWidth.value}px`);

const updateTableWidth = () => {
  tableWidth.value = tableContainer.value ? tableContainer.value.clientWidth : 0;
};

const deleteEmptyRows = () => {
  results.value = results.value.filter(row => row.title.trim() !== '' || row.content.trim() !== '');
}

onMounted(() => {
  updateTableWidth();
  const resizeObserver = new ResizeObserver(updateTableWidth);
  if (tableContainer.value) {
    resizeObserver.observe(tableContainer.value);
  }
  onBeforeUnmount(() => {
    if (resizeObserver && tableContainer.value) {
      resizeObserver.unobserve(tableContainer.value);
    }
  });
});
</script>

<style scoped>
.overall-result {
  margin-bottom: 20px;
}
.add-button {
  background-color: #409EFF;
  border-color: #409EFF;
  color: #fff;
}
</style>
