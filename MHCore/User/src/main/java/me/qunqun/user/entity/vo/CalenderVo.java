package me.qunqun.user.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
public class CalenderVo
{
	private LocalDate generateDate;
	private List<Map<LocalDate, Integer>> appointmentCounts;
	
	
}
