package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.AdminLoginToken;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Feedback;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.repository.UserRepository;
import com.zgdr.schoolhelp.service.FeedbackService;
import com.zgdr.schoolhelp.service.TokenService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 反馈controller类
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/27
 */
@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    
    
    /** 
    * @Description: 新增用户反馈 
    * @Param:  
    * @return:  
    * @Author:yangji
    * @Date: 2019/4/25 
    */
    @UserLoginToken//验证用户是否已登录，登录后才能新建反馈
    @PostMapping(value = "/")
    public Result creatFeedback(@Valid Feedback feedback, HttpServletRequest httpServletRequest) {
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        feedbackService.creatFeedback(feedback,userId);
        return Result.success(null);
    }


    /**
    * @Description:  获取反馈列表所有反馈
    * @Param:
    * @return:  Result中的DATA为一个List<Feedback>列表
    * @Author:yangji
    * @Date: 2019/4/26
    */
    @AdminLoginToken
    @GetMapping(value = "/")
    public Result getAllFeedback(HttpServletRequest httpServletRequest){
        Integer userId=TokenUtil.getUserIdByRequest(httpServletRequest);
        return  Result.success(feedbackService.getAllFeedback(userId));
    }
    
    
    /** 
    * @Description: 根据反馈信息的id查看反馈的详细内容
    * @Param:  反馈信息的id
    * @return:  Result中的DATA对象为一个Feedback对象
    * @Author:yangji
    * @Date: 2019/4/26 
    */
    @AdminLoginToken
    @GetMapping(value = "/details")
    public Result getFeedbackById(@RequestParam(value = "id") Integer id,
                                  HttpServletRequest httpServletRequest){
        Integer userId=TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(feedbackService.getFeedbackById(id, userId));
    }

}
