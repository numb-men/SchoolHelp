package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * commentDao
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/4/27
 */
public interface CommentRepository extends JpaRepository<Comment, Integer>{

      List<Comment> findAllByPostIdOrderByCommentTimeDesc(Integer postId);

      /**
       * 获取贴子的评论用户列表
       * @author fishkk
       * @since  2019/4/27
       *
       * @param   id 贴子id
       * @return   Set 评论用户id集合
       */
      @Query(value = "SELECT user_id FROM comment WHERE post_id=?1", nativeQuery = true)
      public Set<Integer> getListCommentUser(Integer id);

      /**
       * 删除评论通过贴子id
       * @author fishkk
       * @since 2019/4/27
       *
       * @param  postId 贴子id
       * @return
       */

      public void deleteByPostId(Integer postId);

      //通过用户ID查询评论表
      public List<Comment> findAllByUserId(Integer userId);

      /**
       * 获取对应用户的所有帖子，按时间倒序
       * @author hengyumo
       * @since 2019/5/28
       */
      List<Comment> findAllByUserIdOrderByCommentTimeDesc(Integer userId);

      List<Comment> findAllByPostId(Integer postId);

      Integer countByUserId(Integer userId);
}
