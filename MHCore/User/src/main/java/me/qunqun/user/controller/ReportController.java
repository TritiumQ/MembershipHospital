package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.vo.ReportVo;
import me.qunqun.user.service.IReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/report")
@Tag(name = "體檢報告管理", description = "體檢報告管理 API")
public class ReportController
{
	@Resource
	private IReportService reportService;
	
	@Operation(summary = "取得體檢報告列表")
	@GetMapping("/list/{userId}")
	public Result<List<ReportVo>> getReportList(@PathVariable String userId)
	{
		return Result.success(reportService.getReportList(userId));
	}
	
	@Operation(summary = "取得體檢報告")
	@GetMapping("/get/{orderId}")
	public Result<ReportVo> getReport(@PathVariable Integer orderId)
	{
		return Result.success(reportService.getReport(orderId));
	}
}
