<template>
	<div class="login_bg">
		<div class="login_adv">
			<div class="login_adv__title">
				<h2>体 检 系 统 医 生 端</h2>
				<hr width="80%">
				<h3>Membership hospital system</h3>
				<p style="margin-top: 98%;"></p>
				<div>
					<span>
						<el-icon>
							<Tools />
						</el-icon>
					</span>
					<span>
						<el-icon class="add"><el-icon-plus /></el-icon>
					</span>
					<span>
						<el-icon>
							<Platform />
						</el-icon>
					</span>
					<span>
						<el-icon class="add"><el-icon-plus /></el-icon>
					</span>
					<span>
						<el-icon>
							<User />
						</el-icon>
					</span>
					<span>
						<el-icon class="add"><el-icon-plus /></el-icon>
					</span>
					<span>
						<el-icon>
							<Edit />
						</el-icon>
					</span>
				</div>
			</div>
			<div class="login_adv__mask"></div>
			<div class="login_adv__bottom">
				给予每一位用户会员般的服务，为您提供最优质的体检服务
			</div>
		</div>
		<div class="login_main">
			<div class="login-form">
				<div class="login-header">
					<div class="logo">
						<img :alt="ddd" src="../../assets/img/logo.png">
						<label>体检系统-医生端</label>
					</div>
				</div>
				<el-tabs stretch="true">
					<el-tab-pane label="账号登录">
						<password-form></password-form>
					</el-tab-pane>
<!--					<el-tab-pane label="游客访问">-->
<!--						<swim-visit></swim-visit>-->
<!--					</el-tab-pane>-->
				</el-tabs>


			</div>
		</div>
	</div>

</template>

<script lang="ts" setup>
import passwordForm from './passwordForm.vue'
</script>
<style scoped>
.login_bg {
	width: 100%;
	height: 100%;
	background: #fff;
	display: flex;
}

.login_adv {
	width: 33.33333%;
	background-image: url('../../assets/img/auth_banner.jpg');
	background-color: #555;
	background-size: cover;
	background-position: center center;
	background-repeat: no-repeat;
	position: relative;

}

.login_adv__title {
	color: #fff;
	padding: 40px;
	position: absolute;
	top: 0px;
	left: 0px;
	right: 0px;
	z-index: 2;
}

.login_adv__title h2 {
	font-size: 40px;
}

.login_adv__title h4 {
	font-size: 18px;
	margin-top: 10px;
	font-weight: normal;
}

.login_adv__title p {
	font-size: 14px;
	margin-top: 10px;
	line-height: 1.8;
	color: rgba(255, 255, 255, 0.6);
}

.login_adv__title div {
	margin-top: 10px;
	display: flex;
	align-items: center;
}

.login_adv__title div span {
	margin-right: 15px;
}

.login_adv__title div i {
	font-size: 40px;
}

.login_adv__title div i.add {
	font-size: 20px;
	color: rgba(255, 255, 255, 0.6);
}

.login_adv__bottom {
	position: absolute;
	left: 0px;
	right: 0px;
	bottom: 40px;
	color: #fff;
	padding: 40px;
	background-image: linear-gradient(transparent, #000);
	z-index: 3;
}

.login_adv__mask {
	position: absolute;
	top: 0px;
	left: 0px;
	right: 0px;
	bottom: 0px;
	background: rgba(0, 0, 0, 0.5);
	z-index: 1;
}

.login_main {
	flex: 1;
	overflow: auto;
	display: flex;
}

.login-form {
	width: 400px;
	margin: auto;
	padding: 20px 0;
}

.login-header {
	margin-bottom: 40px;
}

.login-header .logo {
	display: flex;
	align-items: center;
}

.login-header .logo img {
	width: 40px;
	height: 40px;
	vertical-align: bottom;
	margin-right: 10px;
}

.login-header .logo label {
	font-size: 26px;
	font-weight: bold;
}

.login-oauth {
	display: flex;
	justify-content: space-around;
}

.login-form .el-divider {
	margin-top: 40px;
}

.login-form {}

.login-form:deep(.el-tabs) .el-tabs__header {
	margin-bottom: 25px;
}

.login-form:deep(.el-tabs) .el-tabs__header .el-tabs__item {
	font-size: 14px;
}

.login-form:deep(.login-forgot) {
	text-align: right;
}

.login-form:deep(.login-forgot) a {
	color: var(--el-color-primary);
}

.login-form:deep(.login-forgot) a:hover {
	color: var(--el-color-primary-light-3);
}

.login-form:deep(.login-reg) {
	font-size: 14px;
	color: var(--el-text-color-primary);
}

.login-form:deep(.login-reg) a {
	color: var(--el-color-primary);
}

.login-form:deep(.login-reg) a:hover {
	color: var(--el-color-primary-light-3);
}

.login_config {
	position: absolute;
	top: 20px;
	right: 20px;
}

.login-form:deep(.login-msg-yzm) {
	display: flex;
	width: 100%;
}

.login-form:deep(.login-msg-yzm) .el-button {
	margin-left: 10px;
	--el-button-size: 42px;
}

.qrCodeLogin {
	text-align: center;
	position: relative;
	padding: 20px 0;
}

.qrCodeLogin img.qrCode {
	background: #fff;
	padding: 20px;
	border-radius: 10px;
}

.qrCodeLogin p.msg {
	margin-top: 15px;
}

.qrCodeLogin .qrCodeLogin-result {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	text-align: center;
	background: var(--el-mask-color);
}

@media (max-width: 1200px) {
	.login-form {
		width: 340px;
	}
}

@media (max-width: 1000px) {
	.login_main {
		display: block;
	}

	.login_main .login_config {
		position: static;
		padding: 20px 20px 0 20px;
		text-align: right;
	}

	.login-form {
		width: 100%;
		padding: 20px 40px;
	}

	.login_adv {
		display: none;
	}
}
</style>
