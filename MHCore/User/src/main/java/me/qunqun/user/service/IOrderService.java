package me.qunqun.user.service;

import me.qunqun.user.entity.dto.OrderDto;
import me.qunqun.user.entity.dto.OrderQueryDto;
import me.qunqun.user.entity.vo.OrderInfoVo;
import me.qunqun.user.entity.vo.OrderVo;

import java.util.List;

public interface IOrderService
{
	
	List<OrderInfoVo> list(OrderQueryDto orderQueryDto);
	
	OrderInfoVo get(Integer orderId);
	
	void cancel(Integer orderId);
	
	OrderInfoVo create(OrderDto orderDto);
}
