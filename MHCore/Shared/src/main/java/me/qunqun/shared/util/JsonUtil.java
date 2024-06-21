package me.qunqun.shared.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json 工具类 <br>
 */
public class JsonUtil
{
	private JsonUtil() {}
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static String toJson(Object obj)
	{
		try
		{
			return objectMapper.writeValueAsString(obj);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static String toPrettyJson(Object obj)
	{
		try
		{
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T fromJson(String json, Class<T> clazz)
	{
		try
		{
			return objectMapper.readValue(json, clazz);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
