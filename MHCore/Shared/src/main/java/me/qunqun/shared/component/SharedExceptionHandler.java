package me.qunqun.shared.component;

import cn.dev33.satoken.exception.SaTokenException;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.shared.exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 默认全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class SharedExceptionHandler
{
	@ExceptionHandler(SaTokenException.class)
	public Result handleSaTokenException(SaTokenException e)
	{
		int code = e.getCode();
		String msg = e.getMessage();
		log.warn("SaTokenException: {} : {}", code, msg);
		return Result.of(code, msg, null);
	}
	
	@ExceptionHandler(CustomException.class)
	public Result handleCustomException(CustomException e)
	{
		int code = e.getCode();
		String msg = e.getMessage();
		log.warn("CustomException: {} : {}", code, msg);
		return Result.of(code, msg, null);
	}
	
	
	/**
	 * 处理所有系统异常
	 */
//	@ExceptionHandler(Exception.class)
//	public SimpleResult<String> handleException(Exception e) {
//		// 检测系统环境
//		if(activeProfile.equals("prod"))
//		{
//			log.error("系统异常");
//			return SimpleResult.error("系统异常");
//		}
//		throw new RuntimeException(e);
//	}
}



