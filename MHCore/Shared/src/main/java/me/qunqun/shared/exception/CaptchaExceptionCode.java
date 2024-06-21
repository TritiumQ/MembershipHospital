package me.qunqun.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CaptchaExceptionCode implements IExceptionCode
{
	//TODO 验证码相关错误
	CAPTCHA_NOT_FOUND(30001, "验证码不存在"),
	CAPTCHA_ERROR(30002, "验证码错误"),
	CAPTCHA_EXPIRED(30003, "验证码已过期"),
	CAPTCHA_VERIFY_ERROR(30004, "验证码验证错误"),
	CAPTCHA_SEND_ERROR(30005, "验证码发送失败"),
	CAPTCHA_GENERATE_ERROR(30006, "验证码生成失败"),
	CAPTCHA_EMPTY(30007, "验证码为空")
	
	
	;
	private final int code;
	private final String message;
}
