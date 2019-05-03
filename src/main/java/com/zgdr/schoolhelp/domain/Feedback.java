package com.zgdr.schoolhelp.domain;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 映射意见反馈表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/26
 */
@Entity
public class Feedback {

   /* 意见反馈的ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedbackId;

    /* 反馈的用户id */
    @NotNull
    private Integer userId;

    /* 反馈的内容 */
    @NotBlank(message = "反馈内容不能为空")
    @Length(min = 10, max = 255, message = "反馈内容不能少于10个字，多于255个字")
    private String feedbackContent;

    /* 是否已经回复 */
    private boolean isReply;

    /* 反馈的时间 */
    private Date feedbackTime;

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public Feedback(@NotNull Integer userId,
                    @NotBlank(message = "反馈内容不能为空")
                    @Length(min = 10, max = 225, message = "反馈内容不能少于10个字，多于255个字")
                    String feedbackContent, boolean isReply, Date feedbackTime) {
        this.userId = userId;
        this.feedbackContent = feedbackContent;
        this.isReply = isReply;
        this.feedbackTime = feedbackTime;
    }

    public Feedback() {
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", userId=" + userId +
                ", feedbackContent='" + feedbackContent + '\'' +
                ", isReply=" + isReply +
                ", feedbackTime=" + feedbackTime +
                '}';
    }
}
