package me.qunqun.user.entity.vo;

import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.CheckItemReport;

import java.util.List;

@Getter
@ToString
public class CheckItemReportVo
{
	private Integer id;
	private String review;
	private Integer orderId;
	private CheckItemVo checkItem;
	private List<CheckItemDetailedReportVo> detailedReports;
	
	public CheckItemReportVo(CheckItemReport checkItemReport)
	{
		this.id = checkItemReport.getId();
		this.review = checkItemReport.getReview();
		this.orderId = checkItemReport.getOrder().getId();
		this.checkItem = new CheckItemVo(checkItemReport.getCheckItem());
		this.detailedReports = checkItemReport.getCheckItemDetailedReports().stream().map(CheckItemDetailedReportVo::new).toList();
	}
}
