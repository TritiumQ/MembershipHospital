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
public class UserInfoVo extends UserVo implements Serializable
{

	private String token;
	
	public UserInfoVo(User user, String token)
	{
		super(user);
		this.token = token;
	}
}