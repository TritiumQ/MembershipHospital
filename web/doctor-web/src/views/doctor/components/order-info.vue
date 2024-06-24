<template>
  <div>
    <h2>体检报告单</h2>
    <div class="order-info">
      <el-row :gutter="20">
        <el-col :span="8">预约号：{{ order.orderId }}</el-col>
        <el-col :span="8">姓名：{{ order.userName }}</el-col>
        <el-col :span="8">性别：{{ getSex(order.userSex) }}</el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">生日：{{ order.userBirthday }}</el-col>
        <el-col :span="8">手机号：{{ order.userId }}</el-col>
        <el-col :span="8">医院：{{ order.hospitalName }}</el-col>
      </el-row>
      <el-row :gutter="20">
        <!--      <el-col :span="8">套餐ID：{{ order.packageId }}</el-col>-->
        <el-col :span="8">日期：{{ order.orderDate }}</el-col>
        <el-col :span="8">体检项目：{{ order.packageName }}</el-col>
        <el-col :span="8">状态：{{ getStatus(order.status) }}</el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
const props = defineProps({
  order: Object,
});

const getSex = (sex) => {
  return sex === 1 ? '男' : '女';
}

const getStatus = (status) => {
  return status === 1 ? '未归档' : '归档';
}


</script>
<style scoped>
.order-info {
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

</style>