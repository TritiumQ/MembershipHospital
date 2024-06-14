package me.qunqun.shared.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final int code;
	
	public CustomException(IExceptionCode code)
	{
		super(code.getMessage());
		this.code = code.getCode();
	}
	
	public CustomException(int code, String message)
	{
		super(message);
		this.code = code;
	}
}
