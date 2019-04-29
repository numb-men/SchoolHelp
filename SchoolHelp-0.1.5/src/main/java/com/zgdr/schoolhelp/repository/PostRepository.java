package com.zgdr.schoolhelp.repository;


import com.zgdr.schoolhelp.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * PostDao
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/4/23
 */
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM post WHERE post_type=?1", nativeQuery = true)
    public List<Post> findPostsByPostType(Integer id);

    @Query(value = "SELECT * FROM post WHERE title LIKE %?1%", nativeQuery = true)
    public List<Post> findPostsByKeyword(String keyword);

    List<Post> findAllByHelpedUserId(Integer helpedUserId);

    /*星夜、痕添加*/
    List<Post> findAllByUserId(Integer userId);

    Post findByPostIdIn(Integer postId);


}
