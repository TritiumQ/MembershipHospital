
<template>
  <div>
    <QueryForm @submit="handleQuerySubmit" />
    <div class="form-container" style="margin-top: 20px;">
      <el-table
          :data="orders"
          style="width: 100%"
          :loading="loading"
      >
        <el-table-column
            prop="orderId"
            label="预约单ID"
        />
        <el-table-column
            prop="userName"
            label="姓名"
        />
        <el-table-column
            prop="userSex"
            label="性别"
        />
        <el-table-column
            prop="userPhone"
            label="手机号"
        />
        <el-table-column
            prop="hospitalName"
            label="医院名称"
        />
        <el-table-column
            prop="orderDate"
            label="预约日期"
        />
        <el-table-column
            prop="packageId"
            label="套餐ID"
        />
        <el-table-column
            prop="packageName"
            label="套餐名称"
        />
        <el-table-column
            prop="status"
            label="状态"
        />
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button type="primary" size="small" @click="openDetails(scope.row.orderId)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        @current-change="handlePageChange"
    />

    <el-dialog v-model="detailsDialogVisible" width="70%" title="体检报告详情" :before-close="handleClose" destroy-on-close>
      <order-info :order="orderDetails.order" />
      <check-items
          v-model="orderDetails.checkItemReports"
          @update:modelValue="updateCheckItems"
      />
      <overall-results
          v-model="orderDetails.overallResults"
          @update:modelValue="updateOverallResults"
      />
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleSave">保存</el-button>
        <el-button  type="success"@click="handleArchive">归档</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, onMounted, reactive} from 'vue';
import { ElLoading, ElMessage, ElMessageBox } from 'element-plus';
import QueryForm from './QueryForm.vue';
import myAxios from '../../api/myAxios';
import OrderInfo from "./components/order-info.vue";
import CheckItems from "./components/check-items.vue";
import OverallResults from "./components/overall-result.vue";

const orders = ref([]);
const loading = ref(false);
const currentPage = ref(1);
const pageSize = ref(10);
const defaultHospitalId = localStorage.getItem('deptNo');
const lastQuery = ref({
  hospitalId: defaultHospitalId,
  status: 1,
  orderId: null,
  userPhone: null,
  userName: null,
  userSex: null,
  orderDate: null,
  packageId: null,
});

const formatDateToISO = (date) => {
  const newDate = new Date(date);
  newDate.setTime(newDate.getTime() - newDate.getTimezoneOffset() * 60 * 1000);
  return newDate.toISOString().split('T')[0];
};

const fetchOrders = async (query, page = 1, size = 10) => {
  const loadingInstance = ElLoading.service({
    lock: true,
    text: '加载中',
    spinner: 'el-icon-loading',
    background: 'rgba(255, 255, 255, 0.5)',
  });

  loading.value = true;
  try {
    query.orderDate = query.orderDate ? formatDateToISO(query.orderDate) : null;
    const response = await myAxios.post('/api/check/Orders', query, {
      params: {
        page: page - 1,
        size: size
      }
    });
    orders.value = response.data.data.map(order => ({
      ...order,
      userPhone: order.userId,
      userSex: order.userSex === 1 ? '男' : '女',
      status: order.status === 1 ? '未归档' : '归档'
    }));
  } catch (error) {
    console.error('Error fetching orders:', error);
  } finally {
    loading.value = false;
    loadingInstance.close();
  }
};

const handleQuerySubmit = (query) => {
  lastQuery.value = query;
  fetchOrders(query, currentPage.value, pageSize.value);
};

const handlePageChange = (page) => {
  currentPage.value = page;
  fetchOrders(lastQuery.value, page, pageSize.value);
};

onMounted(() => {
  fetchOrders(lastQuery.value, currentPage.value, pageSize.value);
});

const detailsDialogVisible = ref(false);
const orderDetails = ref({
  order: {},
  checkItemReports: [],
  overallResults: [],
});

const orderIdGable = ref(null);

const openDetails = async (orderId) => {
  const loadingInstance = ElLoading.service({
    lock: true,
    text: '加载中',
    spinner: 'el-icon-loading',
    background: 'rgba(255, 255, 255, 0.5)',
  });
  loading.value = true;
  try {
    const response = await myAxios.get(`/api/check/CheckReport?orderId=${orderId}`);
    if (response.data.code !== 200) {
      ElMessage.error(`获取订单详情失败: ${response.data.message}`);
      return;
    }
    orderDetails.value = response.data.data;
    detailsDialogVisible.value = true;
    orderIdGable.value = orderId;
    console.log(orderDetails);
  } catch (error) {
    console.error('Error fetching order details:', error);
    ElMessage.error('获取订单详情失败');
  } finally {
    loading.value = false;
    loadingInstance.close();
  }
};

const updateCheckItems = (newCheckItems) => {
  orderDetails.value.checkItemReports = newCheckItems;
};

const updateOverallResults = (newOverallResults) => {
  orderDetails.value.overallResults = newOverallResults;
};

const handleSave = async () => {
  const loadingInstance = ElLoading.service({
    lock: true,
    text: '加载中',
    spinner: 'el-icon-loading',
    background: 'rgba(255, 255, 255, 0.5)',
  });
  loading.value = true;
  try {
    const response = await myAxios.post('/api/report/editReport', {
      orderId: orderIdGable.value,
      checkItemReports: orderDetails.value.checkItemReports,
      overallResults: orderDetails.value.overallResults,
    });
    if (response.data.code === 200) {
      ElMessage.success('保存成功');
    } else {
      ElMessage.error(`保存失败: ${response.data.message}`);
    }
  } catch (error) {
    console.error('Error saving details:', error);
    ElMessage.error('保存失败');
  } finally {
    loading.value = false;
    loadingInstance.close();
  }
};

const handleArchive = async () => {
  const loadingInstance = ElLoading.service({
    lock: true,
    text: '加载中',
    spinner: 'el-icon-loading',
    background: 'rgba(255, 255, 255, 0.5)',
  });
  loading.value = true;
  try {
    const response = await myAxios.post('/api/report/saveReport', {
      orderId: orderIdGable.value,
      checkItemReports: orderDetails.value.checkItemReports,
      overallResults: orderDetails.value.overallResults,
    });
    if (response.data.code === 200) {
      handleQuerySubmit(lastQuery.value);
      ElMessage.success('归档成功');
      detailsDialogVisible.value = false;
    } else {
      ElMessage.error(`归档失败: ${response.data.message}`);
    }
  } catch (error) {
    console.error('Error archiving details:', error);
    ElMessage.error('归档失败');
  } finally {
    loading.value = false;
    loadingInstance.close();
  }
};

const handleClose = (done) => {
  ElMessageBox.confirm(
      '请注意修改数据保存后再退出！',
      '确认退出',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        done(); // 确认退出
      })
      .catch(() => {
        // 取消退出，不做任何操作
      });
};
</script>

<style scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>

<style>
.form-container {
  background-color: white;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}
.dialog-footer {
  text-align: right;
}
</style>
