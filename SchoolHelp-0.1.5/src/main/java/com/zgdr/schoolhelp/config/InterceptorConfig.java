package com.zgdr.schoolhelp.config;

import com.zgdr.schoolhelp.interceptor.AuthenticationInterceptor;
import com.zgdr.schoolhelp.interceptor.NotNullInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * InterceptorConfig
 * 配置处理token
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/25
 */
@Configuration
public class InterceptorConfig  implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor());
        registry.addInterceptor(notNullInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public NotNullInterceptor notNullInterceptor() {
        return new NotNullInterceptor();
    }
}
