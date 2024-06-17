package me.qunqun.user.service.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import me.qunqun.shared.entity.po.QOrder;
import me.qunqun.shared.entity.vo.OrderVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService
{
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	
	public List<OrderVo> list(Integer userId)
	{
		var qOrder = QOrder.order;
		var query = jpaQueryFactory.selectFrom(qOrder)
			.where(qOrder.userId.eq(userId));
		return query.fetch().stream().map(OrderVo::new).toList();
	}
	
	
	
}
