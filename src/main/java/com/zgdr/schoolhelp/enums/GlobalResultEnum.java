package com.zgdr.schoolhelp.enums;

/**
 * 一个封装响应Result code 和 msg的枚举
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public enum GlobalResultEnum implements ResultEnum {
    UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功")
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
