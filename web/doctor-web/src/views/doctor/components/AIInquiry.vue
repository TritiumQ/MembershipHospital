<template>
  <div v-loading="loading" element-loading-text="AI正在生成报告...">
    <el-button type="primary" @click="handleAIInquiry" >
      {{ loading ? 'AI正在生成报告...' : aiResult ? '重新询问AI' : '询问AI' }}
    </el-button>
    <div v-if="aiResult" class="ai-result">
      <el-card>
        <h3>AI生成的报告</h3>
<!--        <p>{{ aiResult }}</p>-->
        <p>1. 分析: {{ analysis }}</p>
        <p>2. 建议：{{ suggestions }}</p>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import {computed, ref, toRefs} from 'vue';
import { ElMessage } from 'element-plus';
import myAxios from '../../../api/myAxios';

const props = defineProps({
  orderId: String,
  checkItemReports: Array,
  overallResults: Array,
});

const aiResult = ref('');
const loading = ref(false);

const analysis = computed(() => {
  const analysisMatch = aiResult.value.match(/分析:(.*?)建议:/s);
  return analysisMatch ? analysisMatch[1].trim() : '';
});

const suggestions = computed(() => {
  const suggestionsMatch = aiResult.value.match(/建议:(.*)/s);
  return suggestionsMatch ? suggestionsMatch[1].trim() : '';
});


const handleAIInquiry = async () => {
  loading.value = true;
  try {

    const aiResponse = await myAxios.post('/api/report/askAI', {
      orderId: props.orderId,
      checkItemReports: props.checkItemReports,
      overallResults: props.overallResults,
    });

    aiResult.value = aiResponse.data.data;
    ElMessage.success('AI报告生成成功');
  } catch (error) {
    console.error('Error during AI inquiry:', error);
    ElMessage.error('AI询问失败');
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.ai-result {
  margin-top: 20px;
}
</style>