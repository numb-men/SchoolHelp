package com.zgdr.schoolhelp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 映射问题回答表表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/29
 */
@Entity(name = "ques_ans")
public class QuestionAnswer {
    /* 问题回答id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ques_ans_id")
    private Integer questionAnswerId;

    /* 问卷回答id */
    @Column(name = "quesnaire_ans_id")
    private Integer quesnaireAnswerId;

    /* 问题id */
    @NotNull
    private Integer questionId;

    /* 问题类型 */
    @Column(length = 6)
    @NotNull
    private String type;

    /* 一对多关系，一个简答题可能有多个回答 */
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ques_ans_id")
    private List<ShortQuestionAnswer> shortQuestionAnswers;

    /* 一对多关系，一个选择题可能有多个回答 */
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "ques_ans_id")
    private List<ChoiceQuestionAnswer> questionAnswers;

    public List<ShortQuestionAnswer> getShortQuestionAnswers() {
        return shortQuestionAnswers;
    }

    public void setShortQuestionAnswers(List<ShortQuestionAnswer> shortQuestionAnswers) {
        this.shortQuestionAnswers = shortQuestionAnswers;
    }

    public List<ChoiceQuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<ChoiceQuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public Integer getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(Integer questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public Integer getQuesnaireAnswerId() {
        return quesnaireAnswerId;
    }

    public void setQuesnaireAnswerId(Integer quesnaireAnswerId) {
        this.quesnaireAnswerId = quesnaireAnswerId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QuestionAnswer(@NotNull Integer questionId, String type) {
        this.questionId = questionId;
        this.type = type;
    }

    public QuestionAnswer() {
    }
}
