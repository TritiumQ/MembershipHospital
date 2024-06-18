package me.qunqun.user.service.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.QHospital;
import me.qunqun.shared.exception.CrudExceptionCode;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.entity.repo.AppointmentCountRepo;
import me.qunqun.user.entity.vo.CalenderVo;
import me.qunqun.user.exception.OperationException;
import me.qunqun.user.exception.OperationExceptionCode;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class CalenderService
{
	@Resource
	private JPAQueryFactory queryFactory;
	
	@Resource
	private AppointmentCountRepo appointmentCountRepo;
	
	/**
	 * 获取预约日历，包括工作时间和预约数量，时间范围为一个月
	 * @param hospitalId 医院id
	 * @return 日历
	 */
	public CalenderVo get(int hospitalId)
	{
		// 先查询数据库
		var qHospital = QHospital.hospital;
		var query = queryFactory.selectFrom(qHospital).where(qHospital.id.eq(hospitalId));
		var hospital = query.fetchOne();
		if(hospital == null)
		{
			throw new CustomException(CrudExceptionCode.QUERY_ERROR);
		}
		var workTimes = hospital.getRule().split(",");
		if(workTimes.length != 7)
		{
			throw new CustomException(OperationExceptionCode.ERROR);
		}
		
		var appointmentCounts = appointmentCountRepo.findAll();
		
		return null;
	}
}
