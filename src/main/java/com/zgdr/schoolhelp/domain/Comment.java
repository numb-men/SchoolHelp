package com.zgdr.schoolhelp.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * comment表映射对象
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/4/27
 */
@Entity(name = "comment")
public class Comment {
    /* 评论ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //使用默认生成方式（MySQL）：自增
    private Integer commentId;

    /* 评论用户ID */
    private Integer userId ;


    /* 被评论贴子的ID */
    private Integer postId;

    /* 评论用户名 */
    private String commentUserName;

    /* 评论内容 */
    @NotBlank
    @Length(min = 1, max = 100, message = "评论内容应在1-100字之间")
    private String commentContent;

    /* 评论日期*/
    private Date commentTime;

    /* 评论者的头像url */
    private  String headImageUrl;

    public Comment() {
    }

    public Comment(Integer userId, Integer postId, String commentUserName,
                   @NotBlank String commentContent, Date commentTime, String headImageUrl) {
        this.userId = userId;
        this.postId = postId;
        this.commentUserName = commentUserName;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
        this.headImageUrl = headImageUrl;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", commentUserName='" + commentUserName + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentTime=" + commentTime +
                '}';
    }
}
