package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.Feedback;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @ProjectName: schoolhelp
 * @Package: com.zgdr.schoolhelp.service
 * @ClassName: FeedbackServiceTest
 * @Author: yangji
 * @Description:
 * @Date: 2019/4/25 20:14
 * @Version: 1.0
 */
@Ignore // 忽略测试
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackServiceTest {
    @Autowired
    FeedbackService feedbackService;
  /*   @Test
   public void getAllFeedback() {
        feedbackService.getAllFeedback(1);
    }*/

    @Test
    public void creatFeedback() {
        Feedback feedback=new Feedback(2,"学生街vtvyvyvyvyvyvyvytvyvy好吃",false,new Date());
        System.out.println(feedback.toString());
        Feedback feedback1=feedbackService.creatFeedback(feedback,2);
        Assert.assertEquals(feedback.toString(),feedback1.toString());
    }



  /*  @Test
    public void doReply() {
    }*/

 /*   @Test
    public void getFeedbackById() {
        Feedback feedback=feedbackService.getFeedbackById(4,2);
        System.out.println(feedback.toString());
    }*/
}