package me.qunqun.user.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.QOrder;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.entity.vo.ReportVo;
import me.qunqun.user.exception.OperationExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CheckReportService
{
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	
	public List<ReportVo> getReportList(String userId)
	{
		var qOrder = QOrder.order;
		var query = jpaQueryFactory
				.selectFrom(qOrder)
				.where(qOrder.user.id.eq(userId)
						.and(qOrder.state.eq(2))
						.and(qOrder.deprecated.eq(false)));
		var orders = query.fetch();
		return orders.stream().map(ReportVo::new).toList();
	}
	
	public ReportVo getReportInfo(Integer orderId)
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
