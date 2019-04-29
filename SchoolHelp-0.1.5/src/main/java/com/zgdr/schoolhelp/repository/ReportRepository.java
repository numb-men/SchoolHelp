package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  ReportRepository
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/
public interface ReportRepository extends JpaRepository<Report,Integer> {


    //通过举报用户ID 查询举报表
    public List<Report> findAllByUserId(Integer userId);
}