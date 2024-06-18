import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import type { UserInfoWithToken } from '@/model/user'
import type { Hospital } from '@/model/hospital'
import type { Package } from '@/model/package'
/**
 * 使用 pinia 保存用户登录状态以及其他信息
 */
export const useUserStore = defineStore(
    'userStore',
    () => {
        // 用户信息
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

        //预约信息
        const hospital = ref<Hospital | null>(null);
        const packageInfo = ref<Package | null>(null);
        const appointmentDate = ref<string | null>(null);
        const hasHospital = computed(() => !!hospital.value);
        const hasPackage = computed(() => !!packageInfo.value);
        const hasAppointmentDate = computed(() => !!appointmentDate.value);
        const appointmentCompleted = computed(() => isLogin.value && hasHospital.value && hasPackage.value && hasAppointmentDate.value);


        return {
            user,
            token,
            isLogin,
            login,
            logout,
            hospital,
            packageInfo,
            appointmentDate,
            hasHospital,
            hasPackage,
            hasAppointmentDate,
            appointmentCompleted,
        }
    },
    {
        persist: true,
    }
)
