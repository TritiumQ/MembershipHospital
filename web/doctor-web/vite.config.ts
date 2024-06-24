import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import VueSetupExtend from 'vite-plugin-vue-setup-extend';
import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers';
export default defineConfig({
	plugins: [
		vue(),
		VueSetupExtend(),
		AutoImport({
			resolvers: [ElementPlusResolver()]
		}),
		Components({
			resolvers: [ElementPlusResolver()]
		})
	],
	optimizeDeps: {
		include: ['schart.js']
	},
	server: {
		host: '127.0.0.1',
		proxy: {
			'/api': {
				// target: 'https://47.121.27.74:8081',
				target: 'http://localhost:9091',
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api/, '/')
			},
			// '/admin': {
			// 	target: 'http://localhost:8081',
			// 	changeOrigin: true,
			// 	rewrite: (path) => path.replace(/^\/admin/, '/user')
			// }
		}
	}
});
