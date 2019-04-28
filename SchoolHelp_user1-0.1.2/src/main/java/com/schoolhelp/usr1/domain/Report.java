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
@Entity(name = "report")
public class Report {

    /*举报ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer reportId;

    /*举报用户ID*/
    private Integer userId;

    /*被举报帖子ID*/
    private Integer postId;

    /*举报描述*/
    private String reportDes;

    /*举报时间*/
    private Date reportTime;


    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", reportDes='" + reportDes + '\'' +
                ", reportTime=" + reportTime +
                '}';
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
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

    public String getReportDes() {
        return reportDes;
    }

    public void setReportDes(String reportDes) {
        this.reportDes = reportDes;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Report(Integer userId, Integer postId, String reportDes, Date reportTime) {
        this.userId = userId;
        this.postId = postId;
        this.reportDes = reportDes;
        this.reportTime = reportTime;
    }

    public Report() {
    }
}
