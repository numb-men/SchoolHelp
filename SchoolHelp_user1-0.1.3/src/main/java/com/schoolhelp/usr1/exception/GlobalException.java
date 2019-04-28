package com.schoolhelp.usr1.exception;

import com.schoolhelp.usr1.enums.GlobalResultEnum;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.persistence.GeneratedValue;

/**
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/27
 **/
public class GlobalException extends ExceptionParent {
    public GlobalException(GlobalResultEnum globalResultEnum){
        super(globalResultEnum);
    }
}
