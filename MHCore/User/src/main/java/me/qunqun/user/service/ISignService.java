package me.qunqun.user.service;

import me.qunqun.user.entity.dto.UserSignInDto;
import me.qunqun.user.entity.dto.UserSignUpDto;
import me.qunqun.user.entity.vo.UserInfoVo;

public interface ISignService
{
	UserInfoVo signIn(UserSignInDto dto);
	
	UserInfoVo signUp(UserSignUpDto dto);
}
