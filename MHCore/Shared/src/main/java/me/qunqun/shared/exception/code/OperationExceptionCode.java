package me.qunqun.shared.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationExceptionCode implements IExceptionCode
{
	//TODO 通用错误代码
	ERROR(1919810, "错误请求"),
	
	;
	private final int code;
	private final String message;
}
