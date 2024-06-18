package me.qunqun.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.vo.CalenderVo;
import me.qunqun.user.service.ICalenderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "日历接口", description = "日历管理API")
@RequestMapping("/api/calender")
public class CalenderController
{
	@Resource
	ICalenderService calenderService;
	
	@Operation(summary = "获取预约日历", description = "获取预约日历信息")
	@GetMapping("/get/{hospitalId}")
	public Result<CalenderVo> get(@PathVariable int hospitalId)
	{
		return Result.success(calenderService.get(hospitalId));
	}
}
