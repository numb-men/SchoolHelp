package com.zgdr.schoolhelp.interceptor;

import com.alibaba.fastjson.JSON;
import com.zgdr.schoolhelp.annotation.NotNull;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.exception.CustomException;
import com.zgdr.schoolhelp.exception.GlobalException;
import com.zgdr.schoolhelp.utils.StringUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * NotNullClass
 * 实现@NotNull拦截器
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/28
 */
public class NotNullInterceptor implements HandlerInterceptor {

    // 在请求处理之前进行调用（Controller方法调用之前）
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(o instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) o;
        Method method = handlerMethod.getMethod();

        // 是否有NotNull注解
        if (method.getAnnotation(NotNull.class) != null) {
            NotNull notNullAnnotation = method.getAnnotation(NotNull.class);
            String value = notNullAnnotation.value();
            String[] params = value.split(",");


            List<String> errorMessages = new ArrayList<>();
            for (String param : params){
                if (httpServletRequest.getParameter(param) == ""){
                    errorMessages.add(param + "不能为空");
                    System.out.println(param);
                }
            }
            if (errorMessages.size() > 0){
                // 抛出异常
                throw new CustomException(-400, StringUtil.listToString(errorMessages, '\n'));
            }
            return true;
        }else{
            return true;
        }
    }

    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}
