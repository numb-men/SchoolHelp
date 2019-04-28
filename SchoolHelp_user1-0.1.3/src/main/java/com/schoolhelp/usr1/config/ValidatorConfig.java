package com.schoolhelp.usr1.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * ValidatorConfig
 * 注入表单验证器
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/
@Configuration
@EnableAutoConfiguration
public class ValidatorConfig {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }
}
