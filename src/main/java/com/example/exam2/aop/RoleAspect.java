package com.example.exam2.aop;

import cn.dev33.satoken.stp.StpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class RoleAspect {
    @Around("@annotation(checkRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, CheckRole checkRole) throws Throwable {
        // 获取当前用户的角色信息
        String role = getCurrentUserRole().get(0);

        // 获取注解中指定的角色名称
        String requiredRole = checkRole.value();

        // 验证用户是否具有角色
        if (!role.equals(requiredRole)) {
            throw new RuntimeException("Access denied");
        }

        // 执行原始方法
        return joinPoint.proceed();
    }

    private List<String> getCurrentUserRole() {
        return StpUtil.getRoleList();
    }
}
