package me.qunqun.shared.exception;

import lombok.Getter;
import me.qunqun.shared.exception.code.IExceptionCode;

@Getter
public class CustomException extends RuntimeException {
	private final int code;
	
	public CustomException(IExceptionCode code)
	{
		super(code.getMessage());
		this.code = code.getCode();
	}
}
