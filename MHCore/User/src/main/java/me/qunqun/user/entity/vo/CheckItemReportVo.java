package me.qunqun.user.entity.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.CheckItemReport;

@Getter
@ToString
public class CheckItemReportVo
{
	private Integer id;
	private String review;
	private Integer orderId;
	private Integer checkItemId;
	
	public CheckItemReportVo(CheckItemReport checkItemReport)
	{
		this.id = checkItemReport.getId();
		this.review = checkItemReport.getReview();
		this.orderId = checkItemReport.getOrder().getId();
		this.checkItemId = checkItemReport.getCheckItem().getId();
	}
}
