<template>
	<div style="margin-top: 30px;">
		<el-row :gutter="20">
			<el-col :span="8">
				<el-card  shadow="hover" class="mgb20" style="height: 252px;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);">
					<div class="user-info">
						<el-avatar :size="120" :src="imgurl" />
						<div class="user-info-cont">
							<div class="user-info-name">{{ name }}</div>
							<el-tag
								:type="'warning'"
								effect="" round>{{ "医生" }}</el-tag>
						</div>
					</div>
<!--					<div class="user-info-list">-->
<!--						当前学期：-->
<!--						<span>{{ currentTerm }}</span>-->
<!--					</div>-->
<!--					<div class="user-info-list">-->
<!--						学期周数：-->
<!--						<span>{{ weekCount }}</span>-->
<!--					</div>-->
				</el-card>
				<el-card shadow="hover" class="plus-shadow" style="height: 252px">
					<template #header>
						<el-checkbox label="修改密码" v-model="formEnable"></el-checkbox>
						<el-button style="margin-left: 63.5%;" type="primary" plain @click="sumbit()" :disabled="!formEnable">确认</el-button>
					</template>
					<el-form :model="changeForm"
						style="display: flex;flex-direction:column; align-items: center;width: 100%;">
						<el-form-item label="旧密码：">
							<el-input  @keyup.enter="foucusNext(1)" type="password" class="input-type" v-model="changeForm.oldPassword"
								placeholder="请输入旧密码" :disabled="!formEnable"></el-input>
						</el-form-item>
						<el-form-item label="新密码：">
							<el-input ref="newInput" @keyup.enter="foucusNext(2)" type="password" class="input-type" v-model="changeForm.newPassword"
								placeholder="请输入新密码" :disabled="!formEnable"></el-input>
						</el-form-item>
						<el-form-item label="新密码：">
							<el-input  ref="newAgainInput" @keyup.enter="sumbit()" type="password" class="input-type" v-model="changeForm.confirmPassword"
								placeholder="请再次输入新密码" :disabled="!formEnable"></el-input>
						</el-form-item>
					</el-form>

				</el-card>
			</el-col>
			<el-col :span="16">
				<el-row :gutter="20" class="mgb20">
					<el-col :span="8">
						<el-card shadow="hover" class="plus-shadow" :body-style="{ padding: '0px' }">
							<div class="grid-content grid-con-1">
								<el-icon class="grid-con-icon">
									<User />
								</el-icon>
								<div class="grid-cont-right">
									<div class="grid-num">Hospital</div>
									<div>医生端</div>
								</div>
							</div>
						</el-card>
					</el-col>

					<el-col :span="8">
						<el-card shadow="hover" class="plus-shadow"  :body-style="{ padding: '0px' }">
							<div class="grid-content grid-con-2">
								<el-icon class="grid-con-icon">
									<Lightning />
								</el-icon>
								<div class="grid-cont-right">
									<div class="grid-num">体检中心</div>
									<div></div>
								</div>
							</div>
						</el-card>
					</el-col>
					<el-col :span="8">
						<el-card shadow="hover" class="plus-shadow"  :body-style="{ padding: '0px' }">
							<div class="grid-content grid-con-3">
								<el-icon class="grid-con-icon">
									<Calendar />
								</el-icon>
								<div class="grid-cont-right">
									<div class="grid-num">Date</div>
									<div>{{ currentDate }}</div>
								</div>
							</div>
						</el-card>
					</el-col>
				</el-row>
				<el-card shadow="hover" class="plus-shadow" style="height: 403px">
					<template #header>
						<div class="clearfix">
							<h3>功能清单</h3>
						</div>
					</template>

					<el-table :show-header="false" :data="functionList" style="width: 100%" stripe>
						<el-table-column  width="170px" >
							<template v-slot:default="{ row }">
								<el-icon :color="row.color">
                                <component :is="row.icon" ></component>
                            </el-icon>
								<span class="span-style">{{ row.name}}</span>
							</template>
						</el-table-column>
						<el-table-column prop="desc">
						</el-table-column>
					</el-table>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script setup lang="ts" name="dashboard">
import Schart from 'vue-schart';
import { computed, onActivated, onMounted, reactive, ref } from 'vue';
import imgurl from '../assets/img/img.jpg';
import axios from 'axios';
import { ElMessage, FormInstance, FormRules } from 'element-plus';
import newAxios from '../api/newAxios';
import { Calendar } from "@element-plus/icons-vue";
interface change {
	oldPassword: string;
	newPassword: string;
	confirmPassword: string;
}
const changeForm = reactive<change>({
	oldPassword: '',
	newPassword: '',
	confirmPassword: ''
})

const formEnable = ref(false);
const name = localStorage.getItem('username');
const role: string = localStorage.getItem("role");
const currentTerm = ref('')
const weekCount = ref('')
const currentDate = ref('');

const adminFunctionList=[
  {
    name: '学期管理',
    icon: 'Calendar',
    desc: '查看系统学期信息，增加/删除学期。',
    color: 'blue'
  },
  {
    name: '平台用户管理',
    icon: 'Avatar',
    desc: '查看系统用户(实验员、教师、学生)信息，增加/删除/修改用户信息，重置密码。',
    color: 'red'
  },
  {
    name: '排课管理',
    icon: 'Memo',
    desc: '查看排课申请，进行排课安排。',
    color: 'purple'
  },
  {
    name: '借用审批',
    icon: 'Checked',
    desc: '查看实验室借用申请，进行审批。',
    color: 'dark'
  },
  {
		name: '查询排课课表',
		icon: 'Search',
		desc: '根据选择日期查看实验室一天中的所有排课安排。',
		color: 'green'

	},
]

const doctorFunctionList = [
  {
    name: '体检单管理',
    icon: 'Search',
    desc: '查看并填写体检报告。',
    color: 'blue'
  },
  {
    name: '数据统计',
    icon: 'List',
    desc: '查看历史数据统计。',
    color: 'green'
  }
]

// const functionList = role==='管理员'?adminFunctionList:role==='学生'?studentFunctionList:role==='老师'?teacherFunctionList:labtorFunctionList;
const functionList = doctorFunctionList;

const sumbit = () => {
	if (!changeForm || !changeForm.confirmPassword || !changeForm.newPassword || !changeForm.oldPassword) { ElMessage.error('请填写完整信息'); return; }
	if (changeForm.newPassword !== changeForm.confirmPassword) { ElMessage.error('两次密码不一致'); return; }
	newAxios.post('/login/changePassword', {
		oldPassword: changeForm.oldPassword,
		newPassword: changeForm.newPassword
	}).then(res => {
		if (res.data.code == 200) {
			ElMessage.success(res.data.data)
			clearForm();
			formEnable.value = false;
		}
		else {
			ElMessage.error(res.data.message)
		}

	})

}
const clearForm = () => {
	changeForm.oldPassword = '';
	changeForm.newPassword = '';
	changeForm.confirmPassword = '';
}
const getTerm = () => {
	axios.get('/api/login/queryTerm').then(res => {
		if (res.data.code === 200) {
			currentTerm.value = res.data.data.termName;
			weekCount.value = res.data.data.weekCount;
		}
	})
}

const formatDate = (date: Date): string => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // Month is 0-indexed
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

onMounted(() => {
  const now = new Date();
  currentDate.value = formatDate(now);
});

const formattedDate = currentDate;
onMounted(() => {
	getTerm();
});
onActivated(() => {
	getTerm();
});
const newInput = ref();
const newAgainInput = ref();
const foucusNext = (index: number) => {
	if (index === 1) {
		newInput.value.focus();
	}
	else {
		newAgainInput.value.focus();
	}
}
</script>

<style scoped>
.input-type {
	width: 300px;

}
.plus-shadow {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
}
.span-style {
	font-weight: bold;
	margin-left: 20px;
}
.el-row {
	margin-bottom: 20px;
}

.grid-content {
	display: flex;
	align-items: center;
	height: 100px;
}

.grid-cont-right {
	flex: 1;
	text-align: center;
	font-size: 14px;
	color: #999;
}

.grid-num {
	font-size: 30px;
	font-weight: bold;
}

.grid-con-icon {
	font-size: 50px;
	width: 100px;
	height: 100px;
	text-align: center;
	line-height: 100px;
	color: #fff;
}


.grid-con-1 .grid-con-icon {
	background: rgb(98, 239, 124);
	
}

.grid-con-1 .grid-num {
	color: rgb(98, 239, 124);
}

.grid-con-2 .grid-con-icon {
	background: rgb(8, 7, 57);
}

.grid-con-2 .grid-num {
	color: rgb(8, 7, 57);
}

.grid-con-3 .grid-con-icon {
	background: rgb(101, 99, 98);
}

.grid-con-3 .grid-num {
	color: rgb(101, 99, 98);
}

.user-info {
	display: flex;
	align-items: center;
	padding-bottom: 20px;
	border-bottom: 2px solid #ccc;
	margin-bottom: 20px;
}

.user-info-cont {
	text-align: center;
	flex: 0.6;
	font-size: 14px;
	letter-spacing: 1px;
	color: #999;
}

.user-info-cont div {
	font-size: 30px;
	color: #222;
	margin-bottom: 3px;
}

.user-info-cont span {
	font-size: 15px;
	color: #3f4bf4;
}

.user-info-list {
	font-size: 14px;
	color: #5e5a5a;
	line-height: 25px;
	font-weight: bold;
}


.mgb20 {
	margin-bottom: 20px;
}

.todo-item {
	font-size: 14px;
}

.todo-item-del {
	text-decoration: line-through;
	color: #999;
}

.schart {
	width: 100%;
	height: 300px;
}
</style>
