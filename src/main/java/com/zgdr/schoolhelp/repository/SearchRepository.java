package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * SearchRepository
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/27
 */
public interface SearchRepository extends JpaRepository<Search, Integer> {
    List<Search> findAllByUserIdOrderBySearchTimeDesc(Integer userId);
    List<Search> findAllByUserIdAndIsHidedFalse(Integer userId);
    Search findBySearchIdIn(Integer searchId);
}
