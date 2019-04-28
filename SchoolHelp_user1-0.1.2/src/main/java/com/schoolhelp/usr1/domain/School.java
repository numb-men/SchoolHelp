package com.schoolhelp.usr1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Entity(name = "school")
public class School {

    /*学校ID*/
    private Integer schoolId;

    /*学校名*/
    @Column(length = 20)
    private String schoolName;

    /*入学时间*/
    private Date joinTime;

    @Override
    public String toString() {
        return "School{" +
                "schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                ", joinTime=" + joinTime +
                '}';
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public School(Integer schoolId, String schoolName, Date joinTime) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.joinTime = joinTime;
    }

    public School() {
    }
}