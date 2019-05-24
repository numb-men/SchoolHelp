package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.PassToken;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.enums.PostResultEnum;
import com.zgdr.schoolhelp.exception.PostException;
import com.zgdr.schoolhelp.service.PostService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * PostControl
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/4/24
 */


@RestController
@RequestMapping(value = "/post")

public class PostController {

    @Autowired
    private PostService postService;

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);

    /**
      * 获取贴子详情
      * @author fishkk
      * @since 2019/4/27
      *
      * @param  id 贴子id
      * @return 贴子以及评论的信息
      */
     @PassToken
     @GetMapping (value = "/id/{id}")
     public Result getPostAllById(@PathVariable("id") Integer id){
         if(postService.isnull(id)){
             return Result.error(PostResultEnum.NOT_FOUND);
         }
         return Result.success(postService.getPostAndComment(id) );
     }


    /**
     * 2通过id获取贴子简要信息
     * @author fishkk
     * @since 2019/4/25
     *
     * @param  id
     * @return 贴子的相关信息
     */
    @PassToken
    @GetMapping(value = "/id/brief/{id}")
    public Result getPostByID(@PathVariable("id") Integer id){
        if(postService.isnull(id)){
            return Result.error(PostResultEnum.NOT_FOUND);
        }
        return Result.success(postService.readPostById(id));
    }

    /**
       * 3 获取最新的xxx条帖子列表
       * @author fishkk
       * @since 2019/4/25
       *
       * @param num
       * @return 返回最新的num条消息 num>贴子总数则返回全部贴子
       */
    @PassToken
    @GetMapping(value = "/num/{num}")
    public Result getLastPostByNum(@PathVariable("num") Integer num){
        return Result.success(postService.getLastPostByNum(num));
    }

    /**
        * 4 获取类别id为xxx的帖子列表
        * @author fishkk
        * @since 2019/4/25
        *
        * @param typeId
        * @return 放回类别id为typeid的贴子列表
        */
    @PassToken
    @GetMapping(value = "/type/{typeId}")
    public Result getPostByTypeId(@PathVariable("typeId") Integer typeId){
        return  Result.success(postService.findPostsByPostType(typeId));
    }

    /**
       * 5搜索关键字为xxxx的帖子，返回列表
       * @author fishkk
       * @since 2019/4/25
       *
       * @param  keyword 关键词
       * @return title中含关键词的贴子列表
       */
    @PassToken
    @GetMapping(value = "/search/{keyword}")
    public Result getPostByKeyword(@PathVariable("keyword") String keyword){
        return  Result.success(postService.findPostByKeyword(keyword));
    }


    /**
      * 6获取热门搜索关键词
      *
      *
      * @author fishkk
      * @since 2019/4/25
      *
      * @param
      * @return 热词列表
      */
    @PassToken
    @GetMapping(value = "/search/hot")
    public Result getHotWord(){
        return Result.success(postService.hotWord());
    }


    /**
     * 7获取贴子的点赞列表
     * @author fishkk
     * @since 2019/4/27
     *
     * @param  postId 贴子id
     * @return 该帖子的点赞用户id列表<Set>
     */
    @PassToken
    @GetMapping(value = "/approval/{postId}")
    public Result getApprovalUser(@PathVariable("postId") Integer postId){
        if(postService.isnull(postId)){
            return Result.error(PostResultEnum.NOT_FOUND);
        }
        return Result.success(postService.getApprovalList(postId));
    }
    
    /**
      * 8获取当前帖子的评论用户列表
      * @author fishkk
      * @since 2019/4/25
      *
      * @param postId 贴子id
      * @return 该帖子的评论用户id列表<Set>
      */
    @PassToken
    @GetMapping(value = "/comment/{postId}")
    public Result getCommentUser(@PathVariable("postId") Integer postId){
        if(postService.isnull(postId)){
            return Result.error(PostResultEnum.NOT_FOUND);
        }
        return  Result.success(postService.getCommentUserList(postId));
    }

    /**
      * 9获取当前帖子的举报用户列表
      * @author fishkk
      * @since 2019/4/25
      *
      * @param postId 贴子id
      * @return 该帖子的举报用户id列表<Set>
      */
    @PassToken
    @GetMapping(value = "/report/{postId}")
    public Result getReportUser(@PathVariable("postId") Integer postId){
        if(postService.isnull(postId)){
            return Result.error(PostResultEnum.NOT_FOUND);
        }
        return  Result.success(postService.getReportUserList(postId));
    }
    
    /**
      * 10获取当前帖子的评论列表
      * @author fishkk
      * @since 2019/4/25
      *
      * @param  postId 贴子id
      * @return  List<Comment> 评论列表
      */
    @PassToken
    @GetMapping(value = "/comment/all/{postId}")
    public Result getPostComment(@PathVariable("postId") Integer postId){
        if(postService.isnull(postId)){
            return Result.error(PostResultEnum.NOT_FOUND);
        }
        return Result.success(postService.getCommentByPostID(postId));
    }


    /**
     * 11点赞
     * @author fishkk
     * @since 2019/4/25
     * token
     * @param  approval
     * @return
     *
     */
    @UserLoginToken
    @PostMapping(value = "/approval")
    public void approval(@Valid Approval approval,BindingResult bindingResult,
                         HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        postService.addPostApproval(approval,userId);
        //return  null;
    }
    
    /**
      * 12评论
      * @author fishkk
      * @since 2019/4/25
      *token
      * @param comment
      * @param bindingResult 表单验证结果
      * @return
      */
    @UserLoginToken
    @PostMapping(value = "/comment")
    public void comment(@Valid Comment comment ,BindingResult bindingResult,
                        HttpServletRequest httpServletRequest){
        if(bindingResult.hasErrors()){
            throw new PostException(PostResultEnum.NOT_COMMENT);
        }
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        postService.createComment(comment,userId);
    }
    
    /**
      * 13当前用户对帖子举报
      * @author fishkk
      * @since 2019/4/25
      *
      * @param report
      * @param bindingResult 表单验证结果
      */
    @UserLoginToken
    @PostMapping(value = "/report")
    public void report(@Valid Report report ,BindingResult bindingResult,
                       HttpServletRequest httpServletRequest){
        if(bindingResult.hasErrors()){
            throw new PostException(PostResultEnum.NO_DES);
        }
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        postService.createReport(report,userId);
    }


    /**
      * 14帖主结贴
      *
      * @author fishkk
      * @since 2019/4/27
      *
      * @param  postId
      * @param submitCommentId 获得积分的评论
      * @return
      */
    @UserLoginToken
    @PostMapping(value = "/submit")
    public void submitPost(@RequestParam("postId") Integer postId,
                           @RequestParam("submitCommentId") Integer submitCommentId,
                           HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
         postService.sumbitPost(userId,postId,submitCommentId);

    }

    /**
     * 15发帖
     * @author fishkk
     * @since 2019/4/25
     *
     * @param post
     * @param bindingResult 表单验证结果
     * @return  贴子对象
     */

    @UserLoginToken
    @PostMapping(value = "")
//    @NotNull(value = "points")
    public Result crateUser(@Valid Post post,
//                          @RequestParam(name = "points") Integer points,
                            BindingResult bindingResult,
                            HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        if(bindingResult.hasErrors()){
            return Result.error(PostResultEnum.NODE);
        }
        if (postService.isRightPoints(post,userId)){
            return Result.error(PostResultEnum.MORE_POINTS);
        }
//        if (post.getPoints().equals(new Object()) ){
//            return Result.error(PostResultEnum.NOT_POINTS);
//        }
        if ( post.getPoints() < 0 ){
            return Result.error(PostResultEnum.ERROR_POINTS);
        }
        return Result.success(postService.createPost(post,userId));
    }


    /**
     * 16删帖自己的帖子
     * @author fishkk
     * @since 2019/4/25
     *
     * @param postId 贴子id
     * @return
     */
    @UserLoginToken
    @DeleteMapping(value = "")
    public void deletePostById(@RequestParam("postId") Integer postId,
                               HttpServletRequest httpServletRequest){

        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        postService.deletePostById(userId, postId);
    }

    /**
      * 17当前用户更新帖子
      * @author fishkk
      * @since 2019/4/25
      *
      * @param postId
      * @param newContent 修改的正文内容
      * @return void
      */
    @UserLoginToken
    @PutMapping(value = "")
    public void updatePost(@RequestParam("postId") Integer postId,
                           @RequestParam("newContent") String newContent){
        if(newContent.isEmpty()){
            throw new PostException(PostResultEnum.NO_DES);
        }
        postService.updatePost(postId , newContent);
    }


}
