package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Collect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CollectRepository
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/27
 */
public interface CollectRepository extends JpaRepository<Collect, Integer> {

    /**
     * 按用户id查找
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户Id
     * @return List<Collect>
     */
    List<Collect> findByUserId(Integer userId);

    Collect findByCollectIdIn(Integer postId);

    Collect findByPostIdAndUserIdIn(Integer userId, Integer postId);
}
