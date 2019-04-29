package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *  SchoolRepository
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/
public interface SchoolRepository extends JpaRepository<School,Integer> {

}
