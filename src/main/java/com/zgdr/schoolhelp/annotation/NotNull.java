package com.zgdr.schoolhelp.annotation;

import java.lang.annotation.*;

/**
 * NotNull
 * 全局参数非空注解
 *
 * @author hengyumo
 * @since 2019/4/28
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface NotNull {
   String value() default "";
}
