package me.qunqun.user.entity.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.OverallResult;

import java.io.Serializable;

/**
 * 体检总结
 * VO for {@link me.qunqun.shared.entity.po.OverallResult}
 */
@Getter
@ToString
public class OverallResultVo implements Serializable
{
	private Integer id;
	@NotNull
	@Size(max = 40)
	private String title;
	@Size(max = 400)
	private String content;
	@NotNull
	private Integer orderId;
	
	public OverallResultVo(OverallResult overallResult)
	{
		this.id = overallResult.getId();
		this.title = overallResult.getTitle();
		this.content = overallResult.getContent();
		this.orderId = overallResult.getOrder().getId();
	}
}