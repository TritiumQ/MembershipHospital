package me.qunqun.shared.component;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

/**
 * 共享模块自动配置
 */
@AutoConfiguration
@ComponentScan(basePackages = "me.qunqun.shared")
@EnableJpaRepositories(basePackages = "me.qunqun.shared.repo")
@EntityScan(basePackages = "me.qunqun.shared.entity")
public class SharedAutoConfiguration
{

}
