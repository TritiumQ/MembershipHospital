package me.qunqun.user.service;

import me.qunqun.shared.entity.vo.PackageInfoVo;
import me.qunqun.shared.entity.vo.PackageVo;

import java.util.List;

public interface IPackageService
{
	List<PackageVo> list(Integer type);
	
	PackageInfoVo get(Integer id);
}
