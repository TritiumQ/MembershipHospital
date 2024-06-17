package me.qunqun.shared.entity.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.Order;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link me.qunqun.shared.entity.po.Order}
 */
@Getter
@ToString
public class OrderVo implements Serializable
{
	private final Integer id;
	@NotNull
	private final LocalDate date;
	@NotNull
	private final Integer userId;
	@NotNull
	private final Integer hospitalId;
	@NotNull
	private final Integer packageId;
	@NotNull
	private final Integer state;
	@NotNull
	private final Boolean deprecated;
	
	public OrderVo(Order order)
	{
		this.id = order.getId();
		this.date = order.getDate();
		this.userId = order.getUserId();
		this.hospitalId = order.getHospitalId();
		this.packageId = order.getPackageId();
		this.state = order.getState();
		this.deprecated = order.getDeprecated();
	}
}