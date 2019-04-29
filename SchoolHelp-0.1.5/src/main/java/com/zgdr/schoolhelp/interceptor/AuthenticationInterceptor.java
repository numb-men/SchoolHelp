package com.zgdr.schoolhelp.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zgdr.schoolhelp.annotation.AdminLoginToken;
import com.zgdr.schoolhelp.annotation.PassToken;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.UserResultEnum;
import com.zgdr.schoolhelp.exception.GlobalException;
import com.zgdr.schoolhelp.exception.UserException;
import com.zgdr.schoolhelp.repository.UserRepository;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * AuthenticationInterceptor
 * 全局处理token
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/25
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Resource
    UserRepository userRepository;

    @Override
    public boolean preHandle( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                              Object object) throws Exception {

        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        // 检查是否有@PassToken注解，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 检查有没有需要登录的注解@UserLoginToken
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);

            // 执行认证
            if (userLoginToken.required()) {
                User user = getUserFromToken(token);
                // 验证 token
                return verifyToken(token, user);
            }
        }

        // 检查有没有需要登录的注解@UserLoginToken
        if (method.isAnnotationPresent(AdminLoginToken.class)) {
            AdminLoginToken adminLoginToken = method.getAnnotation(AdminLoginToken.class);

            // 执行认证
            if (adminLoginToken.required()) {
                User user = getUserFromToken(token);
                if (! user.getRole()){
                    // 没有管理员权限
                    throw new UserException((UserResultEnum.NO_POWER));
                }
                // 验证 token
                return verifyToken(token, user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    private User getUserFromToken(String token){
        if (token == null) {
            throw new GlobalException(GlobalResultEnum.NOT_LOGIN);
        }

        // 获取 token 中的 user id
        Integer userId;
        try {
            userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
        } catch (JWTDecodeException j) {
            throw new RuntimeException("check token failed");
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new GlobalException(GlobalResultEnum.USER_NOT_FIND);
        }
        return user;
    }

    private boolean verifyToken(String token, User user){
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new GlobalException(GlobalResultEnum.TOKEN_CHECK_FAILED);
        }
        return true;
    }
}
