package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 帖子图片的数据接口
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/24
 */
public interface PostImageRepostiry extends JpaRepository<PostImage,Integer> {
    List<PostImage> findByPostId(Integer postId);
}
