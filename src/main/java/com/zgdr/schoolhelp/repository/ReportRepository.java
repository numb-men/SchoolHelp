package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * reportDao
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/4/27
 */
public interface ReportRepository extends JpaRepository<Report, Integer>{
    /**
     * 通过贴子id来获得贴子的举报用户id列表
     * @author fishkk
     * @since 2019/4/27
     *
     * @param  id 贴子id
     * @return    Set<Integer>
     */
    @Query(value = "SELECT user_id FROM report WHERE post_id=?1", nativeQuery = true)
    public Set<Integer> getListReportUser(Integer id);
}
