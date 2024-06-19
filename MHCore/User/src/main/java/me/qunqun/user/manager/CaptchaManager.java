package me.qunqun.user.manager;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import jakarta.annotation.Resource;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.exception.OperationExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 验证码管理器
 */
@Service
public class CaptchaManager
{
	private static final Logger log = LoggerFactory.getLogger(CaptchaManager.class);
	@Resource
	private RedisManager redisManager;
	@Resource
	private SmsManager smsManager;
	
	private static final String MESSAGE_CAPTCHA_REDIS_PREFIX = "message:captcha:";
	private static final String IMAGE_CAPTCHA_REDIS_PREFIX = "image:captcha:";
	
	private static long CAPTCHA_EXPIRE_TIME = 300;
	
	private static long MESSAGE_CAPTCHA_RESEND_TIME = 60;
	
	/**
	 * 发送短信验证码
	 */
	public String sendMessageCaptcha(String id, String phone)
	{
		Assert.notNull(id, "SmsManager.sendSmsCaptcha(): id不能为空");
		Assert.notNull(phone, "SmsManager.sendSmsCaptcha(): phone不能为空");
		// 先判断是否在一分钟内已经发送过验证码
		var key = MESSAGE_CAPTCHA_REDIS_PREFIX + id + ":" + phone;
		var expireTime = redisManager.getExpireTime(key);
		if (expireTime > CAPTCHA_EXPIRE_TIME - MESSAGE_CAPTCHA_RESEND_TIME)
		{
			return "1分钟内不能重复发送验证码";
		}
		// 生成验证码
		var captcha = generateMessageCaptcha();
		// 保存验证码
		redisManager.setString(key, captcha, CAPTCHA_EXPIRE_TIME);
		// 发送验证码
		smsManager.sendCaptcha(phone, captcha);
		return "验证码发送成功, 请在5分钟内完成验证";
	}
	
	/**
	 * 验证短信验证码
	 */
	public boolean verifyMessageCaptcha(String id, String phone, String captcha)
	{
		Assert.notNull(id, "SmsManager.verifyCaptcha(): id不能为空");
		Assert.notNull(phone, "SmsManager.verifyCaptcha(): phone不能为空");
		var key = MESSAGE_CAPTCHA_REDIS_PREFIX + id + ":" + phone;
		var redisCode = redisManager.getString(key);
		redisManager.deleteKey(key);
		return verify(captcha, key, redisCode);
	}
	
	/**
	 * 生成6位数字短信验证码
	 * @return
	 */
	private String generateMessageCaptcha()
	{
		// TODO: 更改生成算法
		return String.valueOf((int)((Math.random() * 9 + 1) * 100000));
	}
	
	/**
	 * 生成图片验证码, 返回文件的BASE64编码
	 */
	public String generateImageCaptcha(String userId)
	{
		Assert.notNull(userId, "CaptchaManager.generateImageCaptcha(): userId不能为空");
		LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
		var code = lineCaptcha.getCode();
		log.info("生成图片验证码: userId: {}, code: {}", userId, code);
		var base64 = lineCaptcha.getImageBase64();
		var key = IMAGE_CAPTCHA_REDIS_PREFIX + userId;
		redisManager.setString(key, code, CAPTCHA_EXPIRE_TIME);
		return base64;
	}
	
	
	/**
	 * 验证图片验证码
	 */
	public boolean verifyImageCaptcha(String userId, String code)
	{
		Assert.notNull(userId, "CaptchaManager.verifyImageCaptcha(): userId不能为空");
		var key = IMAGE_CAPTCHA_REDIS_PREFIX + userId;
		var redisCode = redisManager.getString(key);
		redisManager.deleteKey(key);
		return verify(code, key, redisCode);
	}
	
	/**
	 * 验证验证码
	 */
	private boolean verify(String code, String key, String redisCode)
	{
		if (redisCode == null)
		{
			throw new CustomException(OperationExceptionCode.CAPTCHA_NOT_FOUND);
		}
		if (!redisCode.equals(code))
		{
			throw new CustomException(OperationExceptionCode.CAPTCHA_ERROR);
		}
		return true;
	}
}
