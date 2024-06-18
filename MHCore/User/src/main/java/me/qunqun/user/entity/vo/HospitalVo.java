package me.qunqun.user.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import me.qunqun.shared.entity.po.Hospital;

import java.io.Serializable;

/**
 * 医院信息 <br>
 * VO for {@link me.qunqun.shared.entity.po.Hospital}
 */
@AllArgsConstructor
@Getter
@ToString
public class HospitalVo implements Serializable
{
	private final Integer id;
	private final String name;
	private final String picture;
	private final String telephone;
	private final String address;
	private final String businessHours;
	private final String deadline;
	private final String rule;
	private final Integer state;
	
	public HospitalVo(Hospital hospital)
	{
		this.id = hospital.getId();
		this.name = hospital.getName();
		this.picture = hospital.getPicture();
		this.telephone = hospital.getTelephone();
		this.address = hospital.getAddress();
		this.businessHours = hospital.getBusinessHours();
		this.deadline = hospital.getDeadline();
		this.rule = hospital.getRule();
		this.state = hospital.getState();
	}
}