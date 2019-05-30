package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.HeadImage;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.enums.AccountResultEnum;
import com.zgdr.schoolhelp.exception.AccountException;
import com.zgdr.schoolhelp.repository.HeadImageRepository;
import com.zgdr.schoolhelp.repository.UserRepository;
import com.zgdr.schoolhelp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

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

    @Resource
    private HeadImageRepository headImageRepository;

    /**
     * 注册新用户
     * @author hengyumo
     * @since 2019/4/25
     *
     * @param phone 手机号
     * @param password 密码，加密过
     * @return token
     */
    @Transactional
    public String register(String phone, String password){
        String image = "http://ps0mdxwdu.bkt.clouddn.com/74d5deb3-0921-4e74-acef-0b1fee696c05";
        if (! StringUtil.isGoodPasseord(password)){
            throw new AccountException(AccountResultEnum.NOT_GOOD_PASSWORD);
        }
        User user = userRepository.findByPhoneIn(phone);
        if (user == null) {
            user = new User();
            user.setName("用户" + StringUtil.getRandomString(5));
            user.setPhone(phone);
            user.setPassword(password);
            user = userRepository.saveAndFlush(user);
            //为每个新注册用户设置一个默认头像
            headImageRepository.save(new HeadImage(image, user.getId()));
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
    @Transactional
    public String login(String phone, String password){
        User user = userRepository.findByPhoneIn(phone);
        checkUser(user, password);

        // 设置在线
        user.setOnline(true);
        user.setLastTime(new Date());
        userRepository.saveAndFlush(user);

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
    @Transactional
    public String changePassword(Integer userId, String oldPassword, String newPassword){
        User user = userRepository.findById(userId).orElse(null);
        checkUser(user, oldPassword);
        if (oldPassword.equals(newPassword)){
            throw new AccountException(AccountResultEnum.PASSWORD_EQUAL);
        }
        user.setPassword(newPassword);
        userRepository.saveAndFlush(user);

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
