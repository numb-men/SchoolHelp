package com.zgdr.schoolhelp.controller;

import com.google.gson.JsonObject;
import com.zgdr.schoolhelp.annotation.PassToken;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.service.MessageService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * MessageController
 * 聊天controller
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/28
 */
@Validated
@RestController
public class MessageController {

    @Resource
    private MessageService messageService;

    @PassToken
    @PutMapping(value = "/message/state")
    public Result setMessageState(@RequestParam List<Integer> messageId){
        return Result.success(messageService.setMessageState(messageId));
    }

    @UserLoginToken
    @GetMapping(value = "/message/newmessages")
    public Result getNewMessage(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(messageService.getNewMessage(userId));
    }

    @UserLoginToken
    @GetMapping(value = "/message/chatlist")
    public Result getChatList(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(messageService.getChetList(userId));
    }

    @UserLoginToken
    @GetMapping(value = "/message/chatlist")
    public Result getChatList(HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(messageService.getChetList(userId));
    }
}
