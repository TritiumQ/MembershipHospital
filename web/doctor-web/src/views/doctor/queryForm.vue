<template>
  <div class="form-container">
    <el-form :model="query" label-width="120px" @submit.prevent="handleSubmit" :label-class="['form-label']">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="预约单ID" prop="orderId">
            <el-input v-model="query.orderId" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="姓名" prop="userName">
            <el-input v-model="query.userName" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="手机号" prop="userPhone">
            <el-input v-model="query.userPhone" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="报告状态" prop="status">
            <el-select v-model="query.status" placeholder="请选择" required>
              <el-option label="未归档" :value="1" />
              <el-option label="归档" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="性别" prop="userSex">
            <el-select v-model="query.userSex" placeholder="请选择">
              <el-option label="全部" :value=null />
              <el-option label="男" :value="1" />
              <el-option label="女" :value="0" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="套餐ID" prop="packageId">
            <el-input v-model="query.packageId" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="预约日期" prop="orderDate">
            <el-date-picker v-model="query.orderDate" type="date" placeholder="选择日期" />
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item>
            <el-button type="primary" @click="handleSubmit">查询</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const query = ref({
  orderId: null,
  userPhone: null,
  userName: null,
  userSex: null,
  hospitalId: null,
  orderDate: null,
  packageId: null,
  status: 1,
});

const emit = defineEmits(['submit']);

const handleSubmit = () => {
  query.value.hospitalId = localStorage.getItem('deptNo');
  emit('submit', query.value);
};
</script>

<style scoped>
.form-container {
  background-color: white;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.form-label {
  font-weight: bold;
}
</style>