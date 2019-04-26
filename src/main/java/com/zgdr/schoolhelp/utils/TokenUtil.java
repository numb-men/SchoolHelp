package com.zgdr.schoolhelp.utils;

import com.auth0.jwt.JWT;

import javax.servlet.http.HttpServletRequest;

/**
 * TokenUtil
 * token工具
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/25
 */
public interface TokenUtil {
    /**
     * 从token中取出id
     * @author hengyumo
     * @since 2019/4/25
     *
     * @param httpServletRequest http请求
     * @return id
     */
    static Integer getUserIdByRequest(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        return Integer.valueOf(JWT.decode(token).getAudience().get(0));
    }
}
