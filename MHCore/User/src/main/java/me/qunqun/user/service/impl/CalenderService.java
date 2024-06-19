package me.qunqun.user.service.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.QHospital;
import me.qunqun.shared.exception.CrudExceptionCode;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.entity.po.QAppointmentCount;
import me.qunqun.user.entity.repo.AppointmentCountRepo;
import me.qunqun.user.entity.vo.CalenderVo;
import me.qunqun.user.exception.OperationExceptionCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CalenderService implements me.qunqun.user.service.ICalenderService
{
	@Resource
	private JPAQueryFactory queryFactory;
	
	@Resource
	private AppointmentCountRepo appointmentCountRepo;
	
	/**
	 * 获取预约日历，包括工作时间和预约数量，时间从当天开始, 范围为一个月
	 * @param hospitalId 医院id
	 * @return 日历
	 */
	@Override
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
		// idx - dayofweek : 0 - sunday, 1 - monday, 2 - tuesday, 3 - wednesday, 4 - thursday, 5 - friday, 6 - saturday
		var workTimes = hospital.getRule().split(",");
		if(workTimes.length != 7)
		{
			throw new CustomException(OperationExceptionCode.ERROR);
		}
		// 查询已预约数量
		//var appointmentCountViews = appointmentCountRepo.findAll();
		var qA = QAppointmentCount.appointmentCount;
		var query2 = queryFactory.selectFrom(qA).where(qA.hospitalId.eq(hospitalId));
		var appointmentCountViews = query2.fetch();
		
		var now = LocalDate.now();
		List<CalenderVo.AppointmentCount> appointmentCounts = new ArrayList<>();
		for(int i = 1; i <= 30; i++)
		{
			var date = now.plusDays(i);
			// idx - dayofweek : 0 - sunday 7, 1 - monday 1, 2 - tuesday 2, 3 - wednesday 3, 4 - thursday 4, 5 - friday 5, 6 - saturday 6
			var dayOfWeek = date.getDayOfWeek().getValue() == 7 ? 0 : date.getDayOfWeek().getValue();
			if(workTimes[dayOfWeek].equals("0"))
			{
				appointmentCounts.add(new CalenderVo.AppointmentCount(date, dayOfWeek, 0L));
				continue;
			}
			var baseCount = Long.parseLong(workTimes[dayOfWeek]);
			var appointmentCount = appointmentCountViews.stream().filter(x -> x.getDate().equals(date)).findFirst().orElse(null);
			if(appointmentCount == null)
			{
				appointmentCounts.add(new CalenderVo.AppointmentCount(date, date.getDayOfWeek().getValue(), baseCount));
			}
			else
			{
				appointmentCounts.add(new CalenderVo.AppointmentCount(date, date.getDayOfWeek().getValue(), baseCount - appointmentCount.getCount()));
			}
		}
		var vo = new CalenderVo(now, appointmentCounts);
		return vo;
	}
}
