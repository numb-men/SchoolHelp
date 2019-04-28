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

@Entity(name = "approval")
public class Approval {

    /* 点赞ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer approvalId;

    /* 点赞用户ID */
    private Integer userId;

    /* 被点赞帖子ID */
    private Integer postId;

    /* 点赞时间 */
    private Date approvalTime;

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    @Override
    public String toString() {
        return "approval{" +
                "approvalId=" + approvalId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", approvalTime=" + approvalTime +
                '}';
    }

    public Approval(Integer approvalId, Integer userId, Integer postId, Date approvalTime) {
        this.approvalId = approvalId;
        this.userId = userId;
        this.postId = postId;
        this.approvalTime = approvalTime;
    }

    public Approval() {
    }
}

