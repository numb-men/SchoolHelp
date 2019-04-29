package com.zgdr.schoolhelp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * college
 * 学院表表映射对象
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Entity(name = "college")
public class College {

    /*学院ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer collegeId;

    /*学校ID*/
    private Integer schoolId;

    /*学院名*/
    private String collegeName;

    @Override
    public String toString() {
        return "College{" +
                "collegeId=" + collegeId +
                ", schoolId=" + schoolId +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public College() {
    }

    public College(Integer collegeId, Integer schoolId, String collegeName) {
        this.collegeId = collegeId;
        this.schoolId = schoolId;
        this.collegeName = collegeName;
    }
}
