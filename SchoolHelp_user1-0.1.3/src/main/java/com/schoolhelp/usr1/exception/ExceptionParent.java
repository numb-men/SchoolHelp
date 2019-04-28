package com.schoolhelp.usr1.exception;

import com.schoolhelp.usr1.domain.Result;
import com.schoolhelp.usr1.enums.ResultEnum;

/**
 * ExceptionParent
 * 异常父类，封装了各种方法
 * 派生出各种子异常
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

public class ExceptionParent extends RuntimeException{
    private ResultEnum resultEnum;
    private Integer code;

    public ExceptionParent(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
        this.code = resultEnum.getCode();
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
