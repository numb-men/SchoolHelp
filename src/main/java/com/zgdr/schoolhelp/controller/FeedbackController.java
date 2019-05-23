package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.AdminLoginToken;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.repository.UserRepository;
import com.zgdr.schoolhelp.service.FeedbackService;
import com.zgdr.schoolhelp.service.TokenService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.hibernate.validator.constraints.Length;
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
     * 新增用户反馈
     * @author yangji
     * @since 2019/4/25
     *
     * @param feedbackContent 反馈的内容
     * @return result
     */
    @UserLoginToken
    @PostMapping(value = "/")
    public Result creatFeedback(@Length(min = 10, max = 255, message = "反馈内容不能少于10个字，多于255个字")
                                @Valid String feedbackContent,
                                HttpServletRequest httpServletRequest) {
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        feedbackService.creatFeedback(feedbackContent,userId);
        return Result.success(null);
    }

    /**
     * 获取反馈列表所有反馈
     * @author yangji
     * @since 2019/4/26
     *
     * @return result
     */
    @AdminLoginToken
    @GetMapping(value = "/")
    public Result getAllFeedback(HttpServletRequest httpServletRequest){
        Integer userId= TokenUtil.getUserIdByRequest(httpServletRequest);
        return  Result.success(feedbackService.getAllFeedback(userId));
    }

    /**
     * 根据反馈信息的id查看反馈的详细内容
     * @author yangji
     * @since 2019/4/26
     *
     * @param id 帖子的id
     * @return result
     */
    @AdminLoginToken
    @GetMapping(value = "/details")
    public Result getFeedbackById(@RequestParam(value = "id") Integer id,
                                  HttpServletRequest httpServletRequest){
        Integer userId= TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(feedbackService.getFeedbackById(id, userId));
    }
}
