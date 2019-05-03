package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.ShortQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 简答题回答表的数据接口
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/29
 */
public interface ShortQuestionAnswerRepository extends JpaRepository<ShortQuestionAnswer, Integer> {
}
