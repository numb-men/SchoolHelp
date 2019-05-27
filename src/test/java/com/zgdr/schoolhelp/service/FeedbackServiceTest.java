package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.Feedback;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.repository.FeedbackRepository;
import com.zgdr.schoolhelp.repository.UserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * 用户反馈service单元测试
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/25
 */
//@Ignore // 忽略测试
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackServiceTest {
    @Autowired
    FeedbackService feedbackService;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(FeedbackServiceTest.class);

    private static User user;

    private static Feedback feedback;

    @Before
    public void setUp() throws Exception {
        user = new User("name", "13444678901", "12345678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());;
        feedback = new Feedback(userRepository.saveAndFlush(user).getId(),
                "vsdjhvbdjsjhdvbhsgvdhgs", false,  new Date());
        feedback = feedbackRepository.saveAndFlush(feedback);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.delete(user);
        feedbackRepository.delete(feedback);
    }

    @Test
    public void getAllFeedback() {
        Assert.assertEquals(feedbackRepository.findAll().toString(), feedbackService.getAllFeedback(user.getId()).toString());
    }

    @Test()
    public void creatFeedback() {
        String feedback="ahdsvbjhsdbjvsdfv";
        Feedback feedback1=feedbackService.creatFeedback(feedback,user.getId());
        Assert.assertEquals(feedback,feedback1.getFeedbackContent());
        feedbackRepository.delete(feedback1);
    }

  /*  @Test
    public void doReply() {
    }*/

    @Test
    public void getFeedbackById() {
        Feedback feedback1=feedbackService.getFeedbackById(feedback.getFeedbackId(),user.getId());
     //  logger.info(feedback1.getFeedbackContent());
        Assert.assertEquals("vsdjhvbdjsjhdvbhsgvdhgs", feedback1.getFeedbackContent());
    }
}