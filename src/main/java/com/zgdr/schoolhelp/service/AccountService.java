package com.zgdr.schoolhelp.service;

import com.alibaba.fastjson.JSONObject;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.enums.AccountResultEnum;
import com.zgdr.schoolhelp.exception.AccountException;
import com.zgdr.schoolhelp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * AccountService
 * 账号管理
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/25
 */
@Service
public class AccountService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TokenService tokenService;

    /**
     * 注册新用户
     * @author hengyumo
     * @since 2019/4/25
     *
     * @param phone 手机号
     * @param password 密码，加密过
     * @return token
     */
    public String register(String phone, String password){
        User user = userRepository.findByPhoneIn(phone);
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setPassword(password);
            userRepository.save(user);
        } else {
            throw new AccountException(AccountResultEnum.HAS_REGISTERED);
        }

        return tokenService.getToken(user);
    }

    /**
     * 登录
     * @author hengyumo
     * @since 2019/4/25
     *
     * @param phone 手机号
     * @param password 密码，加密过
     * @return token
     */
    public String login(String phone, String password){
        User user = userRepository.findByPhoneIn(phone);
        checkUser(user, password);

        return tokenService.getToken(user);
    }

    /**
     * 修改密码
     * @author hengyumo
     * @since 2019/4/25
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 新token
     */
    public String changePassword(Integer userId, String oldPassword, String newPassword){
        User user = userRepository.findById(userId).orElse(null);
        checkUser(user, oldPassword);
        user.setPassword(newPassword);
        userRepository.save(user);

        return tokenService.getToken(user);
    }

    /**
     * 校验用户是否存在、密码是否正确
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param user 用户
     * @param password 密码
     */
    private void checkUser(User user, String password){
        if (user == null) {
            throw new AccountException(AccountResultEnum.NOT_USER);
        }

        if (! user.getPassword().equals(password)){
            throw new AccountException(AccountResultEnum.PASSWORD_ERROR);
        }
    }
}
