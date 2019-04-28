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
@Entity(name = "comment")
public class Comment {

    /*评论ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer commentId;

    /*评论用户ID*/
    private Integer userId;

    /*评论帖子ID*/
    private Integer postId;

    /*评论内容*/
    private String commentContent;

    /*评论时间*/
    private Date commentTime;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", commentContent='" + commentContent + '\'' +
                ", commentTime=" + commentTime +
                '}';
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

    public Comment(Integer userId, Integer postId, String commentContent, Date commentTime) {
        this.userId = userId;
        this.postId = postId;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }

    public Comment() {
    }
}
