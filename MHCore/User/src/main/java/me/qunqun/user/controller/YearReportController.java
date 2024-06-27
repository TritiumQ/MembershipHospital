package me.qunqun.user.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.user.entity.po.QYearReport;
import me.qunqun.user.entity.po.YearReport;
import me.qunqun.user.entity.vo.YearReportVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/year-report")
@Slf4j
@Tag(name = "用户年数据报表")
public class YearReportController
{
	
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	
	@GetMapping("/list/{userId}")
	@Operation(summary = "获取用户年数据报表")
	@Transactional
	public List<YearReportVo> list(@PathVariable String userId)
	{
		Assert.notNull(userId, "用户ID不能为空");
		var qYearReport = QYearReport.yearReport;
		var list =  jpaQueryFactory.selectFrom(qYearReport)
			.where(qYearReport.userId.eq(userId))
			.fetch();
		return list.stream().map(YearReportVo::new).toList();
	}
}
