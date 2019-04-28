package com.schoolhelp.usr1.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * UserLoginToken
 * 需要验证用户登录的token注解
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface UserLoginToken {
    boolean required() default true;
}
