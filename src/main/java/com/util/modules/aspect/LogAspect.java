package com.util.modules.aspect;

import com.util.modules.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Order(value = 1)
@Component
public class LogAspect {

    @Around("execution(public * com.util.modules.controller.*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = RequestUtil.getRequest();

        long startAt = System.currentTimeMillis();

        String params = RequestUtil.getRequestArgs(pjp.getArgs());

        log.info("------->REQUEST : {}({}::{}) - {}  -  {}", pjp.getSignature().getDeclaringTypeName(), request.getRequestURI(), RequestUtil.getClientIP(request), request.getMethod(), params);

        Object result = pjp.proceed();

        long endAt = System.currentTimeMillis();

        log.info("------->RESPONSE : {}({}::{}) - {}  -  {}  -  ({}ms)", pjp.getSignature().getDeclaringTypeName(), request.getRequestURI(), RequestUtil.getClientIP(request), request.getMethod(), result, endAt-startAt);

        return result;
    }
}
