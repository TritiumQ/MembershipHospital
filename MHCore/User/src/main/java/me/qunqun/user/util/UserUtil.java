package me.qunqun.user.util;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Nullable;
import me.qunqun.shared.entity.po.User;
import org.springframework.util.Assert;

public class UserUtil
{
	private UserUtil(){}
	/**
	 * 设置用户登录状态
	 * @param user {@link User} 用户尸体信息
	 * @return Jwt token
	 */
	public static String SetUser(User user)
	{
		Assert.notNull(user, "SetUser(): user 不可为 null");
		// 使用id作为登录凭证
		StpUtil.login(user.getId(), SaLoginConfig.setExtra("user", user));
		return StpUtil.getTokenValue();
	}
	
	/**
	 * 获取用户实体信息
	 */
	public static User GetUser()
	{
		return (User)StpUtil.getExtra("user");
	}
	
	/**
	 * 获取用户id
	 */
	public static String GetUserId()
	{
		return StpUtil.getLoginIdAsString();
	}
	
	
	/**
	 * 检查密码是否正确
	 */
	public static boolean CheckPassword(String passwordIn, String passwordDb)
	{
		// 这里可以调整加密算法以及加密规则
		return passwordIn.equals(passwordDb);
	}
	
	
	/**
	 * 加密密码
	 */
	public static String EncryptPassword(String password)
	{
		// 这里可以调整加密算法以及加密规则
		return password;
	}
}
