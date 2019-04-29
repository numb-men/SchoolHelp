package com.zgdr.schoolhelp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Hello World!
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
@Controller
public class HelloWorldController {

    @GetMapping(value = "/")
    public String say() {

        // 使用thymleaf模板引擎
        return "index";
    }
}
