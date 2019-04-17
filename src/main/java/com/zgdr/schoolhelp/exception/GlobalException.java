package com.zgdr.schoolhelp.exception;

import com.zgdr.schoolhelp.enums.GlobalResultEnum;

/**
 * GlobalExceptionParent
 * TODO
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public class GlobalException extends ExceptionParent {

    public GlobalException(GlobalResultEnum globalResultEnum){
        super(globalResultEnum);
    }
}
