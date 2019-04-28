package com.schoolhelp.usr1.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/
@Entity(name = "atten")
public class Atten {

    /*关注ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer attenId;

    /*关注用户ID*/
    private Integer attenUserId;

    /*被关注用户ID*/
    private Integer beAttenUserId;

    /*关注时间*/
    private Date attenTime;

    @Override
    public String toString() {
        return "Atten{" +
                "attenId=" + attenId +
                ", attenUserId=" + attenUserId +
                ", beAttenUserId=" + beAttenUserId +
                ", attenTime=" + attenTime +
                '}';
    }

    public Integer getAttenId() {
        return attenId;
    }

    public void setAttenId(Integer attenId) {
        this.attenId = attenId;
    }

    public Integer getAttenUserId() {
        return attenUserId;
    }

    public void setAttenUserId(Integer attenUserId) {
        this.attenUserId = attenUserId;
    }

    public Integer getBeAttenUserId() {
        return beAttenUserId;
    }

    public void setBeAttenUserId(Integer beAttenUserId) {
        this.beAttenUserId = beAttenUserId;
    }

    public Date getAttenTime() {
        return attenTime;
    }

    public void setAttenTime(Date attenTime) {
        this.attenTime = attenTime;
    }

    public Atten() {
    }

    public Atten(Integer attenUserId, Integer beAttenUserId, Date attenTime) {
        this.attenUserId = attenUserId;
        this.beAttenUserId = beAttenUserId;
        this.attenTime = attenTime;
    }
}
