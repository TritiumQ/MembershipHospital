package me.qunqun.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.User;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.entity.dto.UserSignInDto;
import me.qunqun.user.entity.repo.UserRepo;
import me.qunqun.user.exception.OperationExceptionCode;
import me.qunqun.user.entity.dto.UserSignUpDto;
import me.qunqun.user.entity.vo.UserInfoVo;
import me.qunqun.user.service.IUserService;
import me.qunqun.user.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
//@LogMethod
@Slf4j
public class UserService implements IUserService
{
	@Resource
	private UserRepo userRepository;
	
	@Override
	public UserInfoVo signIn(UserSignInDto dto)
	{
		Assert.notNull(dto, "signIn(): dto 不可为 null");
		Assert.hasText(dto.getId(), "signIn():  id 不可为空");
		Assert.hasText(dto.getPassword(), "signIn():  password 不可为空");
		
		var oUser = userRepository.findById(dto.getId());
		
		if(oUser.isEmpty())
		{
			throw new CustomException(OperationExceptionCode.USER_NOT_FOUND);
		}
		
		var user = oUser.get();
		
		if(UserUtil.CheckPassword(dto.getPassword(), user.getPassword()) == false)
		{
			throw new CustomException(OperationExceptionCode.USER_PASSWORD_ERROR);
		}
		
		var token = UserUtil.SetUser(user);
		
		return new UserInfoVo(user, token);
	}
	
	@Override
	public UserInfoVo signUp(UserSignUpDto dto)
	{
		Assert.notNull(dto, "signIn(): dto 不可为 null");
		Assert.hasText(dto.getId(), "signIn():  id 不可为空");
		Assert.hasText(dto.getPassword(), "signIn():  password 不可为空");
		Assert.hasText(dto.getRealName(), "signIn():  realName 不可为空");
		Assert.notNull(dto.getSex(),"signIn(): sex 不可为空");
		Assert.hasText(dto.getIdCard(), "signIn():  idCard 不可为空");
		Assert.notNull(dto.getBirthday(), "signIn():  birthday 不可为空");
		Assert.notNull(dto.getType(), "signIn():  type 不可为空");
		
		if(userRepository.existsById(dto.getId()) == true)
		{
			throw new CustomException(OperationExceptionCode.USER_ID_EXIST);
		}
		
		var user = new User();
		user.setId(dto.getId());
		user.setPassword(UserUtil.EncryptPassword(dto.getPassword()));
		user.setRealName(dto.getRealName());
		user.setSex(dto.getSex());
		user.setIdCard(dto.getIdCard());
		user.setBirthday(dto.getBirthday());
		user.setType(dto.getType());
		
		userRepository.save(user);
		
		if(userRepository.existsById(dto.getId()) == false)
		{
			throw new CustomException(OperationExceptionCode.USER_NOT_FOUND);
		}
		
		return new UserInfoVo(user, null);
	}
	
	@Override
	public UserInfoVo info()
	{
		StpUtil.checkLogin();
		var id = UserUtil.GetUserId();
		var oUser = userRepository.findById(id);
		if(oUser.isEmpty())
		{
			throw new CustomException(OperationExceptionCode.USER_NOT_FOUND);
		}
		var user = oUser.get();
		return new UserInfoVo(user, null);
	}
	
	@Override
	public UserInfoVo refresh()
	{
		StpUtil.checkLogin();
		var id = UserUtil.GetUserId();
		var oUser = userRepository.findById(id);
		if(oUser.isEmpty())
		{
			throw new CustomException(OperationExceptionCode.USER_NOT_FOUND);
		}
		var user = oUser.get();
		var token = UserUtil.SetUser(user);
		return new UserInfoVo(user, token);
	}
}
