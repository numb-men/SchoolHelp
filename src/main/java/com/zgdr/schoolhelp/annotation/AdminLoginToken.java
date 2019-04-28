package com.zgdr.schoolhelp.annotation;

import java.lang.annotation.*;

/**
 * AdminLoginToken
 * 注解代表访问控制，需要有管理员权限/role=1
 *
 * @author hengyumo
 * @since 2019/4/28
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AdminLoginToken {
    boolean required() default true;
}
