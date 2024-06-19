package me.qunqun.user.manager;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.qunqun.user.templete.JsonRedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Redis 管理器 <br>
 * 目前具有字符串存储和对象存储两种方式 <br>
 */
@Service
public class RedisManager
{
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource
	private JsonRedisTemplate jsonRedisTemplate;
	
	// 默认过期时间 10 分钟
	@Value("${app.redis.expire.minutes:10}")
	@Getter
	private int expireMinutes;
	
	/**
	 * 储存字符串
	 */
	public String setString(String key, String value)
	{
		var fKey = generateFormattedKey(key);
		stringRedisTemplate.opsForValue().set(fKey, value);
		stringRedisTemplate.expire(fKey, Duration.ofMinutes(expireMinutes));
		return key;
	}
	
	
	/**
	 * 读取字符串
	 */
	@Nullable
	public String getString(String key)
	{
		var fKey = generateFormattedKey(key);
		return stringRedisTemplate.opsForValue().get(fKey);
	}
	
	/**
	 * 储存对象
	 */
	@Nullable
	public <T> T setObject(String key, T value)
	{
		var fKey = generateFormattedKey(key);
		jsonRedisTemplate.opsForValue().set(fKey, fromData(value));
		jsonRedisTemplate.expire(fKey, Duration.ofMinutes(expireMinutes));
		return value;
	}
	
	
	/**
	 * 生成格式化的 key
	 */
	public static String generateFormattedKey(String baseKey)
	{
		var sb = new StringBuilder("me:qunqun:user");
		sb.append(":").append(baseKey);
		return sb.toString();
	}
	
	
	/**
	 * 自定义的 JsonData 类型, 便于检查数据的创建时间
	 * @param <T>
	 */
	@AllArgsConstructor
	static class JsonData<T>
	{
		private T data;
		private LocalDateTime createTime;
	}
	
	public static <T> JsonData<T> fromData(T data)
	{
		return new JsonData<>(data, LocalDateTime.now());
	}
	
	public static <T> T toData(JsonData<T> jsonData)
	{
		return jsonData.data;
	}
}
