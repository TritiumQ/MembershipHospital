package me.qunqun.doctor.aspect;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.annotation.RedisCacheEvict;
import me.qunqun.doctor.annotation.RedisCacheable;
import me.qunqun.shared.manager.redis.RedisManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Autowired
    private RedisManager redisManager;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @Around("@annotation(me.qunqun.doctor.annotation.RedisCacheable)")
    public Object cacheable(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Cacheable aspect invoked");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisCacheable redisCacheable = method.getAnnotation(RedisCacheable.class);
        String keyPrefix = redisCacheable.keyPrefix();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();

        String key = generateKey(keyPrefix, className, methodName, args);
        log.info("Generated key: {}", key);

        // Try to get from cache
        try {
            Object cachedResult = redisManager.getObject(key);
//            log.info("Cache get result: {}", cachedResult);
            if (cachedResult != null) {
                log.info("Cache hit for key: {}", key);
                return cachedResult;
            }
        } catch (Exception e) {
            log.error("Error occurred while getting cache: {}", e.getMessage());
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
            redisManager.setObject(key, result, null);
            log.info("Cache put for key: {}", key);
        } catch (Throwable e) {
            log.error("Error occurred while invoking method: {}", e.getMessage());
            throw e;
        }
        return result;
    }



    @Around("@annotation(me.qunqun.doctor.annotation.RedisCacheEvict)")
    public Object cacheEvict(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisCacheEvict redisCacheEvict = method.getAnnotation(RedisCacheEvict.class);
        String keyPrefix = redisCacheEvict.keyPrefix();

        // Proceed with the original method call
        Object result = joinPoint.proceed();

        // First delete
        deleteCacheKeys(keyPrefix);

        // Schedule the second delete
        scheduler.schedule(() -> deleteCacheKeys(keyPrefix), 2, TimeUnit.SECONDS);

        return result;
    }

    private void deleteCacheKeys(String keyPrefix) {
        redisManager.deleteKeys(keyPrefix);
        log.info("Cache evicted for keyPrefix: {}", keyPrefix + "*");
    }

    private String generateKey(String keyPrefix, String className, String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder(keyPrefix);
        sb.append(":").append(className);
        sb.append(":").append(methodName);
        sb.append(":");

        // 使用参数实体的哈希值
        for (Object arg : args) {
            sb.append(arg.hashCode()).append(":");
        }

        // 移除最后一个多余的冒号
        if (sb.charAt(sb.length() - 1) == ':') {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

}
