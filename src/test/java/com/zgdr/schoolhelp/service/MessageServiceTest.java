package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.Message;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.repository.MessgaeRepository;
import com.zgdr.schoolhelp.repository.UserRepository;
import org.hibernate.validator.constraints.pl.REGON;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MessageServiceTest
 * 聊天管理单元测试
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    private User sendUser;

    private User accetUser;

    private Message message;

    @Resource
    private MessageService messageService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private MessgaeRepository messageRepository;

    @Before
    public void doBefore(){
        sendUser = new User("name", "13444678901", "12345678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());;
        accetUser = new User("name", "13490678901", "12345q678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());;
        sendUser = userRepository.save(sendUser);
        accetUser = userRepository.save(accetUser);
        message = new Message(sendUser.getId(), accetUser.getId(),
                "yaschabcbajbvjhabdhjbv", "聊天", new Date());

    }

    @After
    public void doAfter(){
        userRepository.delete(sendUser);
        userRepository.delete(accetUser);
    }

    @Test
    public void setMessageState(){
        message = messageRepository.save(message);
        List<Integer> list = new ArrayList<>();
        list.add(message.getMessageId());
        messageService.setMessageState(list);
        Assert.assertEquals(true,
                messageRepository.findById(message.getMessageId()).orElse(null).isState());
    }

    @Test
    public void getNewMessage(){
        message = messageRepository.save(message);
        List<Message> list = messageService.getNewMessage(accetUser.getId());
        if(!list.isEmpty()){
            for (Message m : list){
                Assert.assertEquals(false, m.isState());
            }
        }
        messageRepository.delete(message);
    }
}
