package me.qunqun.user.entity.vo;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.Order;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
public class ReportVo
{
	private Integer orderId;
	private LocalDate date;
	private HospitalVo hospital;
	@Nullable
	private List<OverallResultVo> overallResults;
	@Nullable
	private List<CheckItemReportVo> checkItemReports;
	
	public ReportVo(Order order)
	{
		this.orderId = order.getId();
		this.date = order.getDate();
		this.hospital = new HospitalVo(order.getHospital());
		if(order.getState() == 2 && order.getDeprecated() == false)
		{
			this.overallResults = order.getOverallResults().stream().map(OverallResultVo::new).toList();
			this.checkItemReports = order.getCheckItemReports().stream().map(CheckItemReportVo::new).toList();
		}
	}
	
}
