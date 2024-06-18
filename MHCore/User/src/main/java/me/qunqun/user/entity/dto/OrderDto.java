package me.qunqun.user.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class OrderDto
{
	@NotNull
	private LocalDate date;
	@NotNull
	private String userId;
	@NotNull
	private Integer hospitalId;
	@NotNull
	private Integer packageId;
	
	
}
