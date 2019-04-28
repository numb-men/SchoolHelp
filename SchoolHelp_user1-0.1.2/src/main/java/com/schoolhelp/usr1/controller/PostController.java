package com.schoolhelp.usr1.controller;

import com.mysql.cj.jdbc.util.ResultSetUtil;
import com.schoolhelp.usr1.domain.Post;
import com.schoolhelp.usr1.domain.Result;
import com.schoolhelp.usr1.repository.PostRepository;
import com.schoolhelp.usr1.utils.ResultUtil;
import javafx.geometry.Pos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * PostControl
 * TODO
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/27
 **/

@Service
@RestController
public class PostController {

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;

    /**
     * 获取所有用户的帖子信息
     * @author 星夜、痕
     * @since 2019/4/27
     *
     *
     **/
     @GetMapping(value = "/post")
     public List<Post> postList(){
         logger.info("postList");
         return postRepository.findAll();
     }

    /**
     * 添加一个用户
     * @author 星夜、痕
     * @since 2019/4/27
     *
     *
     **/

     @PostMapping(value = "/post")
     public Result<Post> postAdd(@Valid Post post,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultUtil.error(-1,bindingResult.getFieldError().getDefaultMessage());
        }
        post.setId(post.getUsrId());
        post.setApprovalNum(post.getApprovalNum());
        post.setCommentNum(post.getCommentNum());
        post.setIssueTime(post.getIssueTime());
        post.setPoints(post.getPoints());
        post.setPostType(post.getPostType());
        post.setViewNum(post.getViewNum());
        post.setUsrId(post.getUsrId());
        post.setTitle(post.getTitle());
        post.setText(post.getText());
        post.setReportNum(post.getReportNum());

        return ResultUtil.success(postRepository.save(post));
     }


    /**
     * 获取当前用户的所有帖子信息
     * @author 星夜、痕
     * @since 2019/4/27
     *
     *
     **/
    @GetMapping(value = "/user/post/{usrId}")
    public List<Post> getPost(@PathVariable("usrId") Integer useId){
        return postRepository.findByUsrId(useId);
    }


}
