package me.qunqun.shared.entity.vo;

import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.CheckItem;

import java.util.List;

/**
 * 检查项详细信息实体类
 * VO for {@link me.qunqun.shared.entity.po.CheckItem} and {@link me.qunqun.shared.entity.po.CheckItemDetailed}
 */
@Getter
@ToString
public class CheckItemInfoVo extends CheckItemVo
{
	private List<CheckItemDetailedVo> checkItemDetaileds;
	
	public CheckItemInfoVo(CheckItem checkItem)
	{
		super(checkItem);
		this.checkItemDetaileds = checkItem.getCheckItemDetaileds().stream().map(CheckItemDetailedVo::new).toList();
	}
}
