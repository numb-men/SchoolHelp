package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Feedback;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 映反馈表数据接口
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/25
 */
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
}
