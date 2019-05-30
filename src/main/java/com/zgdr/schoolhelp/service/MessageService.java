package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.HeadImage;
import com.zgdr.schoolhelp.domain.Message;
import com.zgdr.schoolhelp.repository.HeadImageRepository;
import com.zgdr.schoolhelp.repository.MessgaeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private HeadImageRepository headImageRepository;

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


    /**
     * 获取聊过天的列表
     * @author yangji
     * @since 2019/5/28
     *
     * @param userId 用户的id
     * @return HashMap
     */
    public List<HashMap> getChatList(Integer userId) {
        List<HashMap> clist = new ArrayList<>();
        //作为接收者
        List<Message> list = messgaeRepository.findByAccetOrderBySendTimeDesc(userId);
        //作为发送者
        List<Message> list1 = messgaeRepository.findBySendOrderBySendTimeDesc(userId);
        List<Integer> list2 = new ArrayList<>();
        for (Message message : list) {
            list2.add(message.getSend());
        }
        for (Message message : list1) {
            list2.add(message.getAccet());
        }
        //去掉重复的
        HashSet hashSet = new HashSet(list2);
        list2.clear();
        list2.addAll(hashSet);
        for (Integer id : list2) {
            HashMap hashMap = new HashMap();
            String imageUrl;
            HeadImage headImage = headImageRepository.findByUserId(id);
            if (headImage == null){
                imageUrl = "ps0mdxwdu.bkt.clouddn.com/74d5deb3-0921-4e74-acef-0b1fee696c05";
            }else{
                imageUrl = headImage.getImageUrl();
            }
            String message;
            Date time;
            List<Message> list3 = messgaeRepository.findByAccetAndSendOrderBySendTimeDesc(id, userId);
            List<Message> list4 = messgaeRepository.findByAccetAndSendOrderBySendTimeDesc(userId, id);
            System.out.println(list3.size());
            System.out.println(list4.size());
            if (list3.isEmpty() && !list4.isEmpty()) {
                message = list4.get(0).getMessageContent();
                time = list4.get(0).getSendTime();
            } else if (list4.isEmpty() && !list3.isEmpty()) {
                message = list3.get(0).getMessageContent();
                time = list3.get(0).getSendTime();
            } else if (list3.get(0).getSendTime().getTime() <= list4.get(0).getSendTime().getTime()) {
                message = list4.get(0).getMessageContent();
                time = list4.get(0).getSendTime();
            } else {
                message = list3.get(0).getMessageContent();
                time = list3.get(0).getSendTime();
            }
            Integer num = messgaeRepository.findBySendAndAccetAndState(id, userId, false).size();
            hashMap.put("userId", id);
            hashMap.put("headIimage", imageUrl);
            hashMap.put("latedMessage", message);
            hashMap.put("latedTime", time);
            hashMap.put("newMessageNum", num);
            clist.add(hashMap);
        }
        return clist;
    }
}
