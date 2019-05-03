package com.zgdr.schoolhelp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 映射保存轮播图的表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/2
 */
@Entity
public class RollImage {

    /* 图片的id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer imageId;

    /* 图片的url */
    private String imageUrl;

    /* 图片在轮播版的位置（第几张）*/
    @NotNull
    private Integer imageIndex;

    /* 图片所属轮播版（广告还是首页推送）*/
    @NotNull
    private Boolean imagePosition;

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

    public Integer getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(Integer imageIndex) {
        this.imageIndex = imageIndex;
    }

    public Boolean getImagePosition() {
        return imagePosition;
    }

    public void setImagePosition(Boolean imagePosition) {
        this.imagePosition = imagePosition;
    }

    public RollImage(String imageUrl,
                     @NotNull Integer imageIndex,
                     @NotNull Boolean imagePosition) {
        this.imageUrl = imageUrl;
        this.imageIndex = imageIndex;
        this.imagePosition = imagePosition;
    }

    public RollImage() {
    }
}
