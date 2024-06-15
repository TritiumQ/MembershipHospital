package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.shared.entity.vo.HospitalVo;
import me.qunqun.user.service.IHospitalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "医院管理", description = "医院管理相关接口")
@RestController
@RequestMapping("/api/hospital")
@Slf4j
public class HospitalController
{
	@Resource
	private IHospitalService hospitalService;
	
	@PostMapping("/list")
	@Operation(summary = "查询医院列表", description = "查询医院列表")
	public Result<List<HospitalVo>> list(@RequestBody Integer state)
	{
		return Result.success(hospitalService.list(state));
	}
	
	@PostMapping("/get")
	@Operation(summary = "查询医院详情", description = "查询医院详情")
	public Result<HospitalVo> get(@RequestBody Integer id)
	{
		return Result.success(hospitalService.get(id));
	}
}
