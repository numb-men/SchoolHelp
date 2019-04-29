package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CommentRepository
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    //通过用户ID查询评论表
    public List<Comment> findAllByUserId(Integer userId);
}
