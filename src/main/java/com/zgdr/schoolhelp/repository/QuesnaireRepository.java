package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Quesnaire;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * QuesnaireRepository
 * Quesnaire的数据访问接口
 *
 * @author yangji
 * @since 2019/4/26 20:26
 * @version 1.0
 */
public interface QuesnaireRepository extends JpaRepository<Quesnaire,Integer> {
}
