<template>
  <div>
    <el-form :inline="true" @submit.native.prevent="searchDoctor">
      <el-row :gutter="20">
        <el-col :span="4">
          <el-form-item label="编号">
            <el-input v-model="searchForm.code" placeholder="请输入编号"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="searchForm.realName" placeholder="请输入医生姓名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="性别" style="width: 80%;">
            <el-select v-model="searchForm.sex" placeholder="请选择性别">
              <el-option label="全部" value=""></el-option>
              <el-option label="男" value="1"></el-option>
              <el-option label="女" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="医院" style="width: 80%;">
            <el-select v-model="searchForm.deptNo" placeholder="请选择医院">
              <el-option label="全部" value=""></el-option>
              <el-option v-for="hospital in hospitals" :key="hospital.id" :label="hospital.name" :value="hospital.id">
                {{ hospital.name }}
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="邮箱">
            <el-input v-model="searchForm.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchDoctor">查询</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onActivated } from 'vue';
import myAxios from "../../api/myAxios";
import ElMessage from 'element-plus';

// 使用ref创建响应式数据
const searchForm = ref({
  code: null,
  realName: null,
  sex: null,
  deptNo: null,
  email: null
});
const hospitals = ref([]); // 用于存储医院信息
const emit = defineEmits(['update-doctors']);

// 获取本地存储的医院信息
const getLocalHospitals = () => {
  const localHospitals = localStorage.getItem('hospitals');
  if (localHospitals) {
    hospitals.value = JSON.parse(localHospitals);
  } else {
    queryHospitals();
  }
};

// 查询医院信息
const queryHospitals = async () => {
  try {
    const response = await myAxios.get('/api/admin/queryHospitalVO');
    if (response.data.code === 200) {
      hospitals.value = response.data.data;
      localStorage.setItem('hospitals', JSON.stringify(hospitals.value));
    } else {
      ElMessage.error(response.data.message || '获取医院信息失败');
    }
  } catch (error) {
    ElMessage.error('网络请求异常');
  }
};

// 定义searchDoctor方法
const searchDoctor = async () => {
  try {
    const response = await myAxios.get('/api/admin/queryDoctorVO', { params: searchForm.value });
    // 注意：在 <script setup> 中使用 emit，需要导入 defineEmits
    emit('update-doctors', response.data.data);
  } catch (error) {
    console.error(error);
  }
};

defineExpose({
  searchDoctor
});

// 如果你需要在组件挂载时自动执行searchDoctor，使用onMounted生命周期钩子
onMounted(() => {
  getLocalHospitals();
  searchDoctor();
});

onActivated(() => {
  searchDoctor();
});
</script>
