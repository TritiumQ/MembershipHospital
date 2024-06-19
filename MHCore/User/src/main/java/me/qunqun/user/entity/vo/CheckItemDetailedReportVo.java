package me.qunqun.user.entity.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.CheckItemDetailedReport;
import me.qunqun.shared.entity.po.CheckItemReport;

@Getter
@ToString
public class CheckItemDetailedReportVo
{
	private Integer id;
	private String value;
	private Integer error;
	private Integer orderId;
	private Integer checkItemReportId;
	private CheckItemDetailedVo checkItemDetailed;
	
	public CheckItemDetailedReportVo(CheckItemDetailedReport report)
	{
		this.id = report.getId();
		this.value = report.getValue();
		this.error = report.getError();
		this.orderId = report.getCheckItemReport().getOrder().getId();
		this.checkItemReportId = report.getCheckItemReport().getId();
		this.checkItemDetailed = new CheckItemDetailedVo(report.getCheckItemDetailed());
	}
}
