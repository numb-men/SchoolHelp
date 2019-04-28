package com.zgdr.schoolhelp.exception;

import com.zgdr.schoolhelp.enums.UserResultEnum;

/**
 * UserException
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public class UserException extends ExceptionParent {

    public UserException(UserResultEnum userResultEnum) {
        super(userResultEnum);
    }
}
