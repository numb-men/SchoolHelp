package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * StudentRepository
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/
public interface StudentRepository extends JpaRepository<Student,Integer> {

}
