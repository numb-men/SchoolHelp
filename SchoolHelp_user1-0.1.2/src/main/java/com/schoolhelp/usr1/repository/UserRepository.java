package com.schoolhelp.usr1.repository;

import com.schoolhelp.usr1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
