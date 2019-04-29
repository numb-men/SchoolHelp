package com.zgdr.schoolhelp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Setting
 * 用户设置映射对象
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/27
 */

@JsonIgnoreProperties(value={"userId"}) // 返回时忽略userId
@Entity(name = "setting")
public class Setting {

    /* 用户id */
    @Id
    private Integer userId;

    /* 主题 */
    @NotBlank(message = "主题字段不能为空")
    @Length(max = 20, message = "主题字段超出长度限制")
    @Column(length = 20)
    private String theme;

    /* 消息通知 */
    @NotNull(message = "消息设置字段不能为空")
    private Boolean msgNotify;

    /* 隐藏个人资料 */
    @NotNull(message = "隐藏个人资料设置字段不能为空")
    private Boolean hidePersonalData;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isMsgNotify() {
        return msgNotify;
    }

    public void setMsgNotify(boolean msgNotify) {
        this.msgNotify = msgNotify;
    }

    public boolean isHidePersonalData() {
        return hidePersonalData;
    }

    public void setHidePersonalData(boolean hidePersonalData) {
        this.hidePersonalData = hidePersonalData;
    }

    public Setting(Integer userId,
                   @NotBlank(message = "主题字段不能为空")
                   @Length(max = 20, message = "主题字段超出长度限制") String theme,
                   boolean msgNotify, boolean hidePersonalData) {
        this.userId = userId;
        this.theme = theme;
        this.msgNotify = msgNotify;
        this.hidePersonalData = hidePersonalData;
    }

    public Setting() {
    }

    @Override
    public String toString() {
        return "Setting{" +
                "userId=" + userId +
                ", theme='" + theme + '\'' +
                ", msgNotify=" + msgNotify +
                ", hidePersonalData=" + hidePersonalData +
                '}';
    }
}
