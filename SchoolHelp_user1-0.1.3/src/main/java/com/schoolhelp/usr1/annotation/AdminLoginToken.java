package com.schoolhelp.usr1.annotation;

import java.lang.annotation.*;

/**
 * AdminLoginToken
 * 注解代表访问控制，需要有管理员权限/role=1
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AdminLoginToken {
    boolean required() default true;
}
