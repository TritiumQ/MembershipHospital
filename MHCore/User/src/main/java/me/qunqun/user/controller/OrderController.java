package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.vo.OrderInfoVo;
import me.qunqun.user.entity.vo.OrderVo;
import me.qunqun.user.service.IOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/order")
@Tag(name = "订单管理", description = "订单管理API")
public class OrderController
{
	@Resource
	IOrderService orderService;
	
	@Operation(summary = "订单列表")
	@PostMapping("/list")
	public Result<List<OrderVo>> list(@RequestBody String userId)
	{
		return Result.success(orderService.list(userId));
	}
	
	
	@Operation(summary = "订单详情")
	@PostMapping("/get")
	public Result<OrderInfoVo> get(@RequestBody Integer orderId)
	{
		return Result.success(orderService.get(orderId));
	}
	
	@Operation(summary = "删除订单")
	@PostMapping("/remove")
	public Result<String> remove(@RequestBody Integer orderId)
	{
		orderService.remove(orderId);
		return Result.success();
	}
	
}
