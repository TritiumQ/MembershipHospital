package me.qunqun.shared.manager.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 邮件服务管理器
 */
@Service
@Slf4j
public class EmailManager
{
	/**
	 * 发送邮件
	 */
	public void sendEmail(String email, String title, String content)
	{
		// 预留接口
		log.info("发送邮件: to email: {}, title={}, content={}", email, title, content);
	}

}
