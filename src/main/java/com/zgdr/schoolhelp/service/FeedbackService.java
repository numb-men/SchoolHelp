package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.Feedback;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.enums.FeedbackEnum;
import com.zgdr.schoolhelp.exception.FeedbackExceotion;
import com.zgdr.schoolhelp.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private FeedbackRepository feedbackRepository;

    @Resource
    private UserService userService;

    /**
     * 新增意反见馈
     * @author yangji
     * @since 2019/4/25
     *
     * @param  feedbackContent 反馈的内容
     * @param  userId  反馈的用户id
     * @return feedback
     */
    public Feedback creatFeedback(String feedbackContent, Integer userId){
       if(feedbackContent.length() > 255 ||  feedbackContent.length() < 10){
            throw new FeedbackExceotion(FeedbackEnum.INVALID_FEEDBACK);
       }
       Feedback feedback=new Feedback(userId, feedbackContent, false, new Date());
       return feedbackRepository.save(feedback);
    }

    /**
     * 获取反馈列表
     * @author yangji
     * @since 2019/4/25
     *
     * @param  userId  属于管理员的id
     * @return List<Feedback>
     */
    public List<Feedback> getAllFeedback(Integer userId){
        User user=userService.readUserById(userId);
        //管理员才有查看意见反馈的权限
        if(user!=null&&user.getRole()){
            return feedbackRepository.findAll();
        }else{
            throw new FeedbackExceotion(FeedbackEnum.POWER_LESS);
        }

    }

    /**
     * todo 回复反馈意见，需用到聊天模块，留待开发
     * @author yangji
     * @since 2019/4/25
     */
    public void doReply(){}

    /**
     * 获取一条反馈意见的详细内容
     * @author yangji
     * @since 2019/4/25
     *
     * @param  id 反馈的id
     * @param  userId  属于管理员的id
     * @return feedback
     */
    public Feedback getFeedbackById(Integer id, Integer userId) throws FeedbackExceotion {
        Feedback feedback=feedbackRepository.findById(id).orElse(null);
        if(feedback==null){
            throw new FeedbackExceotion(FeedbackEnum.UNEXIST_FEEDBACK);
        }
        User user=userService.readUserById(userId);
        //管理员才有查看意见反馈的权限
        if(user!=null&&user.getRole()) {
            return feedback;
        }else{
            throw new FeedbackExceotion(FeedbackEnum.POWER_LESS);
        }
    }
}
