package me.qunqun.user.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * SMS服务管理器
 */
@Service
@Slf4j
public class SmsManager
{
	public void sendCaptcha(String phone, String captcha)
	{
		// 发送短信, 预留接入SMS服务商接口
		log.info("发送短信验证码到手机: {}, 验证码: {}", phone, captcha);
	}
	
	public void sendMessage(String phone, String message)
	{
		// 发送短信, 预留接入SMS服务商接口
		log.info("发送短信到手机: {}, 内容: {}", phone, message);
	}
	
}
