package me.qunqun.user.service;

import me.qunqun.user.entity.vo.ReportVo;

import java.util.List;

public interface IReportService
{
	List<ReportVo> getReportList(String userId);
	
	ReportVo getReport(Integer orderId);
}
