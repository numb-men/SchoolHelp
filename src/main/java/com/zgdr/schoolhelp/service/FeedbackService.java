package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.Feedback;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.enums.FeedbackEnum;
import com.zgdr.schoolhelp.exception.FeedbackExceotion;
import com.zgdr.schoolhelp.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 反馈service
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/25
 */
@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private UserService userService;

    /**
     * @Description:   新增意反见馈
     * @Param:  Feedback
     * @return:  Feedback
     * @Author:yangji
     * @Date: 2019/4/25
     */
    public Feedback creatFeedback(Feedback feedback,Integer userId){
        if(feedback.getUserId().intValue()==userId.intValue()) {
            return feedbackRepository.save(feedback);
        }else{
            throw new FeedbackExceotion(FeedbackEnum.FAIL_ADDFEEDBACK);
        }

    }

    /**
     * @Description: 获取反馈列表
     * @Param:
     * @return:List<Feedback>
     * @Author:yangji
     * @Date: 2019/4/25
     */
    public List<Feedback> getAllFeedback(Integer userId){
        User  user=userService.readUserById(userId);
        if(user!=null&&user.getRole()){//管理员才有查看意见反馈的权限
            return feedbackRepository.findAll();
        }else{
            throw new FeedbackExceotion(FeedbackEnum.POWER_LESS);
        }

    }

    /** 
    * @Description: todo 回复反馈意见，需用到聊天模块，留待开发
    * @Param:  
    * @return:  
    * @Author:yangji
    * @Date: 2019/4/25 
    */
    public void doReply(){}
    
    
    /** 
    * @Description:  获取一条反馈意见的详细内容
    * @Param:  Integer id
    * @return:  Feedback
    * @Author:yangji
    * @Date: 2019/4/25 
    */
    public Feedback getFeedbackById(Integer id, Integer userId) throws FeedbackExceotion {
        Feedback feedback=feedbackRepository.findById(id).orElse(null);
        if(feedback==null){
            throw new FeedbackExceotion(FeedbackEnum.UNEXIST_FEEDBACK);
        }
        User  user=userService.readUserById(userId);
        if(user!=null&&user.getRole()) {//管理员才有查看意见反馈的权限
            return feedback;
        }else{
            throw new FeedbackExceotion(FeedbackEnum.POWER_LESS);
        }
    }
}
