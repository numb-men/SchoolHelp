package com.zgdr.schoolhelp.repository;


import com.zgdr.schoolhelp.domain.HeadImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用户头像表数据接口
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/2
 */
public interface HeadImageRepository extends JpaRepository<HeadImage,Integer> {
    HeadImage findByUserId(Integer userId);//查找一个用户的头像记录

    HeadImage getHeadImageByUserId(Integer userId);

    List<HeadImage> findAllByUserId(Integer userIds);
}
