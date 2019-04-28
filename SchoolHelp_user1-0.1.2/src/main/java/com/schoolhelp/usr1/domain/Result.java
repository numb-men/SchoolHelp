package com.schoolhelp.usr1.domain;

import com.schoolhelp.usr1.enums.PostResultEnum;

/**
 * 封装响应
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

public class Result<T> {

    /**  提示信息. */
    private String msg;

    /**  错误码. */
    private Integer code;

    /**  具体的内容. */
    private T data;

    public Result() {
    }

    public Result(PostResultEnum postResultEnum, T data){
        this.msg = postResultEnum.getMsg();
        this.code = postResultEnum.getCode();
        this.data = data;
    }

    public Result(String msg, Integer code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
