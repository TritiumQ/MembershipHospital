<template>
  <div>
    <div class="table-actions">
      <search-doctor @update-doctors="handleDoctorUpdate" ref="search"></search-doctor>
      <el-button @click="showAddDoctorDialog = true" type="success">新增医生</el-button>
      <el-button type="primary" @click="triggerUpload">批量导入</el-button>
      <input type="file" ref="fileUpload" @change="handleFileChange" style="display: none;" accept=".xlsx, .xls"/>
      <el-button @click="confirmAndDeleteDoctors" type="danger" :disabled="selectedDoctors.length === 0" style="margin-left: 10px;">批量删除</el-button>
      <el-button @click="resetPasswords" type="danger" :disabled="selectedDoctors.length === 0" style="margin-left: 10px;">重置密码</el-button>
    </div>

    <!-- Add Doctor Dialog -->
    <el-dialog v-model="showAddDoctorDialog" title="新增医生" width="500">
      <el-form :model="newDoctor" label-position="right" label-width="80px">
        <el-form-item label="编号">
          <el-input v-model="newDoctor.code"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="newDoctor.realName"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="newDoctor.sex" placeholder="性别">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门">
          <el-select v-model="newDoctor.deptNo" placeholder="部门">
            <el-option v-for="hospital in hospitals" :key="hospital.id" :label="hospital.name" :value="hospital.id">
              {{ hospital.name }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮件">
          <el-input v-model="newDoctor.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="showAddDoctorDialog = false">取消</el-button>
    <el-button @click="handleAddDoctor" type="primary">确认</el-button>
  </span>
    </el-dialog>

    <el-dialog v-model="showUpdateDoctorDialog" title="修改医生信息" width="500">
      <el-form :model="updateDoctor" label-position="right" label-width="80px">
        <el-form-item label="编号">
          <el-input v-model="updateDoctor.code"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="updateDoctor.realName"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="updateDoctor.sex" placeholder="性别">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门">
          <el-select v-model="updateDoctor.deptNo" placeholder="部门">
            <el-option v-for="hospital in hospitals" :key="hospital.id" :label="hospital.name" :value="hospital.id">
              {{ hospital.name }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮件">
          <el-input v-model="updateDoctor.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showUpdateDoctorDialog = false">取消</el-button>
        <el-button @click="handleUpdateDoctor" type="primary">确认</el-button>
      </span>
    </el-dialog>

    <!-- Doctors Table -->
    <div>
      <el-table :data="pagedDoctors"
                max-height="65vh" style="width: 100%"
                ref="multipleTable"
                @selection-change="handleSelectionChange"
                @sort-change="sortChange"
                v-loading="loading">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="code" label="编号" sortable></el-table-column>
        <el-table-column prop="sex" label="性别" sortable>
          <template #default="scope">
            {{ mapSexToText(scope.row.sex) }}
          </template>
        </el-table-column>
        <el-table-column prop="realName" label="姓名" sortable></el-table-column>
        <el-table-column prop="deptNo" label="部门" sortable>
          <template #default="scope">
            {{ mapDeptNoToName(scope.row.deptNo) }}
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" sortable></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="prepareUpdateDoctor(scope.row)" size="mini">修改</el-button>
            <el-button @click="confirmAndDeleteDoctor(scope.row)" size="mini" type="danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.currentPage"
          :page-sizes="[10, 20, 50, 100, 500]"
          :page-size="pagination.pageSize"
          :total="doctors.length"
          class="pagination"
          layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>
  </div>
</template>
<script setup lang="ts">
import {ref, reactive, computed, onMounted} from "vue";
import { ElButton, ElDialog, ElForm, ElFormItem, ElInput, ElMessage } from 'element-plus';
import fileAxios from "../../api/fileAxios";
import { ElMessageBox } from 'element-plus';
import myAxios from "../../api/myAxios";
import SearchDoctor from "./search-doctor.vue";

const doctors = reactive([]);
const newDoctor = ref({ id: "", code: "", realName: "", deptNo: "", email: "", sex: "" });
const showAddDoctorDialog = ref(false);
const showUpdateDoctorDialog = ref(false);
const updateDoctor = reactive({id: "", code: "", realName: "", deptNo: "", email: "", sex: ""}); // 存储正在修改的医生信息
const selectedDoctors = ref([]); // 用于存储选中的医生信息
const loading = ref(false);
const search = ref<InstanceType<typeof SearchDoctor>> ();
const fileUpload = ref(null); // 用于引用文件输入元素
const hospitals = reactive([]);
// 获取本地存储的医院信息
const getLocalHospitals = () => {
  const localHospitals = localStorage.getItem('hospitals');
  if (localHospitals) {
    hospitals.push(...JSON.parse(localHospitals));
  } else {
    queryHospitals();
  }
};

// 查询医院信息
const queryHospitals = async () => {
  try {
    const response = await myAxios.get('/api/admin/queryHospitalVO');
    if (response.data.code === 200) {
      hospitals.push(...response.data.data);
      localStorage.setItem('hospitals', JSON.stringify(hospitals));
    } else {
      ElMessage.error(response.data.message || '获取医院信息失败');
    }
  } catch (error) {
    ElMessage.error('网络请求异常');
  }
};

// 映射部门ID到名称
const mapDeptNoToName = (deptNo) => {
  const hospital = hospitals.find(hospital => hospital.id === deptNo);
  return hospital ? hospital.name : deptNo;
};

// 映射性别代码到文本
const mapSexToText = (sex) => {
  return sex == '1' ? '男' : '女';
};

// 这个函数接受一个回调，在对话框中点击确认时调用
const open = (onConfirm) => {
  ElMessageBox.confirm(
      '确认执行删除吗?',
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  ).then(() => {
    // 如果用户点击确认，调用传入的回调函数
    onConfirm();
  })
      .catch(() => {
        // 如果用户点击取消，给出一个提示
        ElMessage({
          type: 'info',
          message: 'Delete canceled',
        });
      });
}

// 触发文件上传操作
const triggerUpload = () => {
  if (fileUpload.value) {
    fileUpload.value.click();
  }
};

// 处理文件选择的变化
const handleFileChange = async (event) => {
  const file = event.target.files[0];
  if (!file) {
    ElMessage.error("请选择文件");
    return;
  }
  try {
    const formData = new FormData();
    formData.append("file", file);
    loading.value = true
    const response = await fileAxios.post("/api/admin/addBatchDoctor", formData);
    loading.value = false
    if (response.data.code === 200) {
      ElMessage.success("批量添加成功");
      // queryDoctors(); // 刷新医生列表
      search.value.searchDoctor();
    } else {
      ElMessage.error(response.data.message || "添加失败");
    }
  } catch (error) {
    ElMessage.error("网络请求异常");
  } finally {
    if (fileUpload.value) {
      fileUpload.value.value = ''; // 清空文件选择器，以便下次相同文件也能触发change事件
    }
  }
};

// 准备更新医生信息
const prepareUpdateDoctor = (doctor) => {
  updateDoctor.realName = doctor.realName;
  updateDoctor.code = doctor.code;
  updateDoctor.deptNo = doctor.deptNo;
  updateDoctor.email = doctor.email;
  updateDoctor.sex = doctor.sex;
  updateDoctor.id = doctor.id; // 需要医生ID以便更新操作能够定位到正确医生
  showUpdateDoctorDialog.value = true;
};
// 处理修改医生信息
const handleUpdateDoctor = async () => {
  try {
    // console.log(updateDoctor)
    loading.value = true
    const response = await myAxios.post("/api/admin/updateDoctor", [updateDoctor]);
    loading.value = false
    if (response.data.code === 200) {
      ElMessage.success("医生信息修改成功");
      showUpdateDoctorDialog.value = false;
      // queryDoctors(); // 刷新医生列表
      search.value.searchDoctor();
    } else {
      ElMessage.error(response.data.message || "修改失败");
    }
  } catch (error) {
    ElMessage.error(error.response?.data?.message || "网络请求异常");
  }
};
const handleSelectionChange = (val) => {
  selectedDoctors.value = val;
};

const handleAddDoctor = async () => {
  try {
    loading.value = true
    const response = await myAxios.post("/api/admin/addDoctor", newDoctor.value);
    loading.value = false
    if (response.data.code === 200) {
      ElMessage.success("新增医生成功");
      showAddDoctorDialog.value = false;
      // queryDoctors(); // Refresh the doctors list
      search.value.searchDoctor();
    } else {
      ElMessage.error(response.data.message || "添加失败");
    }
  } catch (error) {
    ElMessage.error("网络请求异常");
  }
};

const requestDoctorDeletion = async (doctorInfo) => {
  try {
    loading.value = true
    const response = await myAxios.post("/api/admin/delDoctor", [doctorInfo]);
    loading.value = true
    if (response.data.code === 200) {
      ElMessage.success("医生删除成功");
      // queryDoctors(); // Refresh the doctors list
      search.value.searchDoctor();
    } else {
      ElMessage.error(response.data.message || "删除失败");
    }
  } catch (error) {
    ElMessage.error("网络请求异常");
  }
};

// 重置密码函数
const resetPasswords = async () => {
  // 首先，确认已选择至少一个医生
  if (selectedDoctors.value.length === 0) {
    ElMessage.warning("请选择至少一个医生进行密码重置操作");
    return;
  }
  const doctorInfoList = selectedDoctors.value.map(doctor => ({
    id: doctor.id,
    code: doctor.code,
    realName: doctor.realName,
    sex: doctor.sex,
    deptNo: doctor.deptNo,
    email: doctor.email
  }));
  try {
    loading.value = true
    const response = await myAxios.post("/api/admin/resetDoctorPassword", doctorInfoList);
    loading.value = false
    if (response.data.code === 200) {
      ElMessage.success("批量重置密码成功");
    } else {
      ElMessage.error(response.data.message || "批量重置密码失败");
    }
  } catch (error) {
    ElMessage.error(error.response?.data?.message || "网络请求异常");
  }
};


// Function to query doctors
const queryDoctors = async () => {
  try {
    loading.value = true
    const response = await myAxios.get("/api/admin/queryDoctorVO");
    loading.value = false
    if (response.data.code === 200) {
      doctors.splice(0);
      doctors.push(...response.data.data);
    } else {
      ElMessage.error(response.data.message || "查询失败");
    }
  } catch (error) {
    ElMessage.error("网络请求异常");
  }
};

const handleDoctorUpdate = (newDoctors) => {
  doctors.splice(0);
  doctors.push(...newDoctors);
};

const deleteDoctors = async () => {
  if (selectedDoctors.value.length === 0) {
    ElMessage.warning("请选择至少一个医生进行删除操作");
    return;
  }
  const doctorsToDelete = selectedDoctors.value.map(doctor => {
    return { id: doctor.id };
  });
  try {
    console.log(doctorsToDelete);
    loading.value = true
    const response = await myAxios.post("/api/admin/delDoctor", doctorsToDelete);
    loading.value = false
    if (response.data.code === 200) {
      ElMessage.success("批量删除医生成功");
      search.value.searchDoctor();
    } else {
      ElMessage.error(response.data.message || "批量删除失败");
    }
  } catch (error) {
    ElMessage.error("网络请求异常");
  }
};


// 这个函数用于提示用户确认，并在确认后执行批量删除操作
const confirmAndDeleteDoctors = () => {
  open(() => {
    // 这里放置你的删除医生逻辑
    deleteDoctors();
  });
};

const confirmAndDeleteDoctor = (doctorInfo) => {
  open(() => {
    // 这里放置你的删除医生逻辑
    requestDoctorDeletion(doctorInfo);
  });
};

// 分页参数
const pagination = reactive({
  currentPage: 1,
  pageSize: 10
});

// 排序参数
const sortParams = reactive({
  prop: '',
  order: ''
});

// 计算分页后的数据
const pagedDoctors = computed(() => {
  loading.value = true
  // 先对整个数据集进行排序
  let sortedDoctors = [...doctors];
  if (sortParams.prop) {
    sortedDoctors.sort((a, b) => {
      let valA = a[sortParams.prop];
      let valB = b[sortParams.prop];
      if (sortParams.order === 'asc') {
        loading.value = false
        return valA.localeCompare(valB, 'kn', { numeric: true });
      } else {
        loading.value = false
        return valB.localeCompare(valA, 'kn', { numeric: true });
      }
    });
  }

  // 然后执行分页逻辑
  const start = (pagination.currentPage - 1) * pagination.pageSize;
  const end = start + pagination.pageSize;
  loading.value = false
  return sortedDoctors.slice(start, end);
});

// 处理用户界面事件的函数
function handleCurrentChange(newPage) {
  pagination.currentPage = newPage;
}

function handleSizeChange(newSize) {
  pagination.pageSize = newSize;
}

function sortChange({ prop, order }) {
  sortParams.prop = prop;
  // ElementUI排序参数调整
  sortParams.order = order === 'ascending' ? 'asc' : 'desc';
}
onMounted(() => {
  getLocalHospitals();
  queryDoctors();

});
// Call to fetch initial data

</script>

<style scoped>
.pagination {
  position: absolute;
  bottom: 0; /* 调整与底部的距离 */
  left: 50%;
  transform: translateX(-50%); /* 通过transform实现真正的水平居中 */
  z-index: 1000; /* 确保在内容之上 */
  margin-top: auto;
}

.table-actions {
  margin-bottom: 10px; /* Adjust the spacing between the action buttons and the table */
}

</style>