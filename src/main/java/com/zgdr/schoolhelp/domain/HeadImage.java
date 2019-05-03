package com.zgdr.schoolhelp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 映射保存用户头像的表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/2
 */
@Entity
public class HeadImage {

    /* 头像的id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer imageId;

    /* 头像的url */
    private String imageUrl;

    /* 头像所属用户 */
    @NotNull
    private Integer userId;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public HeadImage(String imageUrl, @NotNull Integer userId) {
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public HeadImage() {
    }
}
