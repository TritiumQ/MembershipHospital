package me.qunqun.doctor;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.service.DoctorService;
import me.qunqun.doctor.service.EmailService;
import me.qunqun.doctor.service.OrderService;
import me.qunqun.doctor.utils.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class DoctorApplicationTests
{
	@Autowired
	private EmailService emailService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ScheduledTasks scheduledTasks;

	@Test
	void contextLoads()
	{
		log.info("Test start");
		Integer orderNum = orderService.getOrderCountByHospitalId(1);
		log.info("orderNum: {}", orderNum);
		scheduledTasks.sendScheduledEmail();
	}
	
}
