package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
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
	
	@Operation(summary = "发送短信验证码")
	@RequestMapping("/sms/send")
	public Result<String> sendSmsCaptch()
	{
		var userId = UserUtil.GetUserId();
		var msg = smsManager.sendCaptchaMessage(userId, userId);
		return Result.success(msg);
	}
	
	
}
