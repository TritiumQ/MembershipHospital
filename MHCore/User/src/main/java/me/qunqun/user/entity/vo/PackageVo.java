package me.qunqun.shared.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.qunqun.shared.entity.po.Package;

import java.io.Serializable;

/**
 * 体检套餐实体类
 * VO for {@link me.qunqun.shared.entity.po.Package}
 */
@Getter
@ToString
public class PackageVo implements Serializable
{
	private Integer id;
	private String name;
	private Integer type;
	private Integer price;
	
	public PackageVo(Package pack)
	{
		this.id = pack.getId();
		this.name = pack.getName();
		this.type = pack.getType();
		this.price = pack.getPrice();
	}
}