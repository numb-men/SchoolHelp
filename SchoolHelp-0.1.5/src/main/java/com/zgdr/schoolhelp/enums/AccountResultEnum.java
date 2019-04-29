package com.zgdr.schoolhelp.enums;

/**
 * AccountResultEnum
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/26
 */
public enum AccountResultEnum implements ResultEnum{
    HAS_REGISTERED(-1, "该手机号已注册"),
    NOT_USER(-2, "用户不存在"),
    PASSWORD_ERROR(-3, "密码错误")
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
