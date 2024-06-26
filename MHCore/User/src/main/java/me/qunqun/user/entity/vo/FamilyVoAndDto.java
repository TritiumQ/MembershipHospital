package me.qunqun.user.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.qunqun.shared.entity.po.Family;

@Getter
@Setter
@NoArgsConstructor
public class FamilyVoAndDto
{
	private Integer id;
	
	private String  userId;
	
	private String name;
	
	private String idCard;
	
	private String birthday;
	
	private String phone;
	
	private Integer sex;
	
	public FamilyVoAndDto(Family family)
	{
		this.id = family.getId();
		this.userId = family.getUserId();
		this.name = family.getName();
		this.idCard = family.getIdCard();
		this.birthday = family.getBirthday();
		this.phone = family.getPhone();
		this.sex = family.getSex();
	}
}
