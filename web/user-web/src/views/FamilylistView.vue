<script setup lang="ts">
import type { Hospital } from '@/model/hospital';
import { apiService } from '@/util/request';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import type { Family, FamilyCreate } from '@/model/family';
const router = useRouter();
const userStore = useUserStore();

const userInfo = ref(userStore.user);
const familyList = ref<Family[]>([]);

const getList = async () => {
    await apiService.get<Family[]>(`/family/list/${userInfo.value?.id}`).then((res) => {
        if (res.isSuccess()) {
             familyList.value = res.data;
        }
        else {
            console.error(res.message);
        }
    }).catch((err) => {
        console.error(err);
    });
};

const toNext = (f: Family) => {
    userStore.family = f;
    router.push('/hospital')
}

const markIdCard = (idCard: string) => {
    //只显示前六位和后四位
    return idCard.substring(0, 6) + '*****' + idCard.substring(14);
}

const newFamily = ref<FamilyCreate>({
    name: '',
    phone: '',
    birthday: '',
    idCard: '',
    userId: userInfo.value?.id!,
    sex: 1
});

const addFamilyDrawer = ref(false);


const addFamily = () => {
    addFamilyDrawer.value = true;
}

const cancelAddFamily = () => {
    addFamilyDrawer.value = false;
    newFamily.value = {
        name: '',
        phone: '',
        birthday: '',
        idCard: '',
        userId: userInfo.value?.id!,
        sex: 1
    };
}
const confirmAddFamily = async () => {
    await apiService.post<Family>('/family/add', newFamily.value).then((res) => {
        if (res.isSuccess()) {
            familyList.value.push(res.data);
            addFamilyDrawer.value = false;
        }
        else {
            console.error(res.message);
        }
    }).catch((err) => {
        console.error(err);
    }).finally(() => {
        newFamily.value = {
            name: '',
            phone: '',
            birthday: '',
            idCard: '',
            userId: userInfo.value?.id!,
            sex: 1
        };
    });
}

onMounted(() => {
    getList();
});
</script>
<template>
    <!-- 总容器 -->
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" @click="() => router.back()"></i>
            <p>家属列表</p>
            <div></div>
        </header>
        <div class="top-ban"></div>
        <p class="add" @click="addFamily">
            <i class="fa fa-plus"></i>
            添加家属
        </p>
        <p class="errmsg" v-if="familyList.length === 0">暂无家属信息</p>
        <ul class="hospital" v-for="item, idx in familyList">
            <li>
                <h3 @click="() => toNext(item)">
                    帮他预约
                    <i class="fa fa-angle-right"></i>
                </h3>
                <div class="hospita-info">
                    <table>
                        <tr>
                            <td>姓名</td>
                            <td>{{ item.name }}</td>
                        </tr>
                        <tr>
                            <td>姓别</td>
                            <td>{{ item.sex === 1 ? '男':'女' }}</td>
                        </tr>
                        <tr>
                            <td>电话</td>
                            <td>{{ item.phone }}</td>
                        </tr>
                        <tr>
                            <td>生日</td>
                            <td>{{ item.birthday }}</td>
                        </tr>
                        <tr>
                            <td>身份证</td>
                            <td>{{ markIdCard(item.idCard) }}</td>
                        </tr>
                    </table>
                </div>
            </li>
        </ul>
        <a-drawer :open="addFamilyDrawer" placement="bottom" :closable="false">
            <div class="add-family">
                <h3>添加家属</h3>
                <div class="form">
                    <input type="text" v-model="newFamily.name" placeholder="姓名" />
                    <input type="text" v-model="newFamily.phone" placeholder="电话" />
                    <input type="date" v-model="newFamily.birthday" placeholder="生日" />
                    <input type="text" v-model="newFamily.idCard" placeholder="身份证" />
                    <div class="inner-radio">
                        <div class="radio-item">
                            <input type="radio" v-model="newFamily.sex" value="1" />
                            <p>男</p>
                        </div>
                        <div class="radio-item">
                            <input type="radio" v-model="newFamily.sex" value="0" />
                            <p>女</p>
                        </div>
                    </div>
                </div>
                <div class="btn">
                    <button class="cancel" @click="cancelAddFamily">取消</button>
                    <button class="confirm" @click="confirmAddFamily">确定</button>
                </div>
            </div>
        </a-drawer>
    </div>
</template>
<style scoped>
@import '../assets/css/hospital.css';
.add-family {
    padding: 10px;
    h3 {
        text-align: center;
        margin-bottom: 10px;
    }
    .form {
        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #f0f0f0;
        }
    }
    .btn {
        text-align: center;
        button {
            padding: 10px 20px;
            margin: 0 10px;
            border: 1px solid #f0f0f0;
            background-color: #fff;   
        }
        .confirm {
            color: #fff;
            background-color: #1890ff;
        }
        .cancel {
            color: #333;
        }
    }
    .inner-radio {
        display: flex;
        .radio-item {
            display: flex;
            align-items: center;
            input {
                margin-right: 10px;
            }
            margin-right: 20px;
        }
    }
}
.add {
    text-align: center;
    padding: 10px 0;
    background-color: #fff;
    border-bottom: 1px solid #f0f0f0;
    color: #333;
    i {
        font-size: 20px;
        margin-right: 10px;
    }
}
.errmsg {
    text-align: center;
    padding: 10px 0;
    background-color: #fff;
    border-bottom: 1px solid #f0f0f0;
    color: #333;
}
</style>