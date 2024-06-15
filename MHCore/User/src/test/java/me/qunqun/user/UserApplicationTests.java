package me.qunqun.user;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import me.qunqun.shared.repo.PackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApplicationTests
{
	@Resource
	PackageRepository packageRepository;
	
	
	@Test
	@Transactional
	void test()
	{
		var pack = packageRepository.findById(1).get();
		var checkItems = pack.getCheckItems();
		System.out.println(checkItems);
	}
	
}
