package com.schoolhelp.usr1.enums;

/**
 * 一个封装响应Result code 和 msg的枚举
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

public enum GlobalResultEnum implements ResultEnum{

    SUCCESS(0, "成功"),
    UNKNOW_ERROR(-1, "未知错误"),
    NOT_LOGIN(-2, "未登录"),
    USER_NOT_FIND(-3, "用户不存在"),
    TOKEN_CHECK_FAILED(-4, "登录凭证校验失败")
    ;
    private Integer code;

    private String msg;

    GlobalResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
