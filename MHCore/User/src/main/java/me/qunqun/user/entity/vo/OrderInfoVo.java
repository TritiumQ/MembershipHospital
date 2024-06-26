package me.qunqun.user.entity.vo;

import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.Family;
import me.qunqun.shared.entity.po.Order;
import org.springframework.lang.Nullable;

@Getter
@ToString
public class OrderInfoVo extends OrderVo
{
	//TODO: add more fields
	private final HospitalVo hospital;
	private final UserVo user;
	private final PackageVo packageField;
	@Nullable
	private final FamilyVoAndDto family;
	
	public OrderInfoVo(Order order)
	{
		super(order);
		this.hospital = new HospitalVo(order.getHospital());
		this.user = new UserVo(order.getUser());
		this.packageField = new PackageVo(order.getPackageField());
		this.family = order.getFamily() == null ? null : new FamilyVoAndDto(order.getFamily());
	}
}
