<template>
  <el-form ref="loginForm" :model="form" :rules="rules" label-width="0" size="large" @keyup.enter="handleLogin">
    <el-form-item prop="code">
      <el-input v-model="form.code" label="医生编号" clearable placeholder="医生编号" @keyup.enter="focusNext">
        <template #prepend>
          <el-button icon="User"></el-button>
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
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { ElMessage, FormInstance, FormRules } from 'element-plus';
import { usePermissStore } from '../../store/permiss';

const permiss = usePermissStore();
const router = useRouter();

interface LoginInfo {
  code: string;
  password: string;
}

const lgStr = localStorage.getItem('remember_form');
const defParam = lgStr ? JSON.parse(lgStr) : null;
const autologin = ref<boolean>(lgStr ? true : false);

const form = reactive<LoginInfo>({
  code: defParam ? defParam.code : "",
  password: defParam ? defParam.password : "",
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
          localStorage.setItem('permiss', JSON.stringify(permiss.defaultList[0]));
          ElMessage.success('登录成功');
          router.push('/');
          localStorage.setItem('username', data.realName);
          localStorage.setItem('deptNo', data.deptNo);
          if (autologin.value) {
            localStorage.setItem('remember_form', JSON.stringify(form));
          } else {
            localStorage.removeItem('remember_form');
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
