package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.PostResultEnum;
import com.zgdr.schoolhelp.exception.GlobalException;
import com.zgdr.schoolhelp.exception.PostException;
import com.zgdr.schoolhelp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * PostService
 * TODO
 *
 * @author fishkk
 * @version 1.0
 * @since 2019/4/23
 */


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ApprovalRepository approvalRepository;


    @Autowired
    private UserRepository userRepository;


    /**
     * 返回全部贴子信息
     * @author fishkk
     * @since 2019/4/24
     *
     * @param
     * @return List <post>
     */
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    /**
     * 创建贴子贴子
     * @author fishkk
     * @since 2019/4/24
     *
     * @param post
     * @return post
     */
    public Post createPost(Post post, Integer userId){
//        积分计算的另一种方式

        User user = userRepository.findById(userId).orElse(null);
        user.setPoints(user.getPoints()-post.getPoints());
        user.setPostNum(user.getPostNum() + 1); // 用户发帖数+1
        userRepository.save(user);
        post.setUserName(user.getName());
        post.setPoints(post.getPoints());
        post.setUserId(userId);
        post.setPostType(post.getPostType());
        post.setCommentNum(0);
        post.setReportNum(0);
        post.setViewNum(0);
        post.setApprovalNum(0);
        post.setHelpUserId(-1);
        Date date = new Date();
        post.setIssueTime(date);
        return postRepository.save(post);
    }

    /**
     * 作者查看贴子
     * @author fishkk
     * @since 2019/4/24
     *
     * @param id 贴子id
     * @return post
     */
    public Post readPostById(Integer id){
        Post post = postRepository.findById(id).orElse(null);
        if (post == null){
            throw new PostException(PostResultEnum.NOT_FOUND);
        }

        return post;
    }

    /**
     * 其他用户查看贴子功能
     * @author fishkk
     * @since 2019/4/24
     *
     * @param id 贴子id
     * @return void
     */
    public Post otherReadPostById(Integer id){
        Post post=postRepository.findById(id).orElse(null);

        if(post != null){
            post.setViewNum(post.getViewNum()+1);
            return postRepository.save(post);
        }
        return null;
    }

    /**
     * 点赞功能
     * @author fishkk
     * @since 2019/4/24
     *
     * @param approval
     * @return App
     */
    public Integer addPostApproval(Approval approval, Integer userId) {
        List<Approval> approvalList = approvalRepository.findAll();
        Post post = postRepository.findById(approval.getPostId()).orElse(null);
        if (post == null) {
            throw new PostException(PostResultEnum.NOT_FOUND);
        }
        for (Approval approval1 : approvalList) {
            if ( ((int)approval1.getPostId()==(int)approval.getPostId()) &&((int)approval1.getUserId()==(int)userId)) {
                post.setApprovalNum(post.getApprovalNum() - 1);
                approvalRepository.deleteById(approval1.getApprovalId());
                return null;
            }
        }

        approval.setUserId(userId);//

        post.setApprovalNum(post.getApprovalNum() + 1);
        postRepository.save(post);
        Date date = new Date();
        approval.setApprovalTime(date);
        approvalRepository.save(approval);
        return null ;
    }


    /**
     * 删除自己的贴子
     * @author fishkk
     * @since 2019/4/24
     *
     * @param userId 用户id
     * @param postId 贴子id
     * @return void
     */
    @Transactional
    public void deletePostById(Integer userId, Integer postId){
        Post post = this.readPostById(postId);
        if (! userId.equals(post.getUserId())){
            throw new GlobalException(GlobalResultEnum.NOT_POWER);
        }
        reportRepository.deleteByPostId(postId);
        commentRepository.deleteByPostId(postId);
        approvalRepository.deleteByPostId(postId);
        postRepository.delete(post);
    }

    /**
     * 更新贴子
     * @author fishkk
     * @since 2019/4/24
     *
     * @param postId
     * @param  newContent 更新内容
     * @return void
     */
    public void updatePost(Integer postId , String newContent){

        Post post = postRepository.findById(postId).orElse(null);
        post.setContent(newContent);
        postRepository.save(post);
    }

    /**
     * 判断贴子是否为空
     * @author fishkk
     * @since 2019/4/24
     *
     * @param id 贴子id
     * @return Boolean
     */
    public Boolean isnull(Integer id){
        if(postRepository.findById(id).orElse(null) == null){
            return true;
        }
        return false;
    }

    /**
     * 创建举报
     * @author fishkk
     * @since 2019/4/24
     *
     * @param report
     * @return void
     */
    public void  createReport(Report report, Integer userId){
        if (report.getReportDes() == null){
            throw new PostException(PostResultEnum.NO_DES);
        }
        report.setUserId(userId);//
        Post post=postRepository.findById(report.getPostId()).orElse(null);
        if(post == null){
            throw new PostException(PostResultEnum.NOT_FOUND);
        }
        post.setReportNum(post.getReportNum()+1);
        postRepository.save(post);
        Date date = new Date();
        report.setReportTime(date);
        reportRepository.save(report);
    }


    /**
     * 创建评论
     * @author fishkk
     * @since 2019/4/24
     *
     * @param comment
     * @return void
     */
    public void  createComment(Comment comment, Integer userId){
        comment.setUserId(userId);
        Post post=postRepository.findById(comment.getPostId()).orElse(null);
        if(post == null){
            throw new PostException(PostResultEnum.NOT_FOUND);
        }
        User user = userRepository.findById(userId).orElse(null);
        user.setCommentNum(user.getCommentNum() + 1);
        post.setCommentNum((post.getCommentNum()+1));
        postRepository.save(post);
        userRepository.save(user);
        Date date = new Date();
        comment.setCommentUserName(user.getName());
        comment.setCommentTime(date);
        commentRepository.save(comment);
    }

    /**
      * 获取各种相关列表
      * @author fishkk
      * @since 2019/4/25
      *
      * @param  postId
      * @return  相关的set或者list
      */
    public List<Comment> getCommentByPostID(Integer postId){
       return commentRepository.getCommentByPostId(postId);
    }

    public Set<Integer> getApprovalList(Integer postId){
        return approvalRepository.getListApprovalUser(postId);
    }

    public Set<Integer> getCommentUserList(Integer postId){
        return commentRepository.getListCommentUser(postId);
    }

    public Set<Integer> getReportUserList(Integer postId){
        return reportRepository.getListReportUser(postId);
    }


    /**
     * 获取全部评论详情
     * @author fishkk
     * @since 2019/4/25
     *
     * @param  id
     * @return  HashMap
     */
    public HashMap getPostAndComment(Integer id){
        Post post = postRepository.findById(id).orElse(null);
        HashMap hashMap = new HashMap();
        List<Comment> commentList = this.getCommentByPostID(id);
        post.setViewNum(post.getViewNum()+1);
        hashMap.put("comments",commentList);
        hashMap.put("post",post);
        postRepository.save(post);
        return hashMap;
    }


    /**
     * 获取最新的num条贴子
     * @author fishkk
     * @since 2019/4/25
     *
     * @param  num
     * @return  返回最新的num条贴子
     */
    public List<Post>  getLastPostByNum(Integer num){
        List<Post> posts= this.getAll();
        List<Post> posts1 =new ArrayList<>() ;
        int lenth = posts.size();
        if(lenth < num){
            return posts;
        }
        posts.toArray();
        for (int i=lenth-num, k =0 ;i<lenth ; i++,k++){
           posts1.add(posts.get(i));
        }
        return posts1;
    }

    public List<Post>  findPostsByPostType(Integer postType){
        return postRepository.findPostsByPostType(postType);
    }

    public List<Post>  findPostByKeyword(String keyword){
        return postRepository.findPostsByKeyword(keyword);
    }


    /**
     * 结贴
     * @author fishkk
     * @since 2019/4/24
     *
     * @param userId
     * @param  postId
     * @param  commentId
     * @return
     */
    public void sumbitPost(Integer userId ,Integer postId ,Integer commentId){

        Comment comment = commentRepository.findById(commentId).orElse(null);
        //       获取获得积分的用户
        Integer user1 =  comment.getUserId();
        User userget =  userRepository.findById(user1).orElse(null);
//        获取贴子积分
        Post post = postRepository.findById(postId).orElse(null);
        Integer points = post.getPoints();
        userget.setPoints(userget.getPoints()+points);
        post.setPoints(0);
        post.setHelpUserId(user1);
        postRepository.save(post);
        userRepository.save(userget);
    }


    /**
     * 判断积分是否充足
     * @author fishkk
     * @since 2019/4/25
     *
     * @param  userId
     * @return  Boolean
     */
    public Boolean isRightPoints(Post post, Integer userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user.getPoints()<post.getPoints()){
            return true;
        }
        return false;
    }

    public List<String> hotWord(){
        List x = new ArrayList();
        x.add("二手书交易");
        x.add("面试");
        x.add("实习");
        x.add("生活");
        x.add("运动");
        x.add("学习");
        return  x;
    }
}
