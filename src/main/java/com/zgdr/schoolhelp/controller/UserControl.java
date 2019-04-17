package com.zgdr.schoolhelp.controller;

import com.sun.xml.internal.bind.v2.TODO;
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

import javax.validation.Valid;
import java.util.List;

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
public class UserControl {

    @Autowired
    private UserService userService;

    private final static Logger logger = LoggerFactory.getLogger(UserControl.class);

    /**
     * 查询所有用户信息
     * @author hengyumo
     * @since 2019/4/17
     *
     * @return Result
     */
    @GetMapping(value = "/")
    public Result getAll() {
        return Result.success(userService.getAll());
    }

    /**
     * 获取某个用户的信息
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param id 用户id
     * @return Result
     */
    @GetMapping(value = "/id/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        return Result.success(userService.readUserById(id));
    }

    /**
     * 新增用户
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param user 用户
     * @param bindingResult 表单验证结果
     * @return
     */
    @PostMapping(value = "/")
    public Result crateUser(@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            /*
             * TODO 修改错误类型为表单校验错误
             *
             * @author hengyumo
             * @date 2019/4/17
             */
            return Result.error(GlobalResultEnum.UNKNOW_ERROR);
        }
        return Result.success(userService.createUser(user));
    }

    /**
     * 修改某个用户的信息
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param id 用户id
     * @param user 用户
     * @param bindingResult 表单验证结果
     * @return Result
     */
    @PutMapping(value = "/id/{id}")
    public Result updateById(@PathVariable("id") Integer id,
                             @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            /*
             * TODO 修改错误类型为表单校验错误
             *
             * @author hengyumo
             * @date 2019/4/17
             */
            return Result.error(GlobalResultEnum.UNKNOW_ERROR);
        }
        return Result.success(userService.updateUser(user));
    }

    /**
     * 删除某个用户信息
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param id 用户id
     * @return Result
     */
    @DeleteMapping(value = "/id/{id}")
    public Result deleteById(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return Result.success(null);
    }
}
