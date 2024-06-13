package me.qunqun.shared.exception;


import me.qunqun.shared.exception.code.OperationExceptionCode;

public class OperationException extends CustomException
{
	public OperationException(OperationExceptionCode code)
	{
		super(code);
	}
}
