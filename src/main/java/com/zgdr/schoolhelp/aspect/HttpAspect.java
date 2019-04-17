package com.zgdr.schoolhelp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用AOP对Http响应流程统一处理
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    /**
     * 一个通用的切面
     * com.zgdr.schoolhelp.controller.*.*(..) 表示：
     * controller包下的所有类的所有方法
     *
     * @author hengyumo
     * @since 2019/4/17
     */
    @Pointcut("execution(public * com.zgdr.schoolhelp.controller.*.*(..))")
    public void log() { }


    /**
     * Before代表之前
     * 指定拦截的方法 ..代表任何参数
     * com.example.demo.controller.GirlController.*(..)拦截所有方法
     *
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param joinPoint 切入点
     */
    @Before("execution(public * com.zgdr.schoolhelp.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("Not allow!");
        logger.info("Not allow!"); //使用spring自带的日志管理

        //记录请求的内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        logger.info("url={}", request.getRequestURL());

        //method
        logger.info("method={}", request.getMethod());

        //ip
        logger.info("ip={}", request.getRemoteAddr());

        //类方法
        logger.info("class_mathod={}", joinPoint.getSignature().getDeclaringTypeName() + "." +
                joinPoint.getSignature().getName());

        //参数
        logger.info("args={}", joinPoint.getArgs());

    }


    /**
     * 当Controller执行完后执行
     * @author hengyumo
     * @since 2019/4/17
     */
    @After("log()")
    public void doAfter() {
        System.out.println("Do after");
        logger.info("Do after");
    }



    /**
     * 当Controller请求返回时执行，打印响应的数据
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param object 响应数据
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object){
        if (object != null){
            logger.info("response={}", object.toString());
        }
    }
}
