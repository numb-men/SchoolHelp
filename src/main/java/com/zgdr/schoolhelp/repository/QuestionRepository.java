package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 问题表数据接口
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/26
 */
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findAllByQuesnaireId(Integer quesnaireId);//根据问卷的Id获取属于该问卷的所有问题
}
