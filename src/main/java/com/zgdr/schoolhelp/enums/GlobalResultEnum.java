package com.zgdr.schoolhelp.enums;

/**
 * 一个封装响应Result code 和 msg的枚举
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public enum GlobalResultEnum implements ResultEnum {
    /*枚举*/
    SUCCESS(0, "成功"),
    UNKNOW_ERROR(-10, "未知错误"),
    NOT_LOGIN(-11, "未登录"),
    USER_NOT_FIND(-12, "用户不存在"),
    TOKEN_CHECK_FAILED(-13, "登录凭证校验失败"),
    NOT_POWER(-14, "没有权限")
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
