package com.schoolhelp.usr1.utils;


import com.auth0.jwt.JWT;

import javax.servlet.http.HttpServletRequest;

/**
 * TokenUtil
 * token工具
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

public interface TokenUtil {
    /**
     * 从token中取出id
     * @author 星夜、痕
     * @version 1.0
     * @since 2019/4/28
     *
     * @param httpServletRequest http请求
     * @return id
     */

    static Integer getUserIdByRequest(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        return Integer.valueOf(JWT.decode(token).getAudience().get(0));

    }

}
