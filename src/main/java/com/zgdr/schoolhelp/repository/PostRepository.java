package com.zgdr.schoolhelp.repository;


import com.zgdr.schoolhelp.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * postDao
 *
 * @author fishk
 * @version 1.0
 * @since 2019/4/27
 */
public interface PostRepository extends JpaRepository<Post, Integer> {
    /**
     * 通过贴子类型来获得贴子列表
     * @author fishkk
     * @since  2019/4/27
     *
     * @param  id 贴子类型id
     * @return    List<Post>
     */
    @Query(value = "SELECT * FROM post WHERE post_type=?1", nativeQuery = true)
    public List<Post> findPostsByPostType(Integer id);


    /**
     * 通过帮助者id来获得贴子详情
     * @author fishkk
     * @since  2019/4/27
     *
     * @param  id 贴子类型id
     * @return    List<Post>
     */
    @Query(value = "SELECT * FROM post WHERE help_user_id=?1", nativeQuery = true)
    public List<Post> findAllByHelpedUserId(Integer id);


    /**
     * @10 通过贴子id来获得贴子的举报用户id列表
     * @author fishkk
     * @since 2019/4/27
     *
     * @param  keyword 关键词
     * @return   List<Post>
     */
    @Query(value = "SELECT * FROM post WHERE title LIKE %?1%", nativeQuery = true)
    public List<Post> findPostsByKeyword(String keyword);

    Post findByPostIdIn(Integer postID);

    /*星夜、痕添加*/
    List<Post> findAllByUserId(Integer userId);





}
