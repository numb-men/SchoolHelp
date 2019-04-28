package com.zgdr.schoolhelp.domain;

import javax.persistence.*;

/**
 * major
 * 专业表映射对象
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Entity(name = "major")
public class Major {

    /*专业D*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer majorId;

    /*学校ID*/
    private Integer schooleId;

    /*学院ID*/
    private Integer collegeId;

    /*专业名*/
    @Column(length = 20)
    private String majoName;

    @Override
    public String toString() {
        return "Major{" +
                "majorId=" + majorId +
                ", schooleId=" + schooleId +
                ", collegeId=" + collegeId +
                ", majoName='" + majoName + '\'' +
                '}';
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getSchooleId() {
        return schooleId;
    }

    public void setSchooleId(Integer schooleId) {
        this.schooleId = schooleId;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public String getMajoName() {
        return majoName;
    }

    public void setMajoName(String majoName) {
        this.majoName = majoName;
    }

    public Major(Integer majorId, Integer schooleId, Integer collegeId, String majoName) {
        this.majorId = majorId;
        this.schooleId = schooleId;
        this.collegeId = collegeId;
        this.majoName = majoName;
    }

    public Major() {
    }
}
