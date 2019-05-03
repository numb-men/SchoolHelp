package com.zgdr.schoolhelp.enums;

/**
 * 一个封装响应Result code 和 msg的枚举
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/5/2
 */
public enum PostResultEnum implements ResultEnum {

    NOT_FOUND(404,"未找到贴子"),
    NODE(100,"各输入项输入不能为空"),
    MORE_POINTS(101,"积分不足"),
    ERROR_POINTS(5,"积分不能为负"),
    NOT_POINTS(12,"积分为空"),
    NO_DES(6,"描述为空"),
    NOT_COMMENT(7,"请输入评论内容"),

    ;

    private Integer code;

    private String msg;

    PostResultEnum(Integer code, String msg) {
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