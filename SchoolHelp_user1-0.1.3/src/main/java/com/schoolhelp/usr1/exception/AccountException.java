package com.schoolhelp.usr1.exception;

import com.schoolhelp.usr1.enums.ResultEnum;

/**
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/27
 **/
public class AccountException extends  ExceptionParent{
    public AccountException(ResultEnum resultEnum){
        super(resultEnum);
    }
}
