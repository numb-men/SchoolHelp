package com.zgdr.schoolhelp.enums;

/**
 * 一个封装响应Result code 和 msg的枚举
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/5/2
 */
public enum PostResultEnum implements ResultEnum {

    /* 根据id没找到帖子 */
    NOT_FOUND(-404,"未找到贴子"),

    /* 帖子的信息不足 */
    NODE(-100,"各输入项输入不能为空"),

    /* 用户积分不够帖子积分 */
    MORE_POINTS(-101,"积分不足"),

    /* 用户输入的积分为负数 */
    ERROR_POINTS(-18,"积分不能为负"),

    /* 用户没有输入积分 */
    NOT_POINTS(-19,"积分为空"),

    /* 帖子内容为空 */
    NO_DES(-20,"描述为空"),

    /* 评论内容为空 */
    NOT_COMMENT(-21,"请输入评论内容"),

    /* 发帖图片多余九张 */
    TO_MUCH_IMAGE(-54,"最多只能添加9张图片"),

    REPEAT_REPORT(-56,"你已经举报过该帖子"),

    NOT_FOUND_COMMENT(-57,"该评论已不存在"),

    NOT_POST_MASTER(-58, "非帖主不能结贴")
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