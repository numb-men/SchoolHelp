package com.zgdr.schoolhelp.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 映射问题表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/25
 */
@Entity(name = "question")
public class Question {
    /* 问题的ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;

    /* 所属问卷id */
    @Column(name = "quesnaire_id")//因为表间关联关系，该注解不可省略
    private Integer quesnaireId;

    /* 题号 */
    private Integer questionIndex;

    /* 题目描述 */
    @NotBlank(message = "题目不能为空")
    @Length(max = 25, message = "题目不能超过25个字")
    private String title;

    /* 是否必做 */
    @NotNull
    private boolean isMust;

    /* 题目类型 */
    @NotEmpty
    private String type;

    /* 一对多的关联，一个问题具有多个选项 */
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private List<Option> options;

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuesnaireId() {
        return quesnaireId;
    }

    public void setQuesnaireId(Integer quesnaireId) {
        this.quesnaireId = quesnaireId;
    }

    public Integer getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(Integer questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isMust() {
        return isMust;
    }

    public void setMust(boolean must) { isMust = must; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Question(Integer questionIndex,
                    @NotBlank(message = "题目不能为空")
                    @Length(max = 25, message = "题目不能超过25个字") String title,
                    @NotEmpty boolean isMust, @NotEmpty String type) {
        this.questionIndex = questionIndex;
        this.title = title;
        this.isMust = isMust;
        this.type = type;
    }

    public Question() {
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionIndex=" + questionIndex +
                ", title='" + title + '\'' +
                ", isMust=" + isMust +
                ", type='" + type + '\'' +
                '}';
    }
}
