package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.AdminLoginToken;
import com.zgdr.schoolhelp.annotation.NotNull;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.domain.Setting;
import com.zgdr.schoolhelp.enums.UserResultEnum;
import com.zgdr.schoolhelp.exception.UserException;
import com.zgdr.schoolhelp.service.UserService;
import com.zgdr.schoolhelp.utils.TokenUtil;
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
}
