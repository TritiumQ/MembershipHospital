package me.qunqun.user.entity.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 用户登录 DTO <br>
 * DTO for {@link me.qunqun.shared.entity.po.User}
 */
@AllArgsConstructor
@Data
public class UserSignInDto implements Serializable
{
	private String id;
	private String password;
}