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
@Entity(name = "attention")
public class Attention {

    /*关注ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer attentionId;

    /*关注用户ID*/
    private Integer attentionUserId;

    /*被关注用户ID*/
    private Integer beAttentionUserId;

    /*关注时间*/
    private Date attentionTime;

    @Override
    public String toString() {
        return "Attention{" +
                "attentionId=" + attentionId +
                ", attentionUserId=" + attentionUserId +
                ", beAttentionUserId=" + beAttentionUserId +
                ", attentionTime=" + attentionTime +
                '}';
    }

    public Integer getAttentionId() {
        return attentionId;
    }

    public void setAttentionId(Integer attentionId) {
        this.attentionId = attentionId;
    }

    public Integer getAttentionUserId() {
        return attentionUserId;
    }

    public void setAttentionUserId(Integer attentionUserId) {
        this.attentionUserId = attentionUserId;
    }

    public Integer getBeAttentionUserId() {
        return beAttentionUserId;
    }

    public void setBeAttentionUserId(Integer beAttentionUserId) {
        this.beAttentionUserId = beAttentionUserId;
    }

    public Date getAttentionTime() {
        return attentionTime;
    }

    public void setAttentionTime(Date attentionTime) {
        this.attentionTime = attentionTime;
    }

    public Attention() {
    }

    public Attention(Integer attentionUserId, Integer beAttentionUserId, Date attentionTime) {
        this.attentionUserId = attentionUserId;
        this.beAttentionUserId = beAttentionUserId;
        this.attentionTime = attentionTime;
    }
}
