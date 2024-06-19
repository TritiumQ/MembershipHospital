package me.qunqun.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务配置
 */
@Component
public class CrontabConfig
{
	/*
	* cron表达式参数：
	* 秒（0-59） 分钟（0-59） 小时（0-23） 天（1-31） 月（1-12） 星期（0-7，0和7表示周日）
	* '/' 前面的参数表示开始时间，后面的参数表示间隔时间
	* '*' 表示任意时间
	* '?' 表示不指定
	* '-' 表示范围
	* ',' 表示多个值
	* 'L' 表示最后一天
	* 'W' 表示工作日
	* 例子：0 0 12 * * ? 表示每天中午12点执行
	* */
	
}
