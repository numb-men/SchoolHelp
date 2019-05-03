package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.QuesnaireAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * 问卷回答表的数据接口
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/29
 */
public interface QuesnaireAnswerRepository extends JpaRepository<QuesnaireAnswer, Integer> {
    QuesnaireAnswer findByUserIdAndQuesnaireId(Integer userId, Integer quesnaireId);//查找该用户是否已经回答过该问卷
    List<QuesnaireAnswer> findAllByQuesnaireId(Integer quesnaireId);//查找属于一份问卷的答案
    void deleteAllByQuesnaireId(Integer quesnaireId);//根据问卷id删除该问卷的
}
