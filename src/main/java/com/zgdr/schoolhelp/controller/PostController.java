package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.PassToken;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.enums.PostResultEnum;
import com.zgdr.schoolhelp.exception.PostException;
import com.zgdr.schoolhelp.service.PostService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


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

    @Resource
    private PostService postService;

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);

    /**
     * 分页获取帖子列表
     * @author yangji
     * @since 2019/5/25
     *
     * @return page<post> 分页的帖子
     */
    @PassToken
    @GetMapping (value = "/pages")
    public Result getPostPage(@RequestParam(value = "num") Integer num,
                              @RequestParam(value = "postType")  Integer postType){
        return Result.success(postService.getPostPage(num, postType));
    }


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
     * @param  id 帖子id
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
       * @param num 第几页
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
        * @param typeId 帖子类型id
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
     * @param  approval 点赞的信息
     *
     */
    @UserLoginToken
    @PostMapping(value = "/approval")
    public Result approval(@Valid Approval approval,BindingResult bindingResult,
                         HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success(postService.addPostApproval(approval,userId));
    }
    
    /**
      * 12评论
      * @author fishkk
      * @since 2019/4/25
      *token
      * @param comment 评论信息
      */
    @UserLoginToken
    @PostMapping(value = "/comment")
    public Result comment(@Valid Comment comment ,
                        HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        postService.createComment(comment,userId);
        return Result.success(null);
    }
    
    /**
      * 13当前用户对帖子举报
      * @author fishkk
      * @since 2019/4/25
      *
      * @param report 举报信息
      */
    @UserLoginToken
    @PostMapping(value = "/report")
    public Result report(@Valid Report report,
                       HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        postService.createReport(report,userId);
        return Result.success(null);
    }


    /**
      * 14帖主结贴
      *
      * @author fishkk
      * @since 2019/4/27
      *
      * @param  postId 帖子id
      * @param submitCommentId 获得积分的评论
      */
    @UserLoginToken
    @PostMapping(value = "/submit")
    public Result submitPost(@RequestParam("postId") Integer postId,
                           @RequestParam("submitCommentId") Integer submitCommentId,
                           HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
         postService.sumbitPost(userId,postId,submitCommentId);
        return Result.success(null);
    }

    /**
     * 15发帖
     * @author fishkk
     * @since 2019/4/25
     *
     * @param post 帖子信息
     * @param image 图片数组
     * @return  贴子对象
     */

    @UserLoginToken
    @PostMapping(value = "")
    public Result creatPost(@Valid Post post,
                            @RequestParam(required = false) List<MultipartFile> image,
                            HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        if (postService.isRightPoints(post,userId)){
            return Result.error(PostResultEnum.MORE_POINTS);
        }
        if ( post.getPoints() < 0 ){
            return Result.error(PostResultEnum.ERROR_POINTS);
        }
            return Result.success(postService.createPost(post,userId,image));
    }


    /**
     * 16删帖自己的帖子
     * @author fishkk
     * @since 2019/4/25
     *
     * @param postId 贴子id
     */
    @UserLoginToken
    @DeleteMapping(value = "")
    public Result deletePostById(@RequestParam("postId") Integer postId,
                               HttpServletRequest httpServletRequest){
        Integer userId = TokenUtil.getUserIdByRequest(httpServletRequest);
        postService.deletePostById(userId, postId);
        return Result.success(null);
    }

    /**
      * 17当前用户更新帖子
      * @author fishkk
      * @since 2019/4/25
      *
      * @param postId 帖子id
      * @param newContent 修改的正文内容
      */
    @UserLoginToken
    @PutMapping(value = "")
    public Result updatePost(@RequestParam("postId") Integer postId,
                           @RequestParam("newContent") String newContent){
        if(newContent.isEmpty()){
            throw new PostException(PostResultEnum.NO_DES);
        }
        postService.updatePost(postId , newContent);
        return Result.success(null);
    }


}
