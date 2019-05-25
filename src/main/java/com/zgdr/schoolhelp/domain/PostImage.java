package com.zgdr.schoolhelp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 帖子图片
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/24
 */
@Entity
public class PostImage {

    /* 图片id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postImageId;

    /* 图片所属帖子的id */
    @NotNull
    private Integer postId;

    /* 图片的url*/
    private String imageUrl;

    public Integer getPostImageId() {
        return postImageId;
    }

    public void setPostImageId(Integer postImageId) {
        this.postImageId = postImageId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PostImage() {
    }

    public PostImage(@NotNull Integer postId, String imageUrl) {
        this.postId = postId;
        this.imageUrl = imageUrl;
    }
}
