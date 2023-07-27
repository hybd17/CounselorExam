package com.example.exam2.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimerAspect {
    @Around("execution(* com.example.exam2.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        long start = System.currentTimeMillis();
        Object res = pjp.proceed();
        long end = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getMethod().getName();
        System.out.println(methodName+"方法耗时:"+(end-start)+"ms");
        return res;
    }
}
