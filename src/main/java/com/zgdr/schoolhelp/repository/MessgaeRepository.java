package com.zgdr.schoolhelp.repository;

import com.zgdr.schoolhelp.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * MessgaeRepository
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/
public interface MessgaeRepository extends JpaRepository<Message,Integer> {
    //按接受者 accet 查询
    List<Message> findByAccet(Integer accetId);

    List<Message> findByAccetOrderBySendTimeAsc(Integer accet);

    List<Message> findByAccetOrderBySendTimeAsc(Integer accet);

    //按发送者 send 和接受者查询 accet
    List<Message> findBySendAndAccet(Integer send,Integer accet);

    List<Message> findByAccetAndState(Integer userId, boolean state);

    List<Message> findBySendAndAccetAndState(Integer sendId, Integer accetId, boolean state);

    List<Message> findBySendOrderBySendTimeAsc(Integer sendId);

    List<Message> findByAccetAndSendOrderBySendDesc(Integer acceId, Integer sendId);
}
