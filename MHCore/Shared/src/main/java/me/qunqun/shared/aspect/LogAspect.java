package me.qunqun.shared.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect
{
	@Pointcut("@annotation(me.qunqun.shared.annotation.LogMethod) || @within(me.qunqun.shared.annotation.LogMethod)")
	public void pointcut()
	{
	}
	
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable
	{
		var method = pjp.getSignature().toString();
		log.info("Method Invoke: {}", method);
		long beginTime = System.currentTimeMillis();
		Object result = pjp.proceed();
		long time = System.currentTimeMillis() - beginTime;
		log.info("Method Complete: {}", method);
		return result;
	}
}
