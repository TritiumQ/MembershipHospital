package me.qunqun.user.manager;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.user.templete.JsonRedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Redis 管理器 <br>
 * 目前具有字符串存储和对象存储两种方式 <br>
 */
@Service
@Slf4j
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
		return setString(key, value, this.expireMinutes);
	}
	
	/**
	 * 储存字符串, 使用默认过期时间
	 */
	public String setString(String key, String value, int expireMinutes)
	{
		Assert.notNull(key, "RedisManager setString(): key值不可为空");
		Assert.notNull(key, "RedisManager setString(): key值不可为空");
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
		Assert.notNull(key, "RedisManager getString(): key值不可为空");
		var fKey = generateFormattedKey(key);
		return stringRedisTemplate.opsForValue().get(fKey);
	}
	
	/**
	 * 储存对象
	 */
	public String setObject(String key, Object value, int expireMinutes)
	{
		Assert.notNull(key, "RedisManager setObject(): key值不可为空");
		Assert.notNull(value, "RedisManager setObject(): value值不可为空");
		var fKey = generateFormattedKey(key);
		jsonRedisTemplate.opsForValue().set(fKey, value);
		jsonRedisTemplate.expire(fKey, Duration.ofMinutes(expireMinutes));
		return key;
	}
	
	/**
	 * 储存对象, 使用默认过期时间
	 */
	public String setObject(String key, Object value)
	{
		return setObject(key, value, this.expireMinutes);
	}
	
	/**
	 * 读取对象
	 */
	@Nullable
	public Object getObject(String key)
	{
		var fKey = generateFormattedKey(key);
		return jsonRedisTemplate.opsForValue().get(fKey);
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
	
}
