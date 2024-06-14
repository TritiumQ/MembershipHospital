package me.qunqun.shared.component;

import cn.dev33.satoken.exception.SaTokenException;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.shared.exception.CustomException;
import org.springframework.http.ResponseEntity;
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
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		var res = ResponseEntity.internalServerError()
				.body("系统异常, 具体情况请咨询客服德川");
		log.error("预期外的Exception: ", e);
		return res;
	}
}



