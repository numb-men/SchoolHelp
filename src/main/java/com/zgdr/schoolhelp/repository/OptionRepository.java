package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * OptionRepository
 * 选择题选项数据接口
 *
 * @author yangji
 * @since 2019/4/27 14:36
 * @version 1.0
 */
public interface OptionRepository extends JpaRepository<Option,Integer> {
    List <Option> findAllByQuestionId(Integer questionId);
}
