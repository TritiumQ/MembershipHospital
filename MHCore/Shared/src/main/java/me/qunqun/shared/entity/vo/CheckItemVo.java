package me.qunqun.shared.entity.vo;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.CheckItem;

/**
 * 检查项实体类
 * VO for {@link me.qunqun.shared.entity.po.CheckItem}
 */
@Getter
@ToString
public class CheckItemVo
{
	
	private Integer id;
	private String name;
	private String content;
	private String meaning;
	private String remarks;
	
	public CheckItemVo(CheckItem checkItem)
	{
		this.id = checkItem.getId();
		this.name = checkItem.getName();
		this.content = checkItem.getContent();
		this.meaning = checkItem.getMeaning();
		this.remarks = checkItem.getRemarks();
	}
}
