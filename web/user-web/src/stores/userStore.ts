import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import type { UserInfoWithToken } from '@/model/user'

/**
 * 使用 pinia 保存用户登录状态
 */
export const useUserStore = defineStore(
    'userStore',
    () => {
        const user = ref<UserInfoWithToken | null>(null)
        const token = computed(() => user.value?.token)
        const isLogin = computed(() => !!user.value);

        function login(userInfo: UserInfoWithToken) {
            console.log("User Login: " + JSON.stringify(userInfo))
            localStorage.setItem("userToken", userInfo.token)
            user.value = userInfo
            user.value.test = 1;
        }
        function logout() {
            console.log("User Logout")
            localStorage.removeItem("userToken")
            user.value = null
        }
        return { user, token, isLogin, login, logout }
    },
    {
        persist: true,
    }
)
