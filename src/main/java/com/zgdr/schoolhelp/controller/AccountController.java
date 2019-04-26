package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.PassToken;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.service.AccountService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import com.zgdr.schoolhelp.validators.PhoneValitor;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * AccountController
 * 账号管理
 *
 * @author hengyumo
 * @version 1.5
 * @since 2019/4/25
 */
@Validated
@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 注册
     * @author hengyumo
     * @since 2019/4/25
     *
     * @param phone 手机号
     * @param password 密码，加密后的
     * @return result
     */
    @PassToken
    @PostMapping(value = "/register")
    public Result register(@PhoneValitor @RequestParam(value = "phone") String phone,
                           @Length(max = 255, message = "密码长度超出限制")
                           @RequestParam(value = "password") String password){

        return Result.success(accountService.register(phone, password));
    }

    /**
     * 登录
     * @author hengyumo
     * @since 2019/4/25
     *
     * @param phone 手机号
     * @param password 密码，加密后的
     * @return result
     */
    @PassToken
    @GetMapping(value = "/login")
    public Result login(@PhoneValitor @RequestParam(value = "phone") String phone,
                        @Length(max = 255, message = "密码长度超出限制")
                        @RequestParam(value = "password") String password){

        return Result.success(accountService.login(phone, password));
    }

    /**
     * 修改密码
     * @author hengyumo
     * @since 2019/4/25
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return result
     */
    @UserLoginToken
    @PutMapping(value = "/user/password")
    public Result changePassword(@Length(max = 255, message = "密码长度超出限制")
                                 @RequestParam(value = "oldPassword") String oldPassword,
                                 @Length(max = 255, message = "密码长度超出限制")
                                 @RequestParam(value = "newPassword") String newPassword,
                                 HttpServletRequest httpServletRequest){

        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(accountService.changePassword(userId, oldPassword, newPassword));
    }
}
