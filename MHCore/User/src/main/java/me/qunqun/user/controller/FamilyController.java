package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.vo.FamilyVoAndDto;
import me.qunqun.user.service.impl.FamilyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/family")
@Slf4j
@Tag(name = "家庭管理", description = "家庭管理API")
public class FamilyController
{
	@Resource
	private FamilyService familyService;
	
	@Operation(summary = "家属列表")
	@GetMapping("/list/{userId}")
	public Result<List<FamilyVoAndDto>> list(@PathVariable String userId)
	{
		return Result.success(familyService.list(userId));
	}
	
	@Operation(summary = "添加家属")
	@PostMapping("/add")
	public Result<FamilyVoAndDto> add(@RequestBody FamilyVoAndDto dto)
	{
		return Result.success(familyService.add(dto));
	}
}
