package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.ChoiceQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 映射简答题回答表
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/29
 */
public interface ChoiceQuestionAnswerRepository extends JpaRepository<ChoiceQuestionAnswer, Integer> {
}
