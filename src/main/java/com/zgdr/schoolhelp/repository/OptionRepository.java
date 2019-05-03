package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 选项表数据接口
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/27
 */
public interface OptionRepository extends JpaRepository<Option,Integer> {
    List <Option> findAllByQuestionId(Integer questionId);
}
