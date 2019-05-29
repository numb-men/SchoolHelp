package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.AdminLoginToken;
import com.zgdr.schoolhelp.annotation.NotNull;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.domain.Setting;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.enums.UserResultEnum;
import com.zgdr.schoolhelp.exception.UserException;
import com.zgdr.schoolhelp.repository.*;
import com.zgdr.schoolhelp.service.UserService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * UserController
 * 用户部分接口
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
@Validated
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private AttentionRepository attentionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessgaeRepository messgaeRepository;

    @Autowired
    StudentRepository studentRepository;


    /**
     * 获取用户帮助成功的帖子列表
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @GetMapping(value = "/helped")
    public Result getHelpedPosts(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.getHelpedPosts(userId));
    }

    /**
     * 删除对应的用户
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userIdDeleted 要删除的用户id
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @DeleteMapping(value = {"/", ""})
    @NotNull(value = "userIdDeleted")
    public Result deleteUser(@RequestParam(name = "userIdDeleted") Integer userIdDeleted,
                             HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
         if (! userService.checkPower(userId)){
             throw new UserException(UserResultEnum.NO_POWER);
         } else if (userId.equals(userIdDeleted)){
             throw new UserException(UserResultEnum.CEN_NOT_DELETE_SELF);
         }
        userService.deleteUserById(userIdDeleted);
        return Result.success(null);
    }

    /**
     * 获取用户积分数
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @GetMapping(value = "/points")
    public Result getUserPoints(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.getUserPoints(userId));
    }

    /**
     * 增加用户积分
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param pointNum 增加的积分数
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @AdminLoginToken
    @GetMapping(value = "/point")
    @NotNull(value = "pointNum")
    public Result addUserPoint(@Max(value = 1000, message = "积分增加不能超过1000")
                               @Min(value = -1000, message = "积分减少不能超过1000")
                               @RequestParam(name = "pointNum") Integer pointNum,
                               HttpServletRequest httpServletRequest){

        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.addUserPoint(userId, pointNum));
    }

    /**
     * 获取用户设置
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @GetMapping(value = "/setting")
    public Result getUserSetting(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.getUserSetting(userId));
    }

    /**
     * 更新用户设置
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param setting 新的设置信息
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @PutMapping(value = "/setting")
    public Result updateUserSetting(@Valid Setting setting, HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.updateUserSetting(userId, setting));
    }

    /**
     * 收藏帖子
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param postId 要收藏的帖子ID
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @PostMapping(value = "/collect")
    @NotNull(value = "postId")
    public Result collectPost(@RequestParam Integer postId, HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.collectPost(userId, postId));
    }

    /**
     * 获取用户收藏的帖子列表
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @GetMapping(value = "/collects")
    public Result getUserCollects(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.getUserCollects(userId));
    }

    /**
     * 取消某个收藏
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param httpServletRequest http请求
     * @param collectIdDeleted 要取消的收藏id
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @DeleteMapping(value = "/collect")
    @NotNull(value = "collectIdDeleted")
    public Result deleteUserCollect(@RequestParam(name = "collectId") Integer collectIdDeleted,
                                    HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.deleteUserCollect(userId, collectIdDeleted));
    }

    /**
     * 获取用户搜索历史
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @GetMapping(value = "/searches")
    public Result getUserSearchHistory(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.getUserSearchHistory(userId));
    }

    /**
     *删除对应搜索记录
     * @author yangji
     * @since 2019/5/28
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @DeleteMapping(value = "/delete/search")
    public Result hideOneSearch(@RequestParam(value = "searchId") Integer searchId,
                                HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.hideOneSearch(searchId ,userId));
    }


    /**
     * 获取用户所有评论
     * @author hengyumo
     * @since 2019/5/28
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @GetMapping(value = "/comments")
    public Result getUserComments(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.getUserComments(userId));
    }


    /**
     * 将用户当前的搜索历史隐藏不显示
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @DeleteMapping(value = "searches")
    public Result hideUserSearchHistoty(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.hideUserSearchHistory(userId));
    }

    /**
     * 删除某条搜索记录
     * @author hengyumo
     * @since 2019/5/28
     *
     * @param searchIdDeleted 待删除的搜索Id
     * @param httpServletRequest Http请求
     * @return com.zgdr.schoolhelp.domain.Result
     */
    @UserLoginToken
    @DeleteMapping(value = "search")
    @NotNull(value = "searchIdDeleted")
    public Result deleteASearchHistoty(@RequestParam(name = "searchId") Integer searchIdDeleted,
                                       HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.deleteASearchHistory(userId, searchIdDeleted));
    }

    /**
     * 获取当前用户的所有帖子信息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    @UserLoginToken
    @GetMapping(value = "/post")
    public Result getUserPosts(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.getUserPosts(userId));
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
    @GetMapping(value = "/approval/post")
    public Result getUserApprovalPosts (HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.getUserApprovalPosts(userId));
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
    @GetMapping(value = "/comment/post")
    public Result getUserCommentPosts (HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.getUserCommentPosts(userId));
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
    @GetMapping(value = "/report/post")
    public Result getUerReportPosts(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return  Result.success(userService.getUserReportPosts(userId));
    }

    /**
     * 获取对应用户的帖子列表
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @return com.zgdr.schoolehelp.domain.Post
     **/
    @GetMapping(value = "/post/{userId}")
    public Result getPosts(@PathVariable("userId") Integer userId){
        return Result.success(userService.getPosts(userId));
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
    @GetMapping(value = "/attention")
    public Result getUserAttention(HttpServletRequest httpServletRequest){
        Integer attentionUserId  = TokenUtil.getUserIdByRequest(httpServletRequest);

        return Result.success(userService.getUserAttention(attentionUserId));
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
    @PostMapping(value = "/attention")
    public Result attentionUser(@RequestParam Integer beAttentionUserId,HttpServletRequest httpServletRequest){
        Integer attentionUserId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.attentionUser(attentionUserId,beAttentionUserId));
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
    @DeleteMapping(value = "/attention")
    public Result deleteUserAttention(@RequestParam(name = "beAttentionUserId") Integer beAttentionUserId,
                                      HttpServletRequest httpServletRequest){
        Integer attentionUserId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.deleteUserAttention(attentionUserId,beAttentionUserId));
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
    @GetMapping(value = {"", "/"})
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

    @GetMapping(value = "/{userId}")
    public Result getUser(@PathVariable("userId") Integer userId){

        return Result.success(userService.getUser(userId));

    }


    /**
     * 更新当前用户的资料
     * @author 星夜、痕
     * @since 2019/4/29
     * @param httpServletRequest http请求
     * @return null
     */
    @UserLoginToken
    @PutMapping(value = {"", "/"})
    public Result updateUser (@Valid User user, HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.updateUser(userId,user));
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
    @GetMapping(value = "/message")
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
    @PostMapping(value = "/message")
    public Result newMessage(@RequestParam String messageContent,
                             @RequestParam Integer accept,
                             HttpServletRequest httpServletRequest){
        Integer send  = TokenUtil.getUserIdByRequest(httpServletRequest);
        if (messageContent != null && "".equals(messageContent)){
            throw new UserException(UserResultEnum.MESSAGE_CANT_NULL);
        }
        return Result.success(userService.newMessage(accept, messageContent,send));
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
    public Result reportPost(@RequestParam Integer postId,HttpServletRequest httpServletRequest,
                             @RequestParam String reportDes){
        Integer userId  = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.reportPost(userId,postId,reportDes));
    }

    /**
     * 申请学生认证
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     **/
    /*
    @UserLoginToken
    @PostMapping(value = "/user/student_apporove")
    public Result studentCertification(@RequestParam Integer studentNum,HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.studentCertification(userId,studentNum));
    }
*/

    @UserLoginToken
    @PostMapping(value = "/student_apporove")
    public Result studentCertification(@RequestParam Integer studentNum,
                                       @RequestParam String relaname,
                                       @RequestParam Integer schoolId,
                                       @RequestParam Integer collegeId,
                                       @RequestParam Integer majorId,
                                       @RequestParam String IdCard,
                                       HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.studentCertification(userId,studentNum ,relaname, schoolId,
                collegeId, majorId, IdCard));
    }

    /**
     * 获取当前用户与对应用户的所有消息
     * @author 星夜、痕
     * @since 2019/5/28
     *
     * @param httpServletRequest http请求
     * @return com.zgdr.schoolhelp.domain.Result
     **/

    @UserLoginToken
    @GetMapping(value = "/message/user")
    public Result newMessage(@RequestParam Integer accept,
                             HttpServletRequest httpServletRequest){
        Integer send  = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(userService.correspondingMessage(send,accept));
    }
}
