package com.zgdr.schoolhelp.domain;

import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.ResultEnum;

/**
 * 一个封装好的响应Result
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public class Result<T> {
    private String msg;
    private Integer code;
    private T data;

    public Result(ResultEnum resultEnum, T data) {
        this.msg = resultEnum.getMsg();
        this.code = resultEnum.getCode();
        this.data = data;
    }

    public Result(T data){
        this(GlobalResultEnum.SUCCESS, data);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static Result success(Object data){
        return new Result<Object>(data);
    }

    public static Result error(ResultEnum resultEnum){
        return new Result<Object>(resultEnum, null);
    }
}
