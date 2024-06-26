package me.qunqun.user.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import me.qunqun.shared.entity.po.Family;

@Getter
public class FamilyVo
{
	private Integer id;
	
	private String  userId;
	
	private String name;
	
	private String idCard;
	
	private String birthday;
	
	private String phone;
	
	public FamilyVo(Family family)
	{
		this.id = family.getId();
		this.userId = family.getUserId();
		this.name = family.getName();
		this.idCard = family.getIdCard();
		this.birthday = family.getBirthday();
		this.phone = family.getPhone();
	}
}
