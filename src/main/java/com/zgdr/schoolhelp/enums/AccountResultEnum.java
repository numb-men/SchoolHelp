package com.zgdr.schoolhelp.enums;

/**
 * AccountResultEnum
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/26
 */
public enum AccountResultEnum implements ResultEnum{
    /*枚举*/
    HAS_REGISTERED(-1, "该手机号已注册"),
    NOT_USER(-2, "用户不存在"),
    PASSWORD_ERROR(-3, "密码错误"),
    NOT_GOOD_PASSWORD(-4, "密码应由8-255位数字和字母组成"),
    PASSWORD_EQUAL(-5, "新密码和旧密码相同，不改变")
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
