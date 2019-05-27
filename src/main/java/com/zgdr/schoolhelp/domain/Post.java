package com.zgdr.schoolhelp.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * post表映射对象
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/4/27
 */
@Entity(name = "post")
public class Post {

    /* 贴子ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //使用默认生成方式（MySQL）：自增
    private Integer postId;

    /* 发帖者ID */
    private Integer userId;

    /* 帮助者ID*/
    private Integer helpUserId;

    /* 标题 */
    @NotBlank
    private String  title;

    /* 贴子内容*/
   @NotBlank
    private String content;

    /* 悬赏积分 */
    @NotNull
    private Integer points ;

    /* 用户名*/
    private String userName;

    /* 浏览量*/
    private Integer viewNum;

    /* 点赞数 */
    private Integer approvalNum;

    /* 评论数 */
    private Integer commentNum;

    /* 举报数*/
    private Integer reportNum;

    /* 贴子类型*/
    private String postType;

    /* 发帖时间 */
    private Date issueTime ;

    /* 发帖人头像的url */
    private String headImageUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getHelpUserId() {
        return helpUserId;
    }

    public void setHelpUserId(Integer helpUserId) {
        this.helpUserId = helpUserId;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public Post() {
    }

    public Post(Integer userId, Integer helpUserId, @NotBlank String title,
                @NotBlank String content, @NotNull Integer points, String userName,
                Integer viewNum, Integer approvalNum,
                Integer commentNum, Integer reportNum, String postType, Date issueTime, String headImageUrl) {
        this.userId = userId;
        this.helpUserId = helpUserId;
        this.title = title;
        this.content = content;
        this.points = points;
        this.userName = userName;
        this.viewNum = viewNum;
        this.approvalNum = approvalNum;
        this.commentNum = commentNum;
        this.reportNum = reportNum;
        this.postType = postType;
        this.issueTime = issueTime;
        this.headImageUrl = headImageUrl;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", helpUserId=" + helpUserId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", points=" + points +
                ", userName='" + userName + '\'' +
                ", viewNum=" + viewNum +
                ", approvalNum=" + approvalNum +
                ", commentNum=" + commentNum +
                ", reportNum=" + reportNum +
                ", postType='" + postType + '\'' +
                ", issueTime=" + issueTime +
                ", headImageUrl='" + headImageUrl + '\'' +
                '}';
    }
}
