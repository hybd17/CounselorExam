package com.example.exam2.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//该注解可以应用于方法上
@Retention(RetentionPolicy.RUNTIME)//该注解在运行时可用
public @interface CheckRole {
    String value();
}