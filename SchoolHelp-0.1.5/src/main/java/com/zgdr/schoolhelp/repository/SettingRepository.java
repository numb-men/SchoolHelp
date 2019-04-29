package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SettingRepository
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/27
 */
public interface SettingRepository extends JpaRepository<Setting, Integer> {
    Setting findByUserIdIn(Integer userId);
}
