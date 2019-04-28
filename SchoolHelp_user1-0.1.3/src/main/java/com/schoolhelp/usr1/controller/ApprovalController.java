package com.schoolhelp.usr1.controller;

import com.schoolhelp.usr1.repository.ApprovalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Service
@RestController
public class ApprovalController {

    private final static Logger logger = LoggerFactory.getLogger(ApprovalController.class);

    @Autowired
    private ApprovalRepository approvalRepository;

    /**
     * 获取当前用户点赞过的帖子
     * @author 星夜、痕
     * @since 2019/4/28
     *
     *
     **/
    @GetMapping(value = "/user/approval/post")
    public void getApproval()
    {

    }

}
