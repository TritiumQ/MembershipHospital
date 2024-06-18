import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

/**
 * 使用 pinia
 */
export const useGlobalStore = defineStore(
    'userStore',
    () => {
        const hospitalList = ref<any[]>([]);
        const hasHospitalList = computed(() => hospitalList.value.length > 0);

        return {
            hospitalList,
            hasHospitalList,
        };
    },
    {
        persist: true,
    }
)
