package com.zgdr.schoolhelp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * SpringBootStartApplication
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/19
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(SchoolhelpApplication.class);
    }
}
