package com.zgdr.schoolhelp.enums;

/**
 * UserResultEnum
 * TODO
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public enum UserResultEnum implements ResultEnum {
    ID_NOT_FOUND(101, "用户ID不存在")
    ;

    private Integer code;

    private String msg;

    UserResultEnum(Integer code, String msg) {
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
