package me.qunqun.user.service;

import me.qunqun.user.entity.vo.PackageInfoVo;

import java.util.List;

public interface IPackageService
{
	List<PackageInfoVo> list(Integer type);
	
	PackageInfoVo get(Integer id);
}
