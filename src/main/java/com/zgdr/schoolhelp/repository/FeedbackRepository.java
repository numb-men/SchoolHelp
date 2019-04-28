package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Feedback;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FeedbackRepository
 * 意见反馈JPA
 *
 * @author yangji
 * @since 2019/4/25 19:16
 * @version 1.0
 */
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
}
