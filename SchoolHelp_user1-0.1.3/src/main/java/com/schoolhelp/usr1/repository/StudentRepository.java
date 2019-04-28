package com.schoolhelp.usr1.repository;

import com.schoolhelp.usr1.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {

}
