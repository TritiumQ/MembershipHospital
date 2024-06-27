package me.qunqun.user.entity.vo;

import lombok.Getter;
import lombok.ToString;
import me.qunqun.user.entity.po.YearReport;

@Getter
@ToString
public class YearReportVo
{
	private final Integer id;
	private final String userId;
	private final Integer year;
	private final String outlay;
	
	public YearReportVo(YearReport report)
	{
		this.id = report.getId();
		this.userId = report.getUserId();
		this.year = report.getYear();
		this.outlay = report.getOutlay().toString();
	}
}
