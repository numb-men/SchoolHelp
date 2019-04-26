package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.ResultEnum;
import com.zgdr.schoolhelp.repository.UserRepository;
import com.zgdr.schoolhelp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * UserControl
 * TODO
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);


}
