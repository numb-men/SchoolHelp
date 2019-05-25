package com.zgdr.schoolhelp.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * report表映射对象
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/4/27
 */
@Entity(name = "report")
public class Report {
    /* 举报ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //使用默认生成方式（MySQL）：自增
    private Integer reportId;

    /* 举报者用户ID */
    private Integer userId;

    /* 被举报贴子ID */
    private Integer postId;

    /* 举报描述 */
    @NotBlank
    private String reportDes;

    /* 举报日期*/
    private Date reportTime;

    public Report() {
    }

    public Report(Integer userId, Integer postId, @NotBlank String reportDes, Date reportTime) {
        this.userId = userId;
        this.postId = postId;
        this.reportDes = reportDes;
        this.reportTime = reportTime;
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
}
