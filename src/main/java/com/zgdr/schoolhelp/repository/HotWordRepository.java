package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.HotWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 热词数据接口
 *
 * @author kai
 * @version 1.0
 * @since 2019/5/29
 */
public interface HotWordRepository extends JpaRepository<HotWord, Integer> {
    HotWord findByHotWord(String keyWord);
    List<HotWord> findAllByOrderByTimesDesc();
}
