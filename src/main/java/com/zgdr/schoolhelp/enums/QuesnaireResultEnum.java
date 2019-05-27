package com.zgdr.schoolhelp.enums;

/**
 * 问卷的异常信息
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/27
 */
public enum QuesnaireResultEnum  implements  ResultEnum{
    /*枚举*/
   UNEXIST_QUESNAIRE(-22,"问卷不存在"),
   INVALID_FORM(-23,"表单验证失败"),
   NO_POWER_TO_DELETE(-24, "无权限删除该问卷"),
   NO_POWER_TO_UPDATE(-25, "无权限修改该问卷"),
   NO_REPEAT_REPLY(-26,"你已填写过该问卷"),
    LESS_POINTS(-27, "积分不足");




    private Integer code;

    private  String msg;

    QuesnaireResultEnum(Integer code, String msg) {
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
