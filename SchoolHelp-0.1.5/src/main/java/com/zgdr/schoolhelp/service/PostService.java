package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * PostService
 * 帖子管理
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Service
public class PostService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PostRepository postRepository;

    @Resource
    private ApprovalRepository approvalRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private ReportRepository reportRepository;

    @Resource
    private AttentionRepository attentionRepository;

    @Resource
    private MessgaeRepository messgaeRepository;

    @Resource
    private StudentRepository studentRepository;

    /**
     * 获取当前用户的所有帖子信息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 用户id
     * @return java.util.List<java.lang.Integer> postId列表
     **/
    public List<Integer> getUserPosts(Integer userId){
        List<Post> posts = postRepository.findAllByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Post post : posts){
            postIds.add(post.getPostId());
        }

        return postIds;
    }

    /**
     * 获取当前用户点赞过的所有帖子信息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 点赞用户id
     * @return java.util.List<java.lang.Integer> postId列表
     **/
    public List<Integer> gerUserApprovalPosts(Integer userId){
        List<Approval> approvals = approvalRepository.findAllByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Approval approval : approvals){
            postIds.add(approval.getPostId());
        }
        return postIds;
    }

    /**
     * 获取当前用户评论过的帖子
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 评论用户id
     * @return java.util.List<java.lang.Integer> postId列表
     **/
    public List<Integer> gerUserCommentPosts(Integer userId){
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Comment comment : comments){
            postIds.add(comment.getPostId());
        }
        return postIds;
    }

    /**
     * 获取当前用户举报过的帖子
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 举报用户id
     * @return java.util.List<java.lang.Integer> postId列表
     **/
    public List<Integer> getUserReportPosts(Integer userId){
        List<Report> reports = reportRepository.findAllByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Report report : reports){
            postIds.add(report.getPostId());
        }
        return postIds;
    }

    /**
     * 当前用户关注对应的用户
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param attentionUserId 关注者id
     * @param beAttentionUserId 被关注者id
     * @return Integer Attention列表
     **/
    public Integer attentionUser(Integer attentionUserId,Integer beAttentionUserId){

        Attention attention = new Attention();
        attention.setAttentionUserId(attentionUserId);
        attention.setBeAttentionUserId(beAttentionUserId);
        attentionRepository.saveAndFlush(attention);
        return attention.getAttentionId();
    }

/**
 * 当前用户取消关注对应的用户
 * @author 星夜、痕
 * @since 2019/4/29
 *
 * @param attentionUserId 关注者id
 * @param beAttentionUserId 将取消的关注者id
 * @return  null
 */
 public Object deleteUserAttention (Integer attentionUserId,Integer beAttentionUserId){
     Attention attention = attentionRepository.findByAttentionUserIdAndBeAttentionUserId(attentionUserId,beAttentionUserId);

     attentionRepository.delete(attention);
     return null;
 }

    /**
     * 新建给对应用户的新消息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param accept 接受者
     * @param messageContent 消息内容
     * @return Integer Message列表
     **/
    public Integer newMessage(Integer accept,String messageContent){

        Message message = new Message();
        message.setAccet(accept);
        message.setMessageContent(messageContent);
        messgaeRepository.saveAndFlush(message);
        return message.getMessageId();
    }

    /**
     * 举报对应帖子
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 举报用户ID
     * @param postId 被举报帖子ID
     * @return Integer Report列表
     **/

    public Integer reportPost(Integer userId,Integer postId){
        Report report = new Report();
        report.setUserId(userId);
        report.setPostId(postId);
        reportRepository.saveAndFlush(report);
        return report.getReportId();
    }

    /**
     * 申请学生认证
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 学生认证的用户id
     * @param student 学生认证的信息
     * @return java.util.List<java.lang.Integer>
     **/
    public Object studentCertification(Integer userId, Student student){

        Student newstudent = new Student();
        newstudent.setUserId(userId);
        newstudent.setStudentNum(student.getStudentNum());
        newstudent.setRelaname(student.getRelaname());
        newstudent.setSchoolId(student.getSchoolId());
        newstudent.setCollegeId(student.getCollegeId());
        newstudent.setMajorId(student.getMajorId());
        newstudent.setIdCard(student.getIdCard());
        newstudent.setAuthenticateTime(student.getAuthenticateTime());
        studentRepository.saveAndFlush(newstudent);
        return newstudent.getUserId();
    }
}
