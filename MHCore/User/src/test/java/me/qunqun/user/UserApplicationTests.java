package me.qunqun.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import me.qunqun.shared.entity.po.QUser;
import me.qunqun.user.entity.repo.PackageRepository;
import me.qunqun.user.service.impl.CalenderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApplicationTests
{
	@Resource
	PackageRepository packageRepository;
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	
	@Test
	@Transactional
	void test()
	{
		var pack = packageRepository.findById(1).get();
		var checkItems = pack.getCheckItems();
		System.out.println(checkItems);
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
	
}
