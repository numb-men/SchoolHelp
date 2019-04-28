package com.zgdr.schoolhelp.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Search
 * 搜索实体映射对象
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/27
 */
@Entity(name = "search")
public class Search {

    /* 搜索记录ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer searchId;

    /* 搜索用户ID */
    private Integer userId;

    /* 搜索内容 */
    @NotBlank(message = "搜索内容不能为空")
    @Length(max = 100)
    @Column(length = 100)
    private String content;

    /* 搜索时间 */
    private Date searchTime = new Date();

    /* 是否隐藏 */
    private Boolean isHided = false;

    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Date searchTime) {
        this.searchTime = searchTime;
    }

    public boolean isHided() {
        return isHided;
    }

    public void setHided(boolean hided) {
        isHided = hided;
    }

    public Search(@NotBlank(message = "用户ID不能为空") Integer userId,
                  @NotBlank(message = "搜索内容不能为空") @Length(max = 100) String content,
                  Date searchTime, boolean isHided) {
        this.userId = userId;
        this.content = content;
        this.searchTime = searchTime;
        this.isHided = isHided;
    }

    public Search() {
    }

    @Override
    public String toString() {
        return "Search{" +
                "searchId=" + searchId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", searchTime=" + searchTime +
                ", isHided=" + isHided +
                '}';
    }
}
