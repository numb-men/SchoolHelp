package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Attention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * AttentionRepository
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

public interface AttentionRepository extends JpaRepository <Attention,Integer>{

    //由关注者attentionUserId查询关注表
    public List<Attention> findAllByAttentionUserId(Integer attentionUserId);

    //由关注表ID查询关注表
    public Attention findByAttentionId(Integer attentionId);

    //由被关注者beAttentionUserId和关注者attentionUserId查询关注表
    public Attention findByAttentionUserIdAndBeAttentionUserId(Integer attentionUserId, Integer beAttentionUserId);
}
