package com.zgdr.schoolhelp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 映射选择题回答表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/29
 */
@Entity(name = "choice_ans")
public class ChoiceQuestionAnswer {

    /* 选择题回答id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "choice_ans_id")
    private Integer choiceAnswerId;

    /* 问题回答id */
    @Column(name = "ques_ans_id")
    private Integer questionAnswerId;

    /* 选择题选项id */
    @NotNull
    private Integer optionId;

    public Integer getChoiceAnswerId() {
        return choiceAnswerId;
    }

    public Integer getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(Integer questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public ChoiceQuestionAnswer(@NotNull Integer optionId) {
        this.optionId = optionId;
    }

    public ChoiceQuestionAnswer() {
    }

    @Override
    public String toString() {
        return "ChoiceQuestionAnswer{" +
                "choiceAnswerId=" + choiceAnswerId +
                ", questionAnswerId=" + questionAnswerId +
                ", optionId=" + optionId +
                '}';
    }
}
