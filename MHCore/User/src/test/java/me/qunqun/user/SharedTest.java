package me.qunqun.user;

import jakarta.annotation.Resource;
import lombok.*;
import me.qunqun.shared.manager.mq.entity.ObjectMessage;
import me.qunqun.shared.manager.mq.handler.DefaultDirectProducer;
import me.qunqun.shared.manager.redis.IRedisManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class SharedTest
{
	@Resource
	IRedisManager redisManager;
	
	@Test
	void TestRedis()
	{
		// 测试Redis
		// 保存字符串
		var str1v1 = "Hello this is str1v1";
		redisManager.setString("str1", str1v1,null);
		var str1Get = redisManager.getString("str1");
		assert str1v1.equals(str1Get);
		System.out.println("str1v1: " + str1Get + " => str1Get: " + str1Get);
		
		// 覆盖保存
		var str1v2 = "Hello this is str1v2...";
		redisManager.setString("str1", str1v2,null);
		var str1Get2 = redisManager.getString("str1");
		assert str1v2.equals(str1Get2);
		System.out.println("str1v2: " + str1Get2 + " => str1Get2: " + str1Get2);
		assert !str1v1.equals(str1Get2);
		System.out.println("str1v1: " + str1v1 + " => str1Get2: " + str1Get2);
		
		// 删除
		redisManager.deleteKey("str1");
		var str1Get3 = redisManager.getString("str1");
		assert str1Get3 == null;
		System.out.println("str1Get3: " + str1Get3);
		
		// 读取不存在的key
		var str3Get = redisManager.getString("str3");
		assert str3Get == null;
		System.out.println("str3Get: " + str3Get);
		
		// 保存对象
		var obj1 = new TestObj("Hello this is obj1", 123, LocalDateTime.now());
		redisManager.setObject("obj1", obj1,null);
		var obj1Get = (TestObj) redisManager.getObject("obj1");
		assert obj1.equals(obj1Get);
		System.out.println("obj1: " + obj1Get + " => obj1Get: " + obj1Get);
		
		// 读取对象Json
		var obj1Json = redisManager.getObjectJson("obj1");
		System.out.println("obj1Json: " + obj1Json);
		
		//设置缓存时间
		redisManager.setString("str2", "Hello this is str2", 10L);
		var str2Get = redisManager.getString("str2");
		assert str2Get != null;
		System.out.println("str2Get: " + str2Get);
		try
		{
			System.out.println("等待10秒");
			Thread.sleep(10000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		var str2Get2 = redisManager.getString("str2");
		assert str2Get2 == null;
		System.out.println("str2Get2: " + str2Get2);
		
	}
	
	@Resource
	DefaultDirectProducer defaultDirectProducer;
	
	
	@Test
	void TestMq()
	{
		// 测试MQ
		var msg = ObjectMessage.create("Hello this is message", new TestObj("Hello this is obj1", 123, LocalDateTime.now()));
		defaultDirectProducer.sendObject(msg);
		defaultDirectProducer.sendString("Hello this is string");
		try
		{
			Thread.sleep(10000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Getter
	@ToString
	@EqualsAndHashCode
	@AllArgsConstructor
	@NoArgsConstructor
	private static class TestObj
	{
		private String str;
		private int num;
		private LocalDateTime time;
	}
}
