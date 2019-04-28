package com.schoolhelp.usr1.exception;

import com.schoolhelp.usr1.enums.UserResultEnum;

/**
 * UserException
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

public class UserException extends  ExceptionParent{

    public UserException(UserResultEnum userResultEnum){
        super(userResultEnum);
    }
}
