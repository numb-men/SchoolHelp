package com.zgdr.schoolhelp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zgdr.schoolhelp.domain.User;
import org.springframework.stereotype.Service;


/**
 * TokenService
 * 生成Token
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/26
 */
@Service
public class TokenService {

    /**
     * 根据User信息生成token
     * @author hengyumo
     * @since 2019/4/25
     *
     * @param user 用户信息
     * @return token
     */
    public String getToken(User user) {
        // 存储id，无过期时间，以 password 作为 token 的密钥
        return JWT.create().withAudience(String.valueOf(user.getId()))
                  .sign(Algorithm.HMAC256(user.getPassword()));
    }
}
