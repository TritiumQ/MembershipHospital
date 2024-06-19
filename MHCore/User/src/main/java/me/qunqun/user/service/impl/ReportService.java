package me.qunqun.user.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.QOrder;
import me.qunqun.shared.entity.po.QUser;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.entity.vo.ReportVo;
import me.qunqun.user.exception.OperationExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReportService implements me.qunqun.user.service.IReportService
{
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	
	@Override
	public List<ReportVo> getReportList(String userId)
	{
		var qUser = QUser.user;
		var queryUser = jpaQueryFactory.selectFrom(qUser).where(qUser.id.eq(userId));
		var user = queryUser.fetchOne();
		if(user == null)
		{
			throw new CustomException(OperationExceptionCode.USER_NOT_FOUND);
		}
		var qOrder = QOrder.order;
		var queryOrder = jpaQueryFactory
				.selectFrom(qOrder)
				.where(qOrder.user.id.eq(userId)
						.and(qOrder.state.eq(2))
						.and(qOrder.deprecated.eq(false)));
		var orders = queryOrder.fetch();
		return orders.stream().map(ReportVo::new).toList();
	}
	
	@Override
	public ReportVo getReport(Integer orderId)
	{
		var qOrder = QOrder.order;
		var query = jpaQueryFactory.selectFrom(qOrder).where(qOrder.id.eq(orderId));
		var order = query.fetchOne();
		if(order == null)
		{
			throw new CustomException(OperationExceptionCode.ORDER_NOT_FOUND);
		}
		if(order.getDeprecated())
		{
			throw new CustomException(OperationExceptionCode.ORDER_HAS_BEEN_CANCELED);
		}
		if(order.getState() != 2)
		{
			throw new CustomException(OperationExceptionCode.ORDER_STATE_ERROR);
		}
		return new ReportVo(order);
	}
	
	
}
