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
      /**
       *  放回贴子的评论列表
       * @author fishkk
       * @since 2019/4/27
       *
       * @param  id 贴子id
       * @return    评论列表List<Comment>
       */
      @Query(value = "SELECT * FROM comment WHERE post_id=?1", nativeQuery = true)
      public List<Comment> getCommentByPostId(Integer id);

      /**
       * 10获取贴子的评论用户列表
       * @author fishkk
       * @since  2019/4/27
       *
       * @param   id 贴子id
       * @return   Set 评论用户id集合
       */
      @Query(value = "SELECT user_id FROM comment WHERE post_id=?1", nativeQuery = true)
      public Set<Integer> getListCommentUser(Integer id);
}
