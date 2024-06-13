package me.qunqun.shared.exception.handler;

import cn.dev33.satoken.exception.SaTokenException;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.shared.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
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
	 * 处理所有系统异常, 仅在产品部署后时使用
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



