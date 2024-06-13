package me.qunqun.user.service.impl;

import jakarta.annotation.Resource;
import me.qunqun.shared.annotation.LogMethod;
import me.qunqun.shared.entity.po.User;
import me.qunqun.user.entity.dto.UserSignInDto;
import me.qunqun.shared.exception.OperationException;
import me.qunqun.shared.exception.code.OperationExceptionCode;
import me.qunqun.shared.repo.UserRepository;
import me.qunqun.user.entity.dto.UserSignUpDto;
import me.qunqun.user.entity.vo.UserInfoVo;
import me.qunqun.user.service.ISignService;
import me.qunqun.user.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@LogMethod
public class SignService implements ISignService
{
	@Resource
	private UserRepository userRepository;
	
	@Override
	public UserInfoVo signIn(UserSignInDto dto)
	{
		Assert.notNull(dto, "signIn(): dto 不可为 null");
		Assert.hasText(dto.getId(), "signIn():  id 不可为空");
		Assert.hasText(dto.getPassword(), "signIn():  password 不可为空");
		
		var oUser = userRepository.findById(dto.getId());
		
		if(oUser.isEmpty())
		{
			throw new OperationException(OperationExceptionCode.USER_NOT_FOUND);
		}
		
		var user = oUser.get();
		
		if(UserUtil.CheckPassword(dto.getPassword(), user.getPassword()) == false)
		{
			throw new OperationException(OperationExceptionCode.USER_PASSWORD_ERROR);
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
			throw new OperationException(OperationExceptionCode.USER_ID_EXIST);
		}
		
		var user = User.builder()
				.id(dto.getId())
				.password(UserUtil.EncryptPassword(dto.getPassword()))
				.realName(dto.getRealName())
				.sex(dto.getSex())
				.idCard(dto.getIdCard())
				.birthday(dto.getBirthday())
				.type(dto.getType())
				.build();
		
		userRepository.save(user);
		
		if(userRepository.existsById(dto.getId()) == false)
		{
			throw new OperationException(OperationExceptionCode.USER_NOT_FOUND);
		}
		
		return new UserInfoVo(user, null);
	}
}
