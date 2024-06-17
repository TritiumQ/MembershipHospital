package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/package")
@Tag(name = "体检套餐接口", description = "套餐接口")
@Slf4j
public class PackageController
{
	
	@Operation
	@PostMapping("/list")
	public Result list()
	{
		return null;
	}
	
	@Operation
	@PostMapping("/get")
	public Result get()
	{
		return null;
	}
}
