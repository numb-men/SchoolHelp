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
    /* 请求的反馈不存在 */
    UNEXIST_FEEDBACK(-6,"该意见反馈不存在"),

    /* 用户反馈失败 */
    FAIL_ADDFEEDBACK(-7,"添加反馈失败"),

    /* 只有管理员才有的操作 */
    POWER_LESS(-8,"权限不够"),

    /* 反馈的内容不满足格式 */
    INVALID_FEEDBACK(-9,"反馈内容应在10--255之间");

   /* 错误码 */
    private Integer code;

    /* 错误描述 */
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
