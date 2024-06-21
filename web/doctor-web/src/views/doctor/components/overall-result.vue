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
  results.value.push({ title: '', content: '' });
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

<!--<template>-->
<!--  <div>-->
<!--    <h2>总评</h2>-->
<!--    <div v-for="(result, index) in results" :key="index" class="overall-result">-->
<!--      <el-row :gutter="20">-->
<!--        <el-col :span="12">-->
<!--          标题：<el-input v-model="result.title" placeholder="请输入标题" />-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          内容：<el-input v-model="result.content" placeholder="请输入内容" />-->
<!--        </el-col>-->
<!--      </el-row>-->
<!--    </div>-->
<!--    <el-button @click="addOverallResult">新增项</el-button>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup>-->
<!--import {ref, watch} from "vue";-->
<!--import {defineProps, defineEmits} from "vue";-->
<!--const props = defineProps({-->
<!--  modelValue: Array-->
<!--});-->

<!--const emits = defineEmits(['update:modelValue']);-->

<!--const results = ref(props.modelValue);-->

<!--// watch for changes and emit update-->
<!--watch(results, (newVal) => {-->
<!--  emits('update:modelValue', newVal);-->
<!--});-->

<!--const addOverallResult = () => {-->
<!--  results.value.push({ title: '', content: '' });-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.overall-result {-->
<!--  margin-bottom: 20px;-->
<!--}-->
<!--</style>-->