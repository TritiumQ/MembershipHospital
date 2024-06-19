package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.vo.PackageInfoVo;
import me.qunqun.user.entity.vo.PackageVo;
import me.qunqun.user.service.IPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/package")
@Tag(name = "体检套餐接口", description = "套餐接口")
@Slf4j
public class PackageController
{
	@Resource
	private IPackageService packageService;
	
	@Operation(summary = "获取套餐列表")
	@GetMapping("/list/{type}")
	public Result<List<PackageInfoVo>> list(@PathVariable Integer type)
	{
		return Result.success(packageService.list(type));
	}
	
	@Operation(summary = "获取套餐详情")
	@GetMapping("/get/{id}")
	public Result<PackageInfoVo> get(@PathVariable Integer id)
	{
		return Result.success(packageService.get(id));
	}
}
