package com.zgdr.schoolhelp.domain;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 映射问卷回答表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/29
 */
@Entity(name = "quesnaire_ans")
public class QuesnaireAnswer {

    /* 问卷回答id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quesnaire_ans_id")
    private Integer quesnaireAnswerId;

    /* 问卷id */
    @NotNull
    private Integer quesnaireId;

    /* 回答的用户 */
    @NotNull
    private Integer userId;

    /* 回答的时间 */
    @NotNull
    @Column(name = "ans_time")
    private Date answerTime;

    /* 一对多关系，一个问卷有多个问题回答表 */
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "quesnaire_ans_id")
    private List<QuestionAnswer> questionAnswers;

    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public Integer getQuesnaireAnswerId() {
        return quesnaireAnswerId;
    }

    public void setQuesnaireAnswerId(Integer quesnaireAnswerId) {
        this.quesnaireAnswerId = quesnaireAnswerId;
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

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public QuesnaireAnswer(@NotNull Integer quesnaireId,
                           @NotNull Integer userId,
                           @NotNull Date answerTime) {
        this.quesnaireId = quesnaireId;
        this.userId = userId;
        this.answerTime = answerTime;
    }

    public QuesnaireAnswer() {
    }

    @Override
    public String toString() {
        return "QuesnaireAnswer{" +
                "quesnaireAnswerId=" + quesnaireAnswerId +
                ", quesnaireId=" + quesnaireId +
                ", userId=" + userId +
                ", answerTime=" + answerTime +
                '}';
    }
}
