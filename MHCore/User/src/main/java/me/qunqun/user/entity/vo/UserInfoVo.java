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
	private String id;
	private String realName;
	private Integer sex;
	private String idCard;
	private LocalDate birthday;
	private Integer type;
	
	private String token;
	
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