package me.qunqun.user.service.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import me.qunqun.shared.entity.po.*;
import me.qunqun.user.entity.dto.OrderDto;
import me.qunqun.user.entity.repo.OrderRepo;
import me.qunqun.user.entity.vo.OrderInfoVo;
import me.qunqun.user.entity.vo.OrderVo;
import me.qunqun.shared.exception.CrudExceptionCode;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.exception.OperationExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService implements me.qunqun.user.service.IOrderService
{
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	
	@Resource
	private OrderRepo orderRepo;
	
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
	
	@Override
	@Transactional
	public OrderInfoVo create(OrderDto orderDto)
	{
		// 先搜索是否有重复的订单
		// 一个用户在同一天的同一个医院中只能预约一次
		var qOrder = QOrder.order;
		var query = jpaQueryFactory.selectFrom(qOrder)
			.where(qOrder.user.id.eq(orderDto.getUserId())
				.and(qOrder.hospital.id.eq(orderDto.getHospitalId()))
				.and(qOrder.date.eq(orderDto.getDate())));
		var orders = query.fetch();
		if(orders.size() > 0)
		{
			throw new CustomException(OperationExceptionCode.ORDER_EXITED);
		}
		
		var qHospital = QHospital.hospital;
		var qPackage = QPackage.package$;
		var qUser = QUser.user;
		
		var order = new Order();
		order.setDate(orderDto.getDate());
		
		var hospital = jpaQueryFactory.selectFrom(qHospital)
			.where(qHospital.id.eq(orderDto.getHospitalId()))
			.fetchOne();
		if(hospital == null)
		{
			throw new CustomException(CrudExceptionCode.QUERY_ERROR);
		}
		order.setHospital(hospital);
		
		var pack = jpaQueryFactory.selectFrom(qPackage)
			.where(qPackage.id.eq(orderDto.getPackageId()))
			.fetchOne();
		if(pack == null)
		{
			throw new CustomException(CrudExceptionCode.QUERY_ERROR);
		}
		order.setPackageField(pack);
		
		var user = jpaQueryFactory.selectFrom(qUser)
			.where(qUser.id.eq(orderDto.getUserId()))
			.fetchOne();
		if(user == null)
		{
			throw new CustomException(CrudExceptionCode.QUERY_ERROR);
		}
		order.setUser(user);
		
		order.setState(0);
		order.setDeprecated(false);
		
		orderRepo.save(order);
		
		if(order.getId() == null)
		{
			throw new CustomException(CrudExceptionCode.ADD_ERROR);
		}
		
		return new OrderInfoVo(order);
	}
	
}
