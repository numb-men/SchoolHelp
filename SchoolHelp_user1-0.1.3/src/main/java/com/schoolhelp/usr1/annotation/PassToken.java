package com.schoolhelp.usr1.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PassToken
 * 跳过token验证的注解
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required()  default true;
}
