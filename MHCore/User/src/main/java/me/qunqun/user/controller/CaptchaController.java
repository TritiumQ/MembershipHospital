package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.user.exception.OperationExceptionCode;
import me.qunqun.user.manager.CaptchaManager;
import me.qunqun.user.manager.SmsManager;
import me.qunqun.user.util.UserUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "验证码管理", description = "验证码管理API")
@RequestMapping("/api/captcha")
@Slf4j
public class CaptchaController
{
	@Resource
	private CaptchaManager captchaManager;
	@Resource
	private SmsManager smsManager;
	
	@Operation(summary = "短信验证码")
	@RequestMapping("/sms/send")
	public Result<String> sendSmsCaptcha()
	{
		var userId = UserUtil.GetUserId();
		var msg = smsManager.sendCaptcha(userId, userId);
		return Result.success(msg);
	}
	
	@Operation(summary = "验证短信验证码")
	@RequestMapping("/sms/verify")
	public Result<String> verifySmsCaptcha(String code)
	{
		var userId = UserUtil.GetUserId();
		var ok = smsManager.verifyCaptcha(userId, userId, code);
		if (ok)
		{
			return Result.success("验证成功");
		}
		throw new RuntimeException("verifySmsCaptch(): 预期外的错误: 验证码验证失败");
	}
	
	
	
}
