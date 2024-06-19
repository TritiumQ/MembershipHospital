package me.qunqun.user.manager;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.exception.OperationExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * SMS服务管理器
 */
@Service
@Slf4j
public class SmsManager
{
	public void sendSms(String phone, String captcha)
	{
		// 发送短信, 预留接入SMS服务商接口
		log.info("发送短信验证码到手机: {}, 验证码: {}", phone, captcha);
	}
	
}
