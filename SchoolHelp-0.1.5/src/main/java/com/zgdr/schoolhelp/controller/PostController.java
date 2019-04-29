package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Post;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.domain.Student;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.repository.AttentionRepository;
import com.zgdr.schoolhelp.repository.MessgaeRepository;
import com.zgdr.schoolhelp.repository.PostRepository;
import com.zgdr.schoolhelp.repository.UserRepository;
import com.zgdr.schoolhelp.service.PostService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * PostControl
 * TODO
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/27
 **/

@Service
@RestController
public class PostController {

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AttentionRepository attentionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessgaeRepository messgaeRepository;

    /**
     * 获取当前用户的所有帖子信息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @GetMapping(value = "/user/post")
    public Result gerUserPosts(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.getUserPosts(userId));
    }

    /**
     * 获取当前用户的点赞过的所有帖子信息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @GetMapping(value = "/user/approval/post")
    public Result gerUserApprovalPosts (HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.gerUserApprovalPosts(userId));
    }

    /**
     * 获取当前用户评论过的帖子
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @GetMapping(value = "/user/comment/post")
    public Result gerUserCommentPosts (HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.gerUserCommentPosts(userId));
    }

    /**
     * 获取当前用户举报过的帖子
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @GetMapping(value = "/user/report/post")
    public Result getUerReportPosts(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return  Result.success(postService.getUserReportPosts(userId));
    }

    /**
     * 获取对应用户的帖子列表
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @return com.zgdr.schoolehelp.domain.Post
     **/
    @GetMapping(value = "/user/post/{userId}")
    public Result getPosts(@PathVariable("userid") Integer userId){
        return Result.success(postRepository.findAllByUserId(userId));
    }

    /**
     * 获取当前用户的关注列表
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @GetMapping(value = "/user/attention")
    public Result getUserAttention(HttpServletRequest httpServletRequest){
        Integer attentionUserId  = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(attentionRepository.findAllByAttentionUserId(attentionUserId));
    }

    /**
     * 当前用户关注对应的用户
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param beAttentionUserId 被关注者id
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @PostMapping(value = "/user/attention")
    public Result attentionUser(@RequestParam Integer beAttentionUserId,HttpServletRequest httpServletRequest){
        Integer attentionUserId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.attentionUser(attentionUserId,beAttentionUserId));
    }

    /**
     * 当前用户取消关注对应的用户
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @param beAttentionUserId 将取消的关注者id
     * @return  null
     */
    @UserLoginToken
    @DeleteMapping(value = "/user/attention")
    public Result deleteUserAttention(@RequestParam(name = "beAttentionUserId") Integer beAttentionUserId,
                                      HttpServletRequest httpServletRequest){
        Integer attentionUserId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.deleteUserAttention(attentionUserId,beAttentionUserId));
    }

    /**
     * 获取当前用户的所有信息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @GetMapping(value = "/user")
    public Result getUserAll(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userRepository.findById(userId));
    }

    /**
     * 获取对应用户的个人信息（非隐私部分）
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @GetMapping(value = "/user/{userId}")
    public Result getUser(@PathVariable("userId") Integer userId){
        return Result.success(userRepository.findById(userId));
    }


    /**
     * 更新当前用户的资料
     * @author 星夜、痕
     * @since 2019/4/29
     * @param httpServletRequest http请求
     * @return null
     */
    @UserLoginToken
    @PutMapping(value = "/user")
    public Result updateUser (@RequestParam("name") String name,
                            @RequestParam("phone") String phone,
                            @RequestParam("password") String password,
                            @RequestParam("sex") Boolean sex,
                            @RequestParam("birthdate") Date birthdate,
                            @RequestParam("points") Integer points,
                            @RequestParam("collectPostNum") Integer collectPostNum,
                            @RequestParam("fallowNum") Integer fallowNum,
                            @RequestParam("role") Boolean role,
                            @RequestParam("isCertified") Boolean isCertified,
                            @RequestParam("isOnline") Boolean isOnline,
                            @RequestParam("registerTime") Date registerTime,
                            @RequestParam("lastTime") Date lastTime,
                            HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        User user = new User();

        user.setId(userId);
        user.setPhone(phone);
        user.setPassword(password);
        user.setSex(sex);
        user.setBirthdate(birthdate);
        user.setPoints(points);
        user.setCollectPostNum(collectPostNum);
        user.setFallowNum(fallowNum);
        user.setRole(role);
        user.setCertified(isCertified);
        user.setOnline(isOnline);
        user.setRegisterTime(registerTime);
        user.setLastTime(lastTime);

        return Result.success(userRepository.save(user));
    }

    /**
     * 查看用户收到的所有信息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @GetMapping(value = "/user/message")
    public Result getUserMessage(HttpServletRequest httpServletRequest){
        Integer accet = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(messgaeRepository.findByAccet(accet));
    }

    /**
     * 新建给对应用户的新消息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @param messageContent 消息内容
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @PostMapping(value = "/user/message")
    public Result newMessage(@RequestParam String messageContent,HttpServletRequest httpServletRequest){
        Integer accept  = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.newMessage(accept,messageContent));
    }

    /**
     * 举报对应帖子
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @param postId 被举报帖子ID
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @PostMapping(value = "/user/report")
    public Result reportPost(@RequestParam Integer postId,HttpServletRequest httpServletRequest){
        Integer userId  = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.reportPost(userId,postId));
    }

    /**
     * 申请学生认证
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @param student 学生认证的信息
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @PostMapping(value = "/user/student_apporove")
    public Object studentCertification(HttpServletRequest httpServletRequest, Student student){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.studentCertification(userId,student));
    }

}
