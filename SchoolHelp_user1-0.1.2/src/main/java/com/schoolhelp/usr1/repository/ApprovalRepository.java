package com.schoolhelp.usr1.repository;

import com.schoolhelp.usr1.domain.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.chrono.JapaneseChronology;
import java.util.List;

public interface ApprovalRepository extends JpaRepository<Approval,Integer> {

    //通过用户ID查询点赞表
    public List<Approval> findByUserId(Integer userId);
}
