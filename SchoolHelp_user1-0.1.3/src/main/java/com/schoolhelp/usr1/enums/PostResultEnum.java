package com.schoolhelp.usr1.enums;

public enum PostResultEnum {
    SUCCESS(0,"成功"),
    UNKNOW_ERROR(-1,"未知错误"),
    ;

    private Integer code;

    private String msg;

    PostResultEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
