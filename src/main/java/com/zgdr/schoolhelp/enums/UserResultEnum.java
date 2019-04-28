package com.zgdr.schoolhelp.enums;

/**
 * UserResultEnum
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public enum UserResultEnum implements ResultEnum {
    ID_NOT_FOUND(-1, "用户不存在"),
    NO_POWER(-2, "没有权限"),
    CEN_NOT_DELETE_SELF(-3, "不能删除自身"),
    POINTS_NOT_ENOUGH(-4, "积分不足"),
    COLLLECT_ID_NOT_FOUND(-5, "该收藏不存在"),
    POST_NOT_FOUND(-6, "帖子未找到"),
    REPEAT_COLLECT(-7, "不能重复收藏帖子")
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
