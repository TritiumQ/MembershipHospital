package me.qunqun.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories(basePackages = "me.qunqun.user.entity.repo")
@EntityScan(basePackages = "me.qunqun.user.entity.po")
@EnableScheduling
public class UserApplication
{
	
	public static void main(String[] args)
	{
		var app = SpringApplication.run(UserApplication.class, args);
		var host = "http://localhost:" + app.getEnvironment().getProperty("server.port");
		log.info("\n----------------------------------------------------------\nAPI文档地址:  {}{}\nOpen API 文档:  {}{}\n----------------------------------------------------------\n", host, app.getEnvironment().getProperty("springdoc.swagger-ui.path"), host, app.getEnvironment().getProperty("springdoc.api-docs.path"));
	}
	
}
