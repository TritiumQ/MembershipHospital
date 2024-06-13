package me.qunqun.shared.annotation;

import java.lang.annotation.*;

/**
 * 当方法被调用时，输出日志<br>
 * 配置在方法上时，只对该方法有效<br>
 * 配置在类上时，对该类的所有方法有效<br>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogMethod
{
}
