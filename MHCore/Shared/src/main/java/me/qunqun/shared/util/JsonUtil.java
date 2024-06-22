package me.qunqun.shared.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json 工具类 <br>
 */
public class JsonUtil
{
	private JsonUtil() {}
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 将对象转换为json字符串
	 */
	public static String toJson(Object obj)
	{
		try
		{
			return objectMapper.writeValueAsString(obj);
		}
		catch (Exception e)
		{
			throw new RuntimeException("JsonUtil.toJson 预期外的错误", e);
		}
	}
	
	/**
	 * 将对象转换为格式化的json字符串
	 */
	public static String toPrettyJson(Object obj)
	{
		try
		{
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		}
		catch (Exception e)
		{
			throw new RuntimeException("JsonUtil.toPrettyJson 预期外的错误", e);
		}
	}
	
	/**
	 * 将json字符串转换为对象
	 */
	public static <T> T fromJson(String json, Class<T> clazz)
	{
		try
		{
			return objectMapper.readValue(json, clazz);
		}
		catch (Exception e)
		{
			throw new RuntimeException("JsonUtil.fromJson 预期外的错误", e);
		}
	}
	
	/**
	 * 将json字符串转换为Json对象
	 * @param json json字符串
	 * @return JsonNode Json对象根节点
	 */
	public static JsonNode readTree(String json)
	{
		try
		{
			return objectMapper.readTree(json);
		}
		catch (Exception e)
		{
			throw new RuntimeException("JsonUtil.readTree 预期外的错误", e);
		}
	}
	
}
