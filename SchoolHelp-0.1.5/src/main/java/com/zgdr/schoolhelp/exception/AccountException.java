package com.zgdr.schoolhelp.exception;

import com.zgdr.schoolhelp.enums.ResultEnum;

/**
 * AccountException
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/25
 */
public class AccountException extends ExceptionParent{
    public AccountException(ResultEnum resultEnum) {
        super(resultEnum);
    }
}
