<template>
	<!-- 头部元素 -->
	<v-header />
	<!-- 侧边栏 -->
	<v-sidebar />
	<!-- 标签页 根据侧边栏的显示来决定距离左边多远-->
	<div class="content-box" :class="{ 'content-collapse': sidebar.collapse }">
		<!-- 标签选项 -->
		<v-tags></v-tags>
		<!-- 内容区域 -->
		<div class="content">
			<router-view v-slot="{ Component }">
				<transition name="move" mode="out-in">
					<keep-alive :include="tags.nameList">
						<component :is="Component"></component>
					</keep-alive>
				</transition>
			</router-view>
		</div>
	</div>
</template>
<script setup lang="ts">
import { useSidebarStore } from '../store/sidebar';
import { useTagsStore } from '../store/tags';
import vHeader from '../components/header.vue';
import vSidebar from '../components/sidebar.vue';
import vTags from '../components/tags.vue';
const sidebar = useSidebarStore();
const tags = useTagsStore();

</script>
