package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.RollImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 轮播图表的数据接口
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/2
 */
public interface RollImageRepository extends JpaRepository<RollImage,Integer> {
    List<RollImage> findAllByImagePosition(Boolean imagePosition);//获取某个轮播板的所有图片
    RollImage findByImageIndexAndImagePosition(Integer index, Boolean imagePosition);//获取某个轮播版的某个图片
}
