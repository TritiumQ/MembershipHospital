package me.qunqun.user.service.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.Family;
import me.qunqun.shared.exception.CrudExceptionCode;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.entity.repo.FamilyRepo;
import me.qunqun.user.entity.repo.UserRepo;
import me.qunqun.user.entity.vo.FamilyVoAndDto;
import me.qunqun.user.exception.OperationExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Slf4j
public class FamilyService
{
	@Resource
	private FamilyRepo familyRepo;
	
	@Resource
	private UserRepo userRepo;
	
	@Resource
	private JPAQueryFactory jpaQueryFactory;
	
	
	public List<FamilyVoAndDto> list(String userId)
	{
		Assert.notNull(userId, "userId不能为空");
		var user = userRepo.findById(userId).orElseThrow(() -> new CustomException(OperationExceptionCode.USER_NOT_FOUND));
		var vos = user.getFamilies().stream().map(FamilyVoAndDto::new).toList();
		return vos;
	}
	
	
	public FamilyVoAndDto add(FamilyVoAndDto dto)
	{
		var family = new Family();
		family.setUserId(dto.getUserId())
			.setName(dto.getName())
			.setIdCard(dto.getIdCard())
			.setBirthday(dto.getBirthday())
			.setPhone(dto.getPhone())
				.setSex(dto.getSex());
		familyRepo.save(family);
		if(family.getId() == null)
		{
			throw new CustomException(CrudExceptionCode.ADD_ERROR);
		}
		return new FamilyVoAndDto(family);
	}
}
