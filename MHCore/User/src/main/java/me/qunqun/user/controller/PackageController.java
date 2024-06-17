package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.shared.entity.vo.PackageInfoVo;
import me.qunqun.shared.entity.vo.PackageVo;
import me.qunqun.user.service.IPackageService;
import me.qunqun.user.service.impl.PackageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/package")
@Tag(name = "体检套餐接口", description = "套餐接口")
@Slf4j
public class PackageController
{
	@Resource
	private IPackageService packageService;
	
	@Operation
	@PostMapping("/list")
	public Result<List<PackageVo>> list(@RequestBody Integer type)
	{
		return Result.success(packageService.list(type));
	}
	
	@Operation
	@PostMapping("/get")
	public Result<PackageInfoVo> get(@RequestBody Integer id)
	{
		return Result.success(packageService.get(id));
	}
}
