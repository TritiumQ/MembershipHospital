package me.qunqun.shared.annotation;

import me.qunqun.shared.config.ApiDocConfig;
import me.qunqun.shared.config.SaTokenLog;
import me.qunqun.shared.config.WebSharedConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 统一导入共享配置类
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy. RUNTIME)
@Documented
@Import({
		ApiDocConfig.class,
		WebSharedConfig.class,
		SaTokenLog.class
})
public @interface ImportSharedConfig
{
}
