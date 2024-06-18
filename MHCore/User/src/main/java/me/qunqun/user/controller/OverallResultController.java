package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.vo.OverallResultVo;
import me.qunqun.user.service.IOverallResultService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "体检总结管理", description = "体检总结 API")
@RequestMapping("/api/overall-result")
public class OverallResultController
{
	@Resource
	private IOverallResultService overallResultService;
	
	@Operation(summary = "获取体检总结列表")
	@PostMapping("/list")
	public Result<List<OverallResultVo>> list(@RequestBody Integer orderId)
	{
		return Result.success(overallResultService.list(orderId));
	}
}
