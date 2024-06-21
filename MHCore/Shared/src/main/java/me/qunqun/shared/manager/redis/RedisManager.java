package me.qunqun.shared.manager.redis;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * Redis 管理器默认实现 <br>
 */
@Service
@Slf4j
public class RedisManager implements IRedisManager
{
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource
	private JsonRedisTemplate jsonRedisTemplate;
	
	// 默认过期时间 300 秒
	@Value("${app.redis.expire:300}")
	private long defaultExpireSeconds;

	@Override
	public String setString(@NonNull String key,@NonNull  String value, @Nullable Long expireSeconds)
	{
		Assert.notNull(key, "RedisManager setString(): key值不可为空");
		Assert.notNull(key, "RedisManager setString(): key值不可为空");
		var fKey = generateFormattedKey(key);
		stringRedisTemplate.opsForValue().set(fKey, value);
		if(expireSeconds != null)
		{
			stringRedisTemplate.expire(fKey, Duration.ofSeconds(expireSeconds));
		}
		else
		{
			stringRedisTemplate.expire(fKey, Duration.ofSeconds(defaultExpireSeconds));
		}
		return key;
	}
	
	@Override
	@Nullable
	public String getString(@NonNull String key)
	{
		Assert.notNull(key, "RedisManager getString(): key值不可为空");
		var fKey = generateFormattedKey(key);
		return stringRedisTemplate.opsForValue().get(fKey);
	}
	
	@Override
	public String setObject(@NonNull String key,@NonNull Object value, @Nullable Long expireSeconds)
	{
		Assert.notNull(key, "RedisManager setObject(): key值不可为空");
		Assert.notNull(value, "RedisManager setObject(): value值不可为空");
		var fKey = generateFormattedKey(key);
		jsonRedisTemplate.opsForValue().set(fKey, value);
		if(expireSeconds != null)
		{
			jsonRedisTemplate.expire(fKey, Duration.ofSeconds(expireSeconds));
		}
		else
		{
			jsonRedisTemplate.expire(fKey, Duration.ofSeconds(defaultExpireSeconds));
		}
		return key;
	}
	
	@Override
	@Nullable
	public Object getObject(@NonNull String key)
	{
		var fKey = generateFormattedKey(key);
		return jsonRedisTemplate.opsForValue().get(fKey);
	}
	
	@Override
	public String getObjectJson(String key)
	{
		Assert.notNull(key, "RedisManager getObjectJson(): key值不可为空");
		var fKey = generateFormattedKey(key);
		return stringRedisTemplate.opsForValue().get(fKey);
	}
	
	@Override
	@Nullable
	public Long getExpireSeconds(@NonNull String key)
	{
		Assert.notNull(key, "RedisManager getObjectExpireTime(): key值不可为空");
		var fKey = generateFormattedKey(key);
		var expireTime = jsonRedisTemplate.getExpire(fKey);
		if (expireTime != null)
		{
			return expireTime;
		}
		return null;
	}
	
	@Override
	public void deleteKey(@NonNull String key)
	{
		Assert.notNull(key, "RedisManager deleteKey(): key值不可为空");
		var fKey = generateFormattedKey(key);
		stringRedisTemplate.delete(fKey);
	}
	
	
	/**
	 * 生成格式化的 key
	 */
	private static String generateFormattedKey(String baseKey)
	{
		var sb = new StringBuilder("me:qunqun:user");
		sb.append(":").append(baseKey);
		return sb.toString();
	}
	
}
