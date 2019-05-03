package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.QuesnaireAnswer;
import com.zgdr.schoolhelp.domain.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 问题回答表数据接口
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/29
 */
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Integer> {
}
