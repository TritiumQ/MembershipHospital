package me.qunqun.shared.service.impl;

import me.qunqun.shared.service.ITestService;
import org.springframework.stereotype.Service;

@Service
public class TestService implements ITestService
{
	
	@Override
	public String testString()
	{
		return "欢迎来到会员制医院！身体好有奖励，身体差有惩罚噢！";
	}
	
	@Override
	public int testInt()
	{
		return Math.random() > 0.5 ? 114514 : 1919810;
	}
	
	@Override
	public String testString(String str)
	{
		return "你输入的字符串是：" + str;
	}
}
