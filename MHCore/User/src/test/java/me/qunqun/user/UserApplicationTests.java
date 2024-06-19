package me.qunqun.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import me.qunqun.shared.entity.po.QUser;
import me.qunqun.user.entity.dto.OrderDto;
import me.qunqun.user.entity.dto.OrderQueryDto;
import me.qunqun.user.entity.repo.PackageRepository;
import me.qunqun.user.manager.RedisManager;
import me.qunqun.user.manager.SmsManager;
import me.qunqun.user.service.IOrderService;
import me.qunqun.user.service.impl.CalenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

@SpringBootTest
class UserApplicationTests
{
	@Resource
	PackageRepository packageRepository;
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	@Resource
	SmsManager smsManager;
	
	@Test
	@Transactional
	void test()
	{
		var msg1 = smsManager.sendCaptchaMessage("12345671111", "12345671111");
		System.out.println(msg1);
		var msg2 = smsManager.sendCaptchaMessage("12345671111", "12345671111");
		System.out.println(msg2);
		// 等待1分钟
		try
		{
			Thread.sleep(1 * 60 * 1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		var msg3 = smsManager.sendCaptchaMessage("12345671111", "12345671111");
		System.out.println(msg3);
	}
	
	@Test
	@Transactional
	void TestQueryDsl()
	{
		var qUser = QUser.user;
		var query = jpaQueryFactory.selectFrom(qUser);
		System.out.println(query);
	}
	
	@Resource
	CalenderService calenderService;
	
	@Test
	@Transactional
	void TestCalenderService()
	{
		var calender = calenderService.get(1);
		System.out.println(calender);
	}
	
	@Resource
	IOrderService orderService;
	
	@Test
	@Transactional
	@Rollback(true)
	void TestOrderService()
	{
		var queryD = new OrderQueryDto(null,null,"12345671111",null,null,null,null);
		var list = orderService.list(queryD);
		System.out.println(list);
		
	}
	
	@Resource
	RedisManager redisManager;
	
	@Test
	void testRedis()
	{
		redisManager.setString("test", "test");
		System.out.println(redisManager.getString("test"));
		var str2 = redisManager.getString("test2");
		System.out.println(str2 == null);
		var orderDto = new OrderDto();
		orderDto.setDate(LocalDate.now());
		orderDto.setUserId("1123");
		System.out.println("raw object: orderDto = " + orderDto);
		redisManager.setObject("order", orderDto);
		var orderDto2 = redisManager.getObject("order");
		System.out.println("get object: orderDto2 = " + orderDto2);
		var orderDto3 = redisManager.getObject("order2");
		System.out.println("get object: orderDto3 = " + orderDto3);
		
	}
	
}
