package com.zgdr.schoolhelp.domain;





import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * 映射问卷表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/25
 */
@Entity(name = "quesnaire")
public class Quesnaire {
    /* 问卷的id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer quesnaireId;

    /* 发布问卷的用户id */
    private Integer userId;

    /* 问卷的描述 */
    @Column(length = 255)
    @NotBlank(message = "问卷描述不能为空")
    private String  quesnaireDescrible;

    /* 问卷采集份数 */
    @Max(value = 100, message = "最多只能采集100份")
    @Min(value = 15, message = "采集份数不得少于10份")
    @NotNull
    private Integer limitNum;

    /* 已经填写的问卷份数 */
    private Integer finishNum;

    /* 问卷积分 */
    private Integer postPoint;

    /* 发布时间 */
    private Date publishTime;

    /* 截止时间 */
 //  @Future
    private Date finishTime;

    /* 一对多的关联，一个问卷有多个问题  */
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "quesnaire_id")
    private List<com.zgdr.schoolhelp.domain.Question> questions;

    public List<com.zgdr.schoolhelp.domain.Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<com.zgdr.schoolhelp.domain.Question> questions) {
        this.questions = questions;
    }

    public Integer getQuesnaireId() {
        return quesnaireId;
    }

    public void setQuesnaireId(Integer quesnaireId) {
        this.quesnaireId = quesnaireId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getQuesnaireDescrible() {  return quesnaireDescrible; }

    public void setQuesnaireDescrible(String quesnaireDescrible) {
        this.quesnaireDescrible = quesnaireDescrible;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public Integer getPostPoint() {
        return postPoint;
    }

    public void setPostPoint(Integer postPoint) {
        this.postPoint = postPoint;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Quesnaire(Integer userId,
                     @NotBlank(message = "问卷描述不能为空") String quesnaireDescrible,
                     @Max(value = 100, message = "最多只能采集100份")
                     @Min(value = 15, message = "采集份数不得少于15份")
                     @NotNull Integer limitNum, Integer finishNum,
                     Integer postPoint, Date publishTime,
                     @Future Date finishTime) {
        this.userId = userId;
        this.quesnaireDescrible = quesnaireDescrible;
        this.limitNum = limitNum;
        this.finishNum = finishNum;
        this.postPoint = postPoint;
        this.publishTime = publishTime;
        this.finishTime = finishTime;
    }

    public Quesnaire() {
    }

    @Override
    public String toString() {
        return "Quesnaire{" +
                "quesnaireId=" + quesnaireId +
                ", userId=" + userId +
                ", quesnaireDescrible='" + quesnaireDescrible + '\'' +
                ", limitNum=" + limitNum +
                ", finishNum=" + finishNum +
                ", postPoint=" + postPoint +
                ", publishTime=" + publishTime +
                ", finishTime=" + finishTime +
                ", questions=" + questions +
                '}';
    }
}
