package com.schoolhelp.usr1.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Entity(name = "student")
public class Student {

    /*用户ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer userId;

    /*学号*/
    @Column(length = 12)
    private Integer studentNum;

    /*真实姓名*/
    @Column(length = 8)
    private String relaname;

    /*学校ID*/
    private Integer schoolId;

    /*学院ID*/
    private Integer collegeId;

    /*专业ID*/
    private Integer majorId;

    /*身份证*/
    @Column(length = 18)
    private String IdCard;

    /*认证时间*/
    private Date authenticateTime;

    @Override
    public String toString() {
        return "Student{" +
                "userId=" + userId +
                ", studentNum=" + studentNum +
                ", relaname='" + relaname + '\'' +
                ", schoolId=" + schoolId +
                ", collegeId=" + collegeId +
                ", majorId=" + majorId +
                ", IdCard='" + IdCard + '\'' +
                ", authenticateTime=" + authenticateTime +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public String getRelaname() {
        return relaname;
    }

    public void setRelaname(String relaname) {
        this.relaname = relaname;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public Date getAuthenticateTime() {
        return authenticateTime;
    }

    public void setAuthenticateTime(Date authenticateTime) {
        this.authenticateTime = authenticateTime;
    }

    public Student() {
    }

    public Student(Integer studentNum, String relaname, Integer schoolId, Integer collegeId,
                   Integer majorId, String idCard, Date authenticateTime) {
        this.studentNum = studentNum;
        this.relaname = relaname;
        this.schoolId = schoolId;
        this.collegeId = collegeId;
        this.majorId = majorId;
        IdCard = idCard;
        this.authenticateTime = authenticateTime;
    }
}
