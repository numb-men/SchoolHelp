package com.zgdr.schoolhelp.domain;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * user表映射对象
 *
 * @author hengyumo
 * @version 1.5
 * @since 2019/4/17
 */
@Entity(name = "user")
public class User{

    /* 用户ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //使用默认生成方式（MySQL）：自增
    private Integer id;

    /* 用户名 */
    @NotBlank(message = "用户名不能为空")
    @Length(max = 30, message = "用户名过长")
    @Column(length = 30)
    private String name;

    /* 手机号 */
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机长度应为11")
    @Column(length = 11, unique = true)
    private String phone;

    /* 密码 */
    @NotBlank(message = "密码不能为空")
    @Length(max = 255, min = 8, message = "密码的长度应在8-255之间")
    @Column(length = 255)
    private String password;

    /* 性别 */
    private Boolean sex = true;
    
    /* 生日 */
    private Date birthdate = new Date();

    /* 积分 */
    private Integer points = 0;
    
    /* 收藏帖子个数 */
    private Integer collectPostNum = 0;

    /* 关注个数 */
    private Integer fallowNum = 0;

    /* 角色 普通用户/管理员 */
    private Boolean role = false;

    /* 是否学生认证 */
    private Boolean isCertified = false;
    
    /* 是否在线 */
    private Boolean isOnline = true;

    /* 注册时间 */
    private Date registerTime = new Date();

    /* 最近登录时间 */
    private Date lastTime = new Date();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getCollectPostNum() {
        return collectPostNum;
    }

    public void setCollectPostNum(Integer collectPostNum) {
        this.collectPostNum = collectPostNum;
    }

    public Integer getFallowNum() {
        return fallowNum;
    }

    public void setFallowNum(Integer fallowNum) {
        this.fallowNum = fallowNum;
    }

    public boolean getRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public boolean isCertified() {
        return isCertified;
    }

    public void setCertified(boolean certified) {
        isCertified = certified;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public User(@NotBlank(message = "用户名不能为空")
                @Length(max = 30, message = "用户名过长") String name,
                @NotBlank(message = "手机号不能为空")
                @Length(min = 11, max = 11, message = "手机长度应为11") String phone,
                @NotBlank(message = "密码不能为空")
                @Length(max = 255, min = 8, message = "密码的长度应在8-255之间") String password,
                boolean sex, Date birthdate, Integer points, Integer collectPostNum, Integer fallowNum,
                boolean role, boolean isCertified, boolean isOnline, Date registerTime, Date lastTime) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.sex = sex;
        this.birthdate = birthdate;
        this.points = points;
        this.collectPostNum = collectPostNum;
        this.fallowNum = fallowNum;
        this.role = role;
        this.isCertified = isCertified;
        this.isOnline = isOnline;
        this.registerTime = registerTime;
        this.lastTime = lastTime;
    }

    public User(){
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", birthdate=" + birthdate +
                ", points=" + points +
                ", collectPostNum=" + collectPostNum +
                ", fallowNum=" + fallowNum +
                ", role=" + role +
                ", isCertified=" + isCertified +
                ", isOnline=" + isOnline +
                ", registerTime=" + registerTime +
                ", lastTime=" + lastTime +
                '}';
    }
}
