package me.qunqun.user.manager;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.stereotype.Service;

/**
 * 验证码管理器
 */
@Service
public class CaptchaManager
{
	
	public void createCaptcha()
	{
		LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
		lineCaptcha.createCode();
		lineCaptcha.write("d:/line.png");
	}
}
