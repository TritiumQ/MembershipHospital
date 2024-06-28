package me.qunqun.user.crontab;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import me.qunqun.shared.entity.po.QOrder;
import me.qunqun.user.entity.po.QYearReport;
import me.qunqun.user.entity.po.YearReport;
import me.qunqun.user.entity.repo.UserRepo;
import me.qunqun.user.entity.repo.YearReportRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class YearReportCron
{
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	@Resource
	private YearReportRepo yearReportRepo;
	@Resource
	private UserRepo userRepo;
	
	/**
	 * 更新年报表，每天凌晨0点执行
	 */
	
	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void generateYearReport()
	{
		var qOrder = QOrder.order;
		// 获取用户列表
		var userList = userRepo.findAll();
		// 获取当前年份
		var year = LocalDate.now().getYear();
		// 遍历用户列表
		var reportList = new ArrayList<YearReport>();
		userList.forEach(user -> {
			// 查询用户当年订单
			var orderlist = jpaQueryFactory.selectFrom(qOrder)
					.where(qOrder.user.id.eq(user.getId())
							.and(qOrder.date.year().eq(year))
							.and(qOrder.pay.eq(1))
							.and(qOrder.deprecated.eq(false)))
					.fetch();
			// 计算支出
			var outlay = orderlist.stream().map(order -> order.getPackageField().getPrice()).reduce((a, b) -> a + b).orElse(0);
			// 保存年报表
			var qYearReport = QYearReport.yearReport;
			var yearReport = jpaQueryFactory.selectFrom(qYearReport)
					.where(qYearReport.userId.eq(user.getId())
							.and(qYearReport.year.eq(year)))
					.fetchFirst();
			if (yearReport == null)
			{
				yearReport = new YearReport();
				yearReport.setUserId(user.getId());
				yearReport.setYear(year);
				yearReport.setOutlay(BigDecimal.valueOf(outlay));
			}
			reportList.add(yearReport);
		});
		yearReportRepo.saveAll(reportList);
	}
}
