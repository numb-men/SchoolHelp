package com.zgdr.schoolhelp.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * PhoneValitor
 * 手机号表单验证注解
 *
 * @author hengyumo
 * @since 2019/4/25
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER) // 仅能用在参数
@Constraint(validatedBy = PhoneValitorClass.class)
public @interface PhoneValitor {
    String message() default "手机号不合法";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
