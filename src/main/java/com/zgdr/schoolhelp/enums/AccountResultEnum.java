package com.zgdr.schoolhelp.enums;

/**
 * AccountResultEnum
 * TODO
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/26
 */
public enum AccountResultEnum implements ResultEnum{
    PHONE_INVALIDED(-1, "手机号不合法"),
    PASSWORD_INVALIDED(-2, "密码不合法"),
    HAS_REGISTERED(-3, "该手机号已注册"),
    OLD_PASSWORD_ERROR(-4, "旧密码不正确"),
    NOT_USER(-5, "用户不存在"),
    PASSWORD_ERROR(-6, "密码错误")
    ;
    private Integer code;
    private String msg;

    AccountResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
