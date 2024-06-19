package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.dto.OrderDto;
import me.qunqun.user.entity.dto.OrderQueryDto;
import me.qunqun.user.entity.vo.OrderInfoVo;
import me.qunqun.user.entity.vo.OrderVo;
import me.qunqun.user.service.IOrderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
	public Result<List<OrderInfoVo>> list(@RequestBody OrderQueryDto orderQueryDto)
	{
		return Result.success(orderService.list(orderQueryDto));
	}
	
	
	@Operation(summary = "订单详情")
	@GetMapping("/get/{orderId}")
	public Result<OrderInfoVo> get(@PathVariable Integer orderId)
	{
		return Result.success(orderService.get(orderId));
	}
	
	@Operation(summary = "取消订单")
	@PostMapping("/cancel/{orderId}")
	@Transactional
	public Result remove(@PathVariable Integer orderId)
	{
		orderService.cancel(orderId);
		return Result.success();
	}
	
	
	@Operation(summary = "创建订单")
	@PostMapping("/create")
	@Transactional
	public Result<OrderInfoVo> create(@RequestBody OrderDto orderDto)
	{
		var order = orderService.create(orderDto);
		return Result.success(order);
	}
	
}
