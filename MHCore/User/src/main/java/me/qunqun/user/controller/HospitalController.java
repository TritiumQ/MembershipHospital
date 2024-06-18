package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.vo.HospitalVo;
import me.qunqun.user.service.IHospitalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "医院管理", description = "医院管理相关接口")
@RestController
@RequestMapping("/api/hospital")
@Slf4j
public class HospitalController
{
	@Resource
	private IHospitalService hospitalService;
	
	@GetMapping("/list/{state}")
	@Operation(summary = "查询医院列表", description = "查询医院列表")
	public Result<List<HospitalVo>> list(@PathVariable Integer state)
	{
		return Result.success(hospitalService.list(state));
	}
	
	@GetMapping("/get/{id}")
	@Operation(summary = "查询医院详情", description = "查询医院详情")
	public Result<HospitalVo> get(@PathVariable Integer id)
	{
		return Result.success(hospitalService.get(id));
	}
}
