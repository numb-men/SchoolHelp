package com.zgdr.schoolhelp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 映射简答题回答表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/29
 */
@Entity(name = "short_ques_ans")
public class ShortQuestionAnswer {

    /* 简答题答案id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "short_ques_ans_id")
    private Integer shortQuestionAnswerId;

    /* 问题答案id */
    @Column(name = "ques_ans_id")
    private Integer questionAnswerId;

    /* 简答题答案 */
    @NotNull
    @Column(name = "ans_content")
    private String answerContent;

    public Integer getShortQuestionAnswerId() {
        return shortQuestionAnswerId;
    }

    public void setShortQuestionAnswerId(Integer shortQuestionAnswerId) {
        this.shortQuestionAnswerId = shortQuestionAnswerId;
    }

    public Integer getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(Integer questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public ShortQuestionAnswer(@NotNull String answerContent) {
        this.answerContent = answerContent;
    }

    public ShortQuestionAnswer() {
    }

    @Override
    public String toString() {
        return "ShortQuestionAnswer{" +
                "shortQuestionAnswerId=" + shortQuestionAnswerId +
                ", questionAnswerId=" + questionAnswerId +
                ", answerContent='" + answerContent + '\'' +
                '}';
    }
}
