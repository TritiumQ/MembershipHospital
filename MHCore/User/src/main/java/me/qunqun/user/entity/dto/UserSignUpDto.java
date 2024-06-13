package me.qunqun.user.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户注册 DTO <br>
 * DTO for {@link me.qunqun.shared.entity.po.User}
 */
@AllArgsConstructor
@Data
public class UserSignUpDto implements Serializable
{
	private final String id;
	private final String password;
	private final String realName;
	private final Integer sex;
	private final String idCard;
	private final LocalDate birthday;
	private final Integer type;
}