package com.zgdr.schoolhelp.exception;

import com.zgdr.schoolhelp.enums.FeedbackEnum;

/**
 * @ProjectName: schoolhelp
 * @Package: com.zgdr.schoolhelp.exception
 * @ClassName: FeedbackExceotion
 * @Author: yangji
 * @Description: 反馈意见异常
 * @Date: 2019/4/25 20:01
 * @Version: 1.0
 */
public class FeedbackExceotion extends ExceptionParent {
    public FeedbackExceotion(FeedbackEnum feedbackEnum) {
        super(feedbackEnum);
    }
}
