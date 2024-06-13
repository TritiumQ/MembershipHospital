package me.qunqun.shared.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
	
	;
	private final int code;
	private final String message;
}
