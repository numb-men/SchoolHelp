package com.zgdr.schoolhelp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Collect
 * 用户收藏映射对象
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/27
 */
@Entity(name = "collect")
public class Collect {

    /* 收藏ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer collectId;

    /* 被收藏的帖子ID */
    private Integer postId;

    /* 收藏用户的ID */
    private Integer userId;

    /* 收藏时间 */
    private Date coolectTiem = new Date();

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
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

    public Date getCoolectTiem() {
        return coolectTiem;
    }

    public void setCoolectTiem(Date coolectTiem) {
        this.coolectTiem = coolectTiem;
    }

    public Collect(@NotBlank(message = "帖子ID不能为空") Integer postId,
                   @NotBlank(message = "用户ID不能为空") Integer userId, Date coolectTiem) {
        this.postId = postId;
        this.userId = userId;
        this.coolectTiem = coolectTiem;
    }

    public Collect() {
    }

    @Override
    public String toString() {
        return "Collect{" +
                "collectId=" + collectId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", coolectTiem=" + coolectTiem +
                '}';
    }
}
