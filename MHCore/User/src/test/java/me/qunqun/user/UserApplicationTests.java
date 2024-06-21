package me.qunqun.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import me.qunqun.shared.entity.po.QUser;
import me.qunqun.user.entity.dto.OrderQueryDto;
import me.qunqun.user.entity.repo.PackageRepository;
import me.qunqun.shared.manager.redis.RedisManager;
import me.qunqun.shared.manager.sms.SmsManager;
import me.qunqun.user.service.IOrderService;
import me.qunqun.user.service.impl.CalenderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

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
	}
	
}
