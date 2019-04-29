package com.zgdr.schoolhelp.handle;

import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.exception.CustomException;
import com.zgdr.schoolhelp.exception.ExceptionParent;
import com.zgdr.schoolhelp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * ExceptionHandel
 * 全局异常处理类
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class) //自定义处理异常
    @ResponseBody
    public Result handle(Exception e) throws Exception{
        if (e instanceof ExceptionParent) {
            // 系统内部异常
            ExceptionParent exceptionParent = (ExceptionParent) e;
            return new Result<Object>(exceptionParent.getResultEnum(), null);
        }
        else if(e instanceof CustomException){
            // 系统自定义异常
            CustomException customException = (CustomException)e;
            return Result.error(customException.getCode(), customException.getMsg());
        }
        else if(e instanceof MissingServletRequestParameterException){
            // 缺少参数
            return Result.error(-100, e.getMessage());
        }
        else if(e instanceof ConstraintViolationException){
            // 表单验证不通过
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
            Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
            List<String> msgList = new ArrayList<>();
            while (iterator.hasNext()) {
                ConstraintViolation<?> constraintViolation = iterator.next();
                msgList.add(constraintViolation.getMessage());
            }

            return Result.error(-200, StringUtil.listToString(msgList,'\n'));
        }
        else if(e instanceof BindException){
            // @Valid表单验证不通过
            BindException bindException = (BindException)e;
            List<ObjectError> errors = bindException.getAllErrors();
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError objectError : errors){
                errorMessages.add(objectError.getDefaultMessage());
            }

            return Result.error(-300, StringUtil.listToString(errorMessages, '\n'));
        }
        else if (e instanceof HttpRequestMethodNotSupportedException){
            // 请求方式不支持
            HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException
                    = (HttpRequestMethodNotSupportedException)e;
            return Result.error(-500, httpRequestMethodNotSupportedException.getMessage());
        }
        else {
            logger.error("【系统异常】", e);
            return new Result<Object>(GlobalResultEnum.UNKNOW_ERROR, null);
        }
    }
}
