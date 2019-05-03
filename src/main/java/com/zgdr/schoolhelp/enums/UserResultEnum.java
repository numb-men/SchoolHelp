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
    REPEAT_COLLECT(-7, "不能重复收藏帖子"),
    CANT_ATTENTION_YOUSELF(-11, "不能关注自己"),
    CANT_SEND_SELF(-21, "不能给自己发信息"),
    HANAV_REPORT(-31, "该帖子已经举报过"),
    CAN_FAND_POST(-41,"帖子没有找到"),
    USER_NOT_FIND(-51,"用户不存在"),
    PASSWORD_IS_JUST_NUM(-61,"密码不能为纯数字"),
    PASSWOR_IS_JUST_ZM(-71,"密码不能为纯字母"),
    MESSAGE_CANT_NULL(-81,"消息不能为空"),
    NAME_NULL(-91,"姓名不能为空"),
    SCHOOLNAME_NULL(-101,"学号不能为空"),
    CHOSE_SCHOOL(-111,"请填写学校"),
    SCHOOL_NULL(-121,"学校不存在"),
    CHOSE_COLLEGE(-131,"请选择学院"),
    COLLEGE_NULL(-141,"学院不存在"),
    CHOSE_MAJOR(-151,"请选择专业"),
    MAJOR_NULL(-161,"专业不存在"),
    IDCARD_ERROR(-171,"身份证信息错误"),
    PASSWORD_MIN_MAX(-181,"密码的长度应在8-255之间")
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
