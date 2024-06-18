package me.qunqun.user.entity.vo;

import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.CheckItemDetailed;

import java.io.Serializable;

/**
 * 检查项详细信息实体类
 * VO for {@link me.qunqun.shared.entity.po.CheckItemDetailed}
 */
@Getter
@ToString
public class CheckItemDetailedVo implements Serializable
{
	private Integer id;
	private String name;
	private String unit;
	private Double minRange;
	private Double maxRange;
	private String normalValue;
	private String normalValueDescription;
	private Integer type;
	private String remarks;
	
	public CheckItemDetailedVo(CheckItemDetailed checkItemDetailed)
	{
		this.id = checkItemDetailed.getId();
		this.name = checkItemDetailed.getName();
		this.unit = checkItemDetailed.getUnit();
		this.minRange = checkItemDetailed.getMinRange();
		this.maxRange = checkItemDetailed.getMaxRange();
		this.normalValue = checkItemDetailed.getNormalValue();
		this.normalValueDescription = checkItemDetailed.getNormalValueDescription();
		this.type = checkItemDetailed.getType();
		this.remarks = checkItemDetailed.getRemarks();
	}
}