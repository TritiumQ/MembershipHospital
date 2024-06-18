package me.qunqun.shared.entity.vo;

import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.Order;

@Getter
@ToString
public class OrderInfoVo extends OrderVo
{
	//TODO: add more fields
	
	public OrderInfoVo(Order order)
	{
		super(order);
	}
}
