package me.qunqun.shared.manager.redis;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Redis 管理器 <br>
 * 目前具有字符串存储和对象存储两种方式 <br>
 */
public interface IRedisManager
{
	
	/**
	 * 储存字符串, 若 expireSeconds 为 null, 则使用默认过期时间
	 */
	String setString(@NonNull String key, @NonNull String value, @Nullable Long expireSeconds);
	
	/**
	 * 读取字符串
	 */
	@Nullable
	String getString(String key);
	
	/**
	 * 储存对象, 若 expireSeconds 为 null, 则使用默认过期时间
	 */
	String setObject(@NonNull String key,@NonNull  Object value, @Nullable Long expireSeconds);
	
	/**
	 * 读取对象
	 */
	@Nullable
	Object getObject(@NonNull String key);
	
	/**
	 * 获取剩余过期时间(秒)
	 */
	@Nullable
	Long getExpireSeconds(@NonNull String key);
	
	/**
	 * 删除 key
	 */
	void deleteKey(@NonNull String key);

}
