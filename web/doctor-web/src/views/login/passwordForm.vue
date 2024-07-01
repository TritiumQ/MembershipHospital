<template>
  <el-form ref="loginForm" :model="form" :rules="rules" label-width="0" size="large" @keyup.enter="handleLogin">
    <el-form-item prop="code">
      <el-input v-model="form.code" label="医生编号" clearable placeholder="医生编号" @keyup.enter="focusNext">
        <template #prepend>
          <el-button icon="User"></el-button>
        </template>
        <template #append>
          <el-select v-model="userType" style="width: 130px;">
            <el-option label="医生" value="医生"></el-option>
            <el-option label="管理员" value="管理员"></el-option>
          </el-select>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input ref="passwordInput" @keyup.enter="handleLogin" v-model="form.password" clearable show-password placeholder="密码">
        <template #prepend>
          <el-button icon="Lock"></el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item style="margin-bottom: 10px;">
      <el-col :span="12">
        <el-checkbox label="记住密码" v-model="autologin"></el-checkbox>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" style="width: 100%;" round @click="handleLogin">登录</el-button>
    </el-form-item>
    <el-divider style="margin-top: 70px;">
      <div class="logo">
        <img :alt="'加载失败'" src="../../assets/img/logo.png">
        <label>版本V1.0</label>
      </div>
    </el-divider>
  </el-form>
</template>

<script lang="ts" setup>
import {ref, reactive, watch, onMounted} from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { ElMessage, FormInstance, FormRules } from 'element-plus';
import { usePermissStore } from '../../store/permiss';

const permiss = usePermissStore();
const router = useRouter();

interface LoginInfo {
  code: string;
  password: string;
  type: number;
}

const lgStr = localStorage.getItem('remember_form');
const defParam = lgStr ? JSON.parse(lgStr) : null;
const autologin = ref<boolean>(lgStr ? true : false);
const userType = ref(defParam ? defParam.type === 0 ? '医生' : defParam.type === 1 ? '管理员' : '医生' : '管理员');


const form = reactive<LoginInfo>({
  code: defParam ? defParam.code : "",
  password: defParam ? defParam.password : "",
  type: defParam ? defParam.type : 0,
});

watch(userType, (newVal) => {
  form.type = newVal === '医生' ? 0 : newVal === '管理员' ? 1 : 0;
});

const rules: FormRules = {
  code: [
    { required: true, message: "请输入用户编号", trigger: 'blur' }
  ],
  password: [
    { required: true, message: "请输入用户密码", trigger: 'blur' }
  ]
};

const loginForm = ref<FormInstance>();

const passwordInput = ref<HTMLInputElement | null>(null);

const focusNext = () => {
  passwordInput.value?.focus();
};

const encryptPassword = (password: string): string => {
  return btoa(password);
};

const handleLogin = () => {
  // form.password = encryptPassword(form.password);
  login(loginForm.value);
};

const login = (formEl: FormInstance | undefined) => {
  if (!formEl) { return }
  formEl.validate((valid: boolean) => {
    if (valid) {
      // 创建请求体
      const loginData = {
        code: form.code,
        password: form.password,
        type: form.type
      };

      axios.post('/api/api/signin', loginData).then(res => {
        if (res.data.code === 200) {
          const { data } = res.data;
          localStorage.setItem('token', data.token);
          const doctorInfo = {
            id: data.id,
            code: data.code,
            realName: data.realName,
            sex: data.sex,
            deptNo: data.deptNo,
            email: data.email
          };
          sessionStorage.setItem('doctorInfo', JSON.stringify(doctorInfo));
          localStorage.setItem('email', data.email);
          localStorage.setItem('permiss', JSON.stringify(permiss.defaultList[form.type]));
          ElMessage.success('登录成功');
          router.push('/');
          localStorage.setItem('code', data.code);
          localStorage.setItem('username', data.realName);
          localStorage.setItem('deptNo', data.deptNo);
          if (autologin.value) {
            localStorage.setItem('remember_form', JSON.stringify(form));
          } else {
            localStorage.removeItem('remember_form');
          }
          if (loginData.type === 0) {
            localStorage.setItem('role', "医生");
          }else {
            localStorage.setItem('role', "管理员");
          }
        } else {
          ElMessage.error(res.data.message);
        }
      }).catch(err => {
        ElMessage.error('登录失败，请稍后重试');
      });
    }
  });
};

onMounted(() => {
  userType.value = defParam ? defParam.type === 0 ? '医生' : defParam.type === 1 ? '管理员' : '医生' : '医生';
});
</script>


<style>
.logo img {
	width: 15px;
	height: 15px;
}

.logo label {
	margin-left: 5px;

	font-size: 12px;
}
</style>
