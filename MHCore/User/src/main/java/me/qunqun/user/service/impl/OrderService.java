package me.qunqun.user.service.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import me.qunqun.shared.entity.po.QOrder;
import me.qunqun.user.entity.vo.OrderInfoVo;
import me.qunqun.user.entity.vo.OrderVo;
import me.qunqun.shared.exception.CrudExceptionCode;
import me.qunqun.shared.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements me.qunqun.user.service.IOrderService
{
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	
	@Override
	public List<OrderVo> list(String userId)
	{
		var qOrder = QOrder.order;
		var query = jpaQueryFactory.selectFrom(qOrder)
			.where(qOrder.user.id.eq(userId));
		return query.fetch().stream().map(OrderVo::new).toList();
	}
	
	
	@Override
	public OrderInfoVo get(Integer orderId)
	{
		var qOrder = QOrder.order;
		var query = jpaQueryFactory.selectFrom(qOrder)
			.where(qOrder.id.eq(orderId));
		var order =  query.fetchOne();
		return new OrderInfoVo(order);
	}
	
	@Override
	public void remove(Integer orderId)
	{
		var qOrder = QOrder.order;
		var query = jpaQueryFactory.delete(qOrder)
			.where(qOrder.id.eq(orderId));
		var result = query.execute();
		if(result == 0)
		{
			throw new CustomException(CrudExceptionCode.DELETE_ERROR);
		}
	}
	
}
