package com.zgdr.schoolhelp.exception;

import com.zgdr.schoolhelp.enums.QuesnaireResultEnum;

/**
 * @ProjectName: schoolhelp
 * @Package: com.zgdr.schoolhelp.exception
 * @ClassName: QuesnaireException
 * @Author: yangji
 * @Description: 问卷类异常处理
 * @Date: 2019/4/27 11:15
 * @Version: 1.0
 */
public class QuesnaireException extends ExceptionParent{
    public QuesnaireException(QuesnaireResultEnum quesnaireResultEnum){super(quesnaireResultEnum);}
}
