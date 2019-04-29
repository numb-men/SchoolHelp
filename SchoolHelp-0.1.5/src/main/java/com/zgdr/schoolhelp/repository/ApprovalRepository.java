package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ApprovalRepository
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/


public interface ApprovalRepository extends JpaRepository<Approval,Integer> {

    //通过用户ID查询点赞表
    public List<Approval> findAllByUserId(Integer userId);
}
