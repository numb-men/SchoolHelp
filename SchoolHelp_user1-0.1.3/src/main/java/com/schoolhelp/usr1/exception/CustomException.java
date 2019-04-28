package com.schoolhelp.usr1.exception;

/**
 * CustomException
 * 自定义异常（code，message）
 * 适合message不固定的情况
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/27
 **/
public class CustomException extends  RuntimeException{
    private String msg;
    private Integer code;

    public CustomException(Integer code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
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
}
