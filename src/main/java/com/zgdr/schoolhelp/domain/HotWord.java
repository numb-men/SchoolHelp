package com.zgdr.schoolhelp.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 搜索热词实体类
 *
 *
 * @author kai
 * @version 1.0
 * @since 2019/5/29
 */
@Entity
public class HotWord {

    /* 热词id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer hotWordId;

    /* 内容 */
    @Column(unique = true)
    private String hotWord;

    /* 次数 */
    private Integer times;

    /* 最近出现时间 */
    private Date recentTime;

    public HotWord(String hotWord, Integer times, Date recentTime) {
        this.hotWord = hotWord;
        this.times = times;
        this.recentTime = recentTime;
    }

    public HotWord() {
    }

    public Integer getHotWordId() {
        return hotWordId;
    }

    public void setHotWordId(Integer hotWordId) {
        this.hotWordId = hotWordId;
    }

    public String getHotWord() {
        return hotWord;
    }

    public void setHotWord(String hotWord) {
        this.hotWord = hotWord;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Date getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(Date recentTime) {
        this.recentTime = recentTime;
    }
}
