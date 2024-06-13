package me.qunqun.shared.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置
 */
@Configuration
public class WebSharedConfig implements WebMvcConfigurer
{
	// Sa-Token 配置类
	// https://sa-token.cc/doc.html#/use/route-check  参考文档
	
	// 注册拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册 Sa-Token 拦截器，定义详细认证规则
		registry.addInterceptor(new SaInterceptor(handle -> {
					// API请求日志输出
					SaRouter
							.notMatch("/api/docs/**")
							.match("/**")
							.check(r -> {
								if(StpUtil.getTokenValue() != null)
								{
									SaManager.getLog().debug(
											"API请求: {}, id={}, token={}",
											SaHolder.getRequest().getRequestPath(),
											StpUtil.getLoginIdAsInt(),
											StpUtil.getTokenValue().substring(0, 10)
									);
								}
								else
								{
									SaManager.getLog().debug("API 请求: {}, 无token", SaHolder.getRequest().getRequestPath());
								}
							});
					//鉴权规则
					SaRouter
							// 排除掉的 path 列表，可以写多个
							.notMatch("/api/signin")
							.notMatch("/api/test/**")
							.notMatch("/api/docs/**")
							// 拦截的 path 列表，可以写多个 */
							.match("/**")
							//校验登录
							.check(r -> StpUtil.checkLogin());
				})).addPathPatterns("/**");
		
		// 其他拦截器
		
	}
	
	
	// Sa-Token 整合 jwt , 设置为 Stateless 无状态模式
	@Bean
	public StpLogic getStpLogicJwt() {
		return new StpLogicJwtForStateless();
	}
	
	/**
	 * Sa-Token 全局过滤器，处理跨域问题
 	 */
	@Bean
	public SaServletFilter getSaServletFilter() {
		return new SaServletFilter()
				// 指定 [拦截路由] 与 [放行路由]
				.addInclude("/api/**")
				.addExclude("/api/test/**")
				.addExclude("/api/docs/**")
				.addExclude("/api/signin")
				
				// 认证函数: 每次请求执行
//				.setAuth(obj -> {
//					SaManager.getLog().debug("----- 请求path={}  提交token={}", SaHolder.getRequest().getRequestPath(), StpUtil.getTokenValue());
//					// ...
//				})
				
				// 异常处理函数：每次认证函数发生异常时执行此函数
//				.setError(e -> {
//					return SaResult.error(e.getMessage());
//				})
				
				// 前置函数：在每次认证函数之前执行
				.setBeforeAuth(obj -> {
					SaHolder.getResponse()
							// ---------- 设置跨域响应头 ----------
							// 允许指定域访问跨域资源
							.setHeader("Access-Control-Allow-Origin", "*")
							// 允许所有请求方式
							.setHeader("Access-Control-Allow-Methods", "*")
							// 允许的header参数
							.setHeader("Access-Control-Allow-Headers", "*")
							// 有效时间
							.setHeader("Access-Control-Max-Age", "3600")
					;
					
					// 如果是预检请求，则立即返回到前端
					SaRouter.match(SaHttpMethod.OPTIONS)
							.free(r -> SaManager.getLog().info("OPTIONS预检请求，不做处理"))
							.back();
				})
				;
	}
}
