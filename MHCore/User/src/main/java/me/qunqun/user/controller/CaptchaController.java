package me.qunqun.user.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.Result;
import me.qunqun.shared.exception.CustomException;
import me.qunqun.shared.exception.CaptchaExceptionCode;
import me.qunqun.shared.manager.captcha.CaptchaManager;
import me.qunqun.user.util.UserUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@Tag(name = "验证码管理", description = "验证码管理API")
@RequestMapping("/api/captcha")
@Slf4j
public class CaptchaController
{
	@Resource
	private CaptchaManager captchaManager;
	
	@Operation(summary = "短信验证码")
	@GetMapping("/sms/send")
	public Result<String> sendSmsCaptcha()
	{
		var userId = UserUtil.GetUserId();
		var msg = captchaManager.sendMessageCaptcha(userId, userId);
		return Result.success(msg);
	}
	
	@Operation(summary = "验证短信验证码")
	@PostMapping("/sms/verify")
	public Result<String> verifySmsCaptcha(String code)
	{
		var userId = UserUtil.GetUserId();
		if(code == null || code.isEmpty())
		{
			throw new CustomException(CaptchaExceptionCode.CAPTCHA_EMPTY);
		}
		var ok = captchaManager.verifyMessageCaptcha(userId, userId, code);
		if (ok)
		{
			return Result.success("验证成功");
		}
		throw new RuntimeException("verifySmsCaptch(): 预期外的错误: 验证码验证失败");
	}
	
	@Operation(summary = "生成图片验证码", description = "生成图片验证码, 返回Base64编码的图片数据")
	@GetMapping("/image")
	@SaIgnore
	public Result<Map<String, String>> createImageCaptcha()
	{
		var uuid = UUID.randomUUID().toString();
		var base64 = captchaManager.generateImageCaptcha(uuid);
		if(base64 == null)
		{
			throw new RuntimeException("createImageCaptcha(): 预期外的错误: 验证码图片生成失败");
		}
		return Result.success(Map.of("uuid", uuid, "image", base64));
	}
	
	@Operation(summary = "验证图片验证码")
	@PostMapping("/image/verify")
	@SaIgnore
	public Result<String> verifyImageCaptcha(@RequestBody Map<String, String> data)
	{
		var uuid = data.get("uuid");
		var code = data.get("code");
		log.info("uuid: {}, code: {}", uuid, code);
		var ok = captchaManager.verifyImageCaptcha(uuid, code);
		if (ok)
		{
			return Result.success("验证成功");
		}
		throw new RuntimeException("verifyImageCaptcha(): 预期外的错误: 验证码验证失败");
	}
	
	
	
}
