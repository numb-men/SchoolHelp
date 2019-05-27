package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;


/**
 * approvalDao
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/4/27
 */

public interface ApprovalRepository extends JpaRepository<Approval, Integer>{
    /**
     * 获取贴子的点赞用户列表
     * @author fishkk
     * @since  2019/4/27
     *
     * @param  id 贴子id
     * @return   Set 点赞用户id集合
     */
    @Query(value = "SELECT user_id FROM approval WHERE post_id=?1", nativeQuery = true)
    public Set<Integer> getListApprovalUser(Integer id);


    /**
     * 删除点赞通过贴子id
     * @author fishkk
     * @since 2019/4/27
     *
     * @param  postId 贴子id
     * @return
     */

    public void deleteByPostId(Integer postId);

    /**
     * 通过用户ID查询点赞表
     *
     * @author 星夜、痕
     * @version 1.0
     * @since 2019/4/28
     **/
    List<Approval> findAllByUserId(Integer userId);

    Approval findByPostIdAndUserId(Integer postId, Integer userId);

}
