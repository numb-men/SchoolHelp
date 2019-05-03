package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Quesnaire;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 问卷表数据接口
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/26
 */
public interface QuesnaireRepository extends JpaRepository<Quesnaire,Integer> {
}
