import router from '../router';
import { ElMessage } from 'element-plus';
import axios from 'axios';

const myAxios = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  },
});

myAxios.interceptors.request.use(
  function(config) {
    const tok = localStorage.getItem('token');
    config.headers.Authorization = `${"Bearer "+tok}`;
    return config;
  },
  function(error) {
    return Promise.reject(error);
  }
);

myAxios.interceptors.response.use(
  function(response) {
    return response;
  },
  function(error) {
    if (error.response.code === 401) {
      router.push('/login');
      ElMessage.error('登录过期，请重新登录');
    }
    return Promise.reject(error);
  }
);

export default myAxios;