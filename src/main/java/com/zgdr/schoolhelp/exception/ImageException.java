package com.zgdr.schoolhelp.exception;

import com.zgdr.schoolhelp.enums.ResultEnum;

/**
 * t
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/2
 */
public class ImageException extends ExceptionParent{
    public ImageException(ResultEnum resultEnum) {
        super(resultEnum);
    }
}
