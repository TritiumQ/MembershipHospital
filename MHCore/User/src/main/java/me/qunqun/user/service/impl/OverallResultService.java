package me.qunqun.user.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import me.qunqun.shared.entity.po.QOverallResult;
import me.qunqun.shared.entity.vo.OverallResultVo;
import me.qunqun.user.service.IOverallResultService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OverallResultService implements IOverallResultService
{
	@Resource
	private JPAQueryFactory qFactory;
	
	@Override
	public List<OverallResultVo> list(Integer orderId)
	{
		var qOResult = QOverallResult.overallResult;
		var query = qFactory.selectFrom(qOResult)
			.where(qOResult.orderId.eq(orderId));
		return query.fetch().stream().map(OverallResultVo::new).toList();
	}
	
}
