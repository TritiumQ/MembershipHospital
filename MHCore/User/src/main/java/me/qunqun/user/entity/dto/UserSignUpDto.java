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
	private String id;
	private String password;
	private String realName;
	private Integer sex;
	private String idCard;
	private LocalDate birthday;
	private Integer type;
}