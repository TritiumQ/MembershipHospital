package me.qunqun.user.exception;


import me.qunqun.shared.exception.CustomException;

public class OperationException extends CustomException
{
	public OperationException(OperationExceptionCode code)
	{
		super(code);
	}
}
