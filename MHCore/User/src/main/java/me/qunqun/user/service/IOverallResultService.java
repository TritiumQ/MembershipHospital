package me.qunqun.user.service;

import me.qunqun.user.entity.vo.OverallResultVo;

import java.util.List;

public interface IOverallResultService
{
	/**
	 * 获取体检总结列表
	 *
	 * @param orderId 订单编号
	 * @return 体检总结列表
	 */
	List<OverallResultVo> list(Integer orderId);
}
