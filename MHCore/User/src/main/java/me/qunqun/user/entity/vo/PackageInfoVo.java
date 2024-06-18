package me.qunqun.user.entity.vo;


import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.Package;

import java.util.List;

/**
 * 体检套餐详细信息实体类
 * VO for {@link me.qunqun.shared.entity.po.Package} and {@link me.qunqun.shared.entity.po.PackageDetailed}
 */
@Getter
@ToString
public class PackageInfoVo extends PackageVo
{
	
	private List<CheckItemVo> checkItems;
	
	public PackageInfoVo(Package pack)
	{
		super(pack);
		this.checkItems = pack.getCheckItems().stream().map(CheckItemVo::new).toList();
	}
}
