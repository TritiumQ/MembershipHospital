package me.qunqun.user.manager;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * SMS服务管理器
 */
@Service
@Slf4j
public class SmsManager
{
	@Resource
	private RedisManager redisManager;
	
	private static final String SMS_CAPTCHA_REDIS_PREFIX = "sms:captcha:";
	
	private static long CAPTCHA_EXPIRE_TIME = 300;
	private static long CAPTCHA_RESEND_TIME = 60;
	
	/**
	 * 发送短信验证码
	 */
	public String sendCaptchaMessage(String id, String phone)
	{
		// 先判断是否在一分钟内已经发送过验证码
		var key = SMS_CAPTCHA_REDIS_PREFIX + id + ":" + phone;
		var expireTime = redisManager.getExpireTime(key);
		if (expireTime > CAPTCHA_EXPIRE_TIME - CAPTCHA_RESEND_TIME)
		{
			return "1分钟内不能重复发送验证码";
		}
		// 生成验证码
		var captcha = generateSmsCaptcha();
		// 保存验证码
		redisManager.setString(key, captcha, CAPTCHA_EXPIRE_TIME);
		// 发送验证码
		sendSms(phone, captcha);
		return "验证码发送成功, 请在5分钟内完成验证";
	}
	
	private void sendSms(String phone, String captcha)
	{
		// 发送短信, 预留接入SMS服务商接口
		log.info("发送短信验证码到手机: {}, 验证码: {}", phone, captcha);
	}
	
	/**
	 * 生成6位数字短信验证码
	 * @return
	 */
	private String generateSmsCaptcha()
	{
		return String.valueOf((int)((Math.random() * 9 + 1) * 100000));
	}
}
