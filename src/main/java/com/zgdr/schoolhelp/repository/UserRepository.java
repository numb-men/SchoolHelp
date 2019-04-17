package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserDao
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
