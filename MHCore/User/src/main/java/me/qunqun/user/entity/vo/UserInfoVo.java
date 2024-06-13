package me.qunqun.user.entity.vo;

import lombok.Getter;
import me.qunqun.shared.entity.po.User;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户信息VO <br>
 * DTO for {@link me.qunqun.shared.entity.po.User}
 */
@Getter
public class UserInfoVo implements Serializable
{
	private final String id;
	private final String realName;
	private final Integer sex;
	private final String idCard;
	private final LocalDate birthday;
	private final Integer type;
	
	private final String token;
	
	public UserInfoVo(User user, String token)
	{
		this.id = user.getId();
		this.realName = user.getRealName();
		this.sex = user.getSex();
		this.idCard = user.getIdCard();
		this.birthday = user.getBirthday();
		this.type = user.getType();
		this.token = token;
	}
}