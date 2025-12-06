package com.github.hitzaki.config;

import com.github.hitzaki.common.BusinessException;
import com.github.hitzaki.common.ErrorCode;
import com.github.hitzaki.common.RateLimit;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitInterceptor {
    private final Map<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object intercept(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = joinPoint.getSignature().toLongString();
        RateLimiter rateLimiter = limiters.computeIfAbsent(key, k -> RateLimiter.create(rateLimit.value()));

        if (!rateLimiter.tryAcquire()) {
            throw new BusinessException(ErrorCode.RATE_LIMIT);
        }
        return joinPoint.proceed();
    }
}

