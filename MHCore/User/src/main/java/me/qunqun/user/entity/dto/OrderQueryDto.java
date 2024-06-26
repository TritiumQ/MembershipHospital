package me.qunqun.user.entity.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderQueryDto
{
	private LocalDate startDate;
	private LocalDate endDate;
	
	private String userId;
	
	private Integer hospitalId;
	
	private Integer packageId;
	
	private Integer state;
	
	private Boolean deprecated;
	
	private Integer familyId;
	
}
