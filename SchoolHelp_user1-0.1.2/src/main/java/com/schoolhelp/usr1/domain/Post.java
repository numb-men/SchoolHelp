package com.schoolhelp.usr1.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/27
 **/

@Entity(name = "post")
public class Post {

    /* 帖子ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer postId;

    /*用户ID*/
    private Integer usrId;

    /*帖子标题*/
    private String title;

    /*帖子内容*/
    private String text;

    /*积分*/
    private Integer points;

    /*浏览量*/
    private Integer viewNum;

    /*点赞量*/
    private Integer approvalNum;

    /*评论量*/
    private Integer commentNum;

    /*举报量*/
    private Integer reportNum;

    /*帖子类型*/
    private String postType;

    /*发布时间*/
    private Date issueTime;

    public Integer getId() {
        return postId;
    }

    public void setId(Integer id) {
        this.postId = id;
    }

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getApprovalNum() {
        return approvalNum;
    }

    public void setApprovalNum(Integer approvalNum) {
        this.approvalNum = approvalNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + postId +
                ", usrId=" + usrId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", points=" + points +
                ", viewNum=" + viewNum +
                ", approvalNum=" + approvalNum +
                ", commentNum=" + commentNum +
                ", reportNum=" + reportNum +
                ", postType='" + postType + '\'' +
                ", issueTime=" + issueTime +
                '}';
    }

    public Post(Integer usrId, String title, String text, Integer points, Integer viewNum,
                Integer approvalNum, Integer commentNum, Integer reportNum, String postType,
                Date issueTime) {
        this.usrId = usrId;
        this.title = title;
        this.text = text;
        this.points = points;
        this.viewNum = viewNum;
        this.approvalNum = approvalNum;
        this.commentNum = commentNum;
        this.reportNum = reportNum;
        this.postType = postType;
        this.issueTime = issueTime;
    }

    public Post() {
    }
}
