package me.qunqun.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.qunqun.shared.exception.IExceptionCode;

@Getter
@AllArgsConstructor
public enum OperationExceptionCode implements IExceptionCode
{
	//TODO 通用错误代码
	ERROR(1919810, "错误请求"),
	
	//TODO 用户相关错误代码
	
	USER_NOT_FOUND(10001, "用户不存在"),
	USER_PASSWORD_ERROR(10002, "密码错误"),
	USER_ID_EXIST(10003, "用户已存在"),
	
	// TODO 订单相关错误
	ORDER_NOT_FOUND(20001, "订单不存在"),
	ORDER_STATE_ERROR(20002, "订单状态错误"),
	ORDER_HAS_BEEN_CANCELED(20003, "订单已取消"),
	ORDER_EXITED(20004, "订单已存在"),
	;
	private final int code;
	private final String message;
}
