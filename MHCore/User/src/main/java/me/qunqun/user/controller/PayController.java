package me.qunqun.user.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.manager.pay.PayManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/pay")
@SaIgnore
public class PayController
{
	@Resource
	private PayManager payManager;
	
	
	@GetMapping("/start")
	public String startPay()
	{
		try
		{
			return payManager.testPay();
		}
		catch (com.alipay.api.AlipayApiException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	
}
