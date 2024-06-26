package me.qunqun.user.entity.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.Order;
import org.springframework.lang.Nullable;

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
	private final String userId;
	@NotNull
	private final Integer hospitalId;
	@NotNull
	private final Integer packageId;
	@NotNull
	private final String packageName;
	@NotNull
	private final Integer state;
	@NotNull
	private final Boolean deprecated;
	@NotNull
	private final Integer pay;
	@Nullable
	private final Integer familyId;
	
	public OrderVo(Order order)
	{
		this.id = order.getId();
		this.date = order.getDate();
		this.userId = order.getUser().getId();
		this.hospitalId = order.getHospital().getId();
		this.packageId = order.getPackageField().getId();
		this.packageName = order.getPackageField().getName();
		this.state = order.getState();
		this.deprecated = order.getDeprecated();
		this.pay = order.getPay();
		this.familyId = order.getFamily() == null ? null : order.getFamily().getId();
	}
}