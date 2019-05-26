package com.zgdr.schoolhelp.enums;

/**
 * UserResultEnum
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public enum UserResultEnum implements ResultEnum {
    /*枚举*/
    ID_NOT_FOUND(-28, "用户不存在"),
    NO_POWER(-29, "没有权限"),
    CEN_NOT_DELETE_SELF(-30, "不能删除自身"),
    POINTS_NOT_ENOUGH(-31, "积分不足"),
    COLLLECT_ID_NOT_FOUND(-32, "该收藏不存在"),
    POST_NOT_FOUND(-33, "帖子未找到"),
    REPEAT_COLLECT(-34, "不能重复收藏帖子"),
    CANT_ATTENTION_YOUSELF(-35, "不能关注自己"),
    CANT_SEND_SELF(-36, "不能给自己发信息"),
    HANAV_REPORT(-37, "该帖子已经举报过"),
    CAN_FAND_POST(-38,"帖子没有找到"),
    USER_NOT_FIND(-39,"用户不存在"),
    PASSWORD_IS_JUST_NUM(-40,"密码不能为纯数字"),
    PASSWOR_IS_JUST_ZM(-41,"密码不能为纯字母"),
    MESSAGE_CANT_NULL(-42,"消息不能为空"),
    NAME_NULL(-43,"姓名不能为空"),
    SCHOOLNAME_NULL(-44,"学号不能为空"),
    CHOSE_SCHOOL(-45,"请填写学校"),
    SCHOOL_NULL(-46,"学校不存在"),
    CHOSE_COLLEGE(-47,"请选择学院"),
    COLLEGE_NULL(-48,"学院不存在"),
    CHOSE_MAJOR(-49,"请选择专业"),
    MAJOR_NULL(-50,"专业不存在"),
    IDCARD_ERROR(-51,"身份证信息错误"),
    PASSWORD_MIN_MAX(-52,"密码的长度应在8-255之间"),
    REPORT_NULL(-53,"举报消息不能为空")
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
