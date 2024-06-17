package me.qunqun.user.service.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
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
		return null;
	}
	
}
