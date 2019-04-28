package com.zgdr.schoolhelp.domain;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 映射选择题选项表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/27
 */
@Entity(name = "options")
public class Option {
    /* 选项的id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer optionId;

    /* 选项所属的问题的id */
    @Column(name = "question_id")//因为表间关联关系，该注解不可省略
    private Integer questionId;

    /* 选项的内容 */
    @NotBlank(message = "选项内容不能为空")
    @Length(min = 1, max=30, message = "选项内容能少于30个字")
    private  String content;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Option(@NotBlank(message = "选项内容不能为空")
                  @Length(min = 1, max = 30, message = "选项内容能少于30个字")
                  String content) {
        this.content = content;
    }

    public Option() { }

    @Override
    public String toString() {
        return "Option{" +
                "optionId=" + optionId +
                ", questionId=" + questionId +
                ", content='" + content + '\'' +
                '}';
    }

}
