package me.qunqun.shared.entity.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
}