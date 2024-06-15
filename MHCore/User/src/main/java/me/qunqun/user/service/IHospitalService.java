package me.qunqun.user.service;

import me.qunqun.shared.entity.vo.HospitalVo;

import java.util.List;

public interface IHospitalService
{
	List<HospitalVo> list(Integer state);
	
	HospitalVo get(Integer id);
}
