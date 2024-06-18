package me.qunqun.user.service.impl;

import jakarta.annotation.Resource;
import me.qunqun.user.entity.vo.HospitalVo;
import me.qunqun.shared.repo.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService implements me.qunqun.user.service.IHospitalService
{
	@Resource
	private HospitalRepository hospitalRepository;
	
	@Override
	public List<HospitalVo> list(Integer state)
	{
		if(state == null)
		{
			return hospitalRepository.findAll().stream().map(hospital -> new HospitalVo(hospital)).toList();
		}
		else
		{
			return hospitalRepository.findAll((root, query, cb) ->
			{
				var predicates = cb.conjunction();
				predicates = cb.and(predicates, cb.equal(root.get("state"), state));
				query.where(predicates);
				return query.getRestriction();
			}).stream().map(hospital -> new HospitalVo(hospital)).toList();
		}
	}
	
	@Override
	public HospitalVo get(Integer id)
	{
		return new HospitalVo(hospitalRepository.findById(id).orElse(null));
	}
}
