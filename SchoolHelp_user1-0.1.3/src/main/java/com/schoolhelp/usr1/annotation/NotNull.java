package com.schoolhelp.usr1.annotation;

import java.lang.annotation.*;

/**
 * NotNull
 * 全局参数非空注解
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface NotNull {
    String value() default "";
}
