package me.qunqun.user.entity.vo;

import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.CheckItemReport;

@Getter
@ToString
public class CheckItemReportInfoVo extends CheckItemReportVo
{
	//TODO add more fields
	
	public CheckItemReportInfoVo(CheckItemReport checkItemReport)
	{
		super(checkItemReport);
	}
}
