package me.qunqun.user.entity.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import me.qunqun.shared.entity.po.User;

import java.time.LocalDate;

@Getter
public class UserVo
{
	private String id;
	private String realName;
	private Integer sex;
	private String idCard;
	private LocalDate birthday;
	private Integer type;
	
	public UserVo(User user)
	{
		this.id = user.getId();
		this.realName = user.getRealName();
		this.sex = user.getSex();
		this.idCard = user.getIdCard();
		this.birthday = user.getBirthday();
		this.type = user.getType();
	}
}
