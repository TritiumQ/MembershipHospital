package me.qunqun.shared.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用返回结果
 * @param <T> 返回数据类型
 */
@Data
@AllArgsConstructor
public class Result<T>
{
	public static final int DEFAULT_SUCCESS_CODE = 114514;
	public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";
	public static final int DEFAULT_FAIL_CODE = 1919810;
	public static final String DEFAULT_FAIL_MESSAGE = "请求失败";
	
	private int code;
	private String message;
	private T data;
	
	public static Result success()
	{
		return new Result(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, null);
	}
	
	public static <T> Result<T> success(T data)
	{
		return new Result(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, data);
	}
	
	public static Result fail()
	{
		return new Result(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MESSAGE, null);
	}
	
	public static Result fail(String message)
	{
		return new Result(DEFAULT_FAIL_CODE, message, null);
	}
	
	public static Result fail(int code, String message)
	{
		return new Result(code, message, null);
	}
	
	public static <T> Result<T> of(int code, String message, T data)
	{
		return new Result<>(code, message, data);
	}
}
