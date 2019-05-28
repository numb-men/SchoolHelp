package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.Message;
import com.zgdr.schoolhelp.repository.MessgaeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * MessageService
 * 聊天管理
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/28
 */

@Service
public class MessageService {

    @Resource
    private MessgaeRepository messgaeRepository;

    /**
     * 设置消息状态为已读
     * @author yangji
     * @since 2019/5/28
     *
     * @param list 保存信息id的数组
     * @return Object
     */
    public Object setMessageState(List<Integer> list){
        for (Integer messageId : list){
            Message message = messgaeRepository.findById(messageId).orElse(null);
            message.setState(true);
            messgaeRepository.save(message);
        }
        return null;
    }

    /**
     * 查找一个用户未读的message
     * @author yangji
     * @since 2019/5/28
     *
     * @param userId 用户的id
     * @return List<Message>
     */
    public List<Message> getNewMessage(Integer userId){
        List<Message> list = messgaeRepository.findByAccetAndState(userId,false);
        return list;
    }

}
