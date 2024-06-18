package me.qunqun.user.service;

import me.qunqun.user.entity.dto.OrderDto;
import me.qunqun.user.entity.vo.OrderInfoVo;
import me.qunqun.user.entity.vo.OrderVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOrderService
{
	List<OrderVo> list(String userId);
	
	OrderInfoVo get(Integer orderId);
	
	void remove(Integer orderId);
	
	@Transactional
	OrderInfoVo create(OrderDto orderDto);
}
