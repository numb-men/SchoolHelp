package com.zgdr.schoolhelp.enums;

/**
 * 反馈的异常信息
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/25
 */
public enum FeedbackEnum implements ResultEnum {
    UNEXIST_FEEDBACK(-1,"该意见反馈不存在"),
    FAIL_ADDFEEDBACK(-2,"添加反馈失败"),
    POWER_LESS(-3,"权限不够");
    private Integer code;

    private  String msg;

    FeedbackEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
