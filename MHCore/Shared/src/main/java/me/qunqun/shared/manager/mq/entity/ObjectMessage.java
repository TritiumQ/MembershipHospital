package me.qunqun.shared.manager.mq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

/**
 * Object消息基类
 * @param <T> 消息数据类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectMessage<T>
{
	@NonNull
	private String id;
	@NonNull
	private String message;
	@Nullable
	private T messageData;
	
	public static <T> ObjectMessage<T> create(String message, T messageData)
	{
		return new ObjectMessage<>(UUID.randomUUID().toString(), message, messageData);
	}
}
