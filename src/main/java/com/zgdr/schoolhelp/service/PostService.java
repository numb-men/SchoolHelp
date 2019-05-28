package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.PostResultEnum;
import com.zgdr.schoolhelp.enums.UserResultEnum;
import com.zgdr.schoolhelp.exception.GlobalException;
import com.zgdr.schoolhelp.exception.PostException;
import com.zgdr.schoolhelp.exception.UserException;
import com.zgdr.schoolhelp.repository.*;
import com.zgdr.schoolhelp.utils.UploadImageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
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

    @Resource
    private PostRepository postRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private ReportRepository reportRepository;

    @Resource
    private ApprovalRepository approvalRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private HeadImageRepository headImageRepository;

    @Resource
    private PostImageRepository postImageRepostiry;

    @Resource
    private UserService userService;

    @Resource
    private SearchRepository searchRepository;


    /**
     * 返回全部贴子信息
     * @author fishkk
     * @since 2019/4/24
     *
     * @return List <post>
     */
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    /**
     * 分页查询，每次返回10条按时间最新的帖子
     * @author yangji
     * @since 2019/5/25
     *
     * @return  Page<Post>
     */
    public Page<Post> getPostPage(Integer num, Integer postType){
        Sort sort = new Sort(Sort.Direction.DESC, "issueTime");
        Pageable pageable = PageRequest.of(num,10, sort);
        return postRepository.findPostsByPostType(pageable,postType.toString());
    }

    /**
     * 删除评论
     * @author yangji
     * @since 2019/5/28
     *
     * @return  Object
     */
    public Object deleteComment(Integer userId, Integer commentId){
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null){
            throw new PostException(PostResultEnum.NOT_FOUND_COMMENT);
        }
        if (!comment.getUserId().equals(userId)){
            throw new UserException(UserResultEnum.NO_POWER);
        }
         commentRepository.delete(comment);
        return null;
    }

    /**
     * 获取用户所有评论
     * @author yangji
     * @since 2019/5/27
     *
     * @return List<Comment> 用户的评论列表
     */
    public List<Comment> getUserAllComment(Integer userId){
        return commentRepository.findAllByUserId(userId);
    }
    /**
     * 创建贴子
     * @author fishkk
     * @since 2019/4/24
     *
     * @param post 帖子信息
     * @return post
     */
    public Post createPost(Post post, Integer userId, List<MultipartFile> image){
        //积分计算的另一种方式
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }
        user.setPoints(user.getPoints()-post.getPoints());
        // 用户发帖数+1
        user.setPostNum(user.getPostNum() + 1);
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
        System.out.println(headImageRepository.findByUserId(userId));
        String headImage = headImageRepository.findByUserId(userId).getImageUrl();
        if(headImage == null){
            throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
        }else{
            post.setHeadImageUrl(headImage);
        }
        post=postRepository.save(post);
        if(image != null){
            if(image.size() > 9){
                throw new PostException(PostResultEnum.TO_MUCH_IMAGE);
            }
            UploadImageUtil uploadImageUtil=new UploadImageUtil();
            for(MultipartFile postImage : image){
                postImageRepostiry.save(new PostImage(post.getPostId(), uploadImageUtil.uploadImage(postImage)));
            }
        }
        return post;
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
     * @param approval 点赞信息
     * @param userId 用户id

     */
    public String addPostApproval(Approval approval, Integer userId) {
        Post post = postRepository.findById(approval.getPostId()).orElse(null);
        if (post == null) {
            throw new PostException(PostResultEnum.NOT_FOUND);
        }
        Approval approval1 = approvalRepository.findByPostIdAndUserId(approval.getPostId(), userId);
        if(approval1!=null){
            post.setApprovalNum(post.getApprovalNum() - 1);
            approvalRepository.deleteById(approval1.getApprovalId());
            postRepository.save(post);
            return "取消点赞";
        }else{
            approval.setUserId(userId);
            post.setApprovalNum(post.getApprovalNum() + 1);
            postRepository.save(post);
            Date date = new Date();
            approval.setApprovalTime(date);
            approvalRepository.save(approval);
            return "点赞成功";
        }
    }


    /**
     * 删除自己的贴子
     * @author fishkk
     * @since 2019/4/24
     *
     * @param userId 用户id
     * @param postId 贴子id
     */
    @Transactional
    public void deletePostById(Integer userId, Integer postId){
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null){
            throw new PostException(PostResultEnum.NOT_FOUND);
        }
        if (! userId.equals(post.getUserId())&&!userService.checkPower(userId)){
            throw new GlobalException(GlobalResultEnum.NOT_POWER);
        }
        reportRepository.deleteByPostId(postId);
        commentRepository.deleteByPostId(postId);
        approvalRepository.deleteByPostId(postId);
        postImageRepostiry.deleteAllByPostId(postId);
        postRepository.delete(post);
    }

    /**
     * 更新贴子
     * @author fishkk
     * @since 2019/4/24
     *
     * @param postId 帖子id
     * @param  newContent 更新内容
     */
    public void updatePost(Integer postId , String newContent){
        Post post = postRepository.findById(postId).orElse(null);
        if(post==null){
            throw new PostException(PostResultEnum.NOT_FOUND);
        }
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
        return postRepository.findById(id).orElse(null) == null;
    }

    /**
     * 创建举报
     * @author fishkk
     * @since 2019/4/24
     *
     * @param report 举报内容
     */
    public void  createReport(Report report, Integer userId){
        if (report.getReportDes() == null){
            throw new PostException(PostResultEnum.NO_DES);
        }
        if(reportRepository.findByUserIdAndPostId(userId, report.getPostId())!=null){
            throw new PostException(PostResultEnum.REPEAT_REPORT);
        }
        report.setUserId(userId);
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
     * @param comment 要新建评论信息
     * @return Comment 存到数据库后返回的评论信息（多了一个评论的id）
     */
    public Comment  createComment(Comment comment, Integer userId){
        comment.setUserId(userId);
        Post post=postRepository.findById(comment.getPostId()).orElse(null);
        if(post == null){
            throw new PostException(PostResultEnum.NOT_FOUND);
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user==null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }
        user.setCommentNum(user.getCommentNum() + 1);
        post.setCommentNum((post.getCommentNum()+1));
        postRepository.save(post);
        userRepository.save(user);
        Date date = new Date();
        comment.setCommentUserName(user.getName());
        comment.setCommentTime(date);
        String headImage = headImageRepository.findByUserId(userId).getImageUrl();
        if(headImage == null){
            throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
        }else{
            comment.setHeadImageUrl(headImage);
        }
        return commentRepository.save(comment);
    }

    /**
      * 获取各种相关列表
      * @author fishkk
      * @since 2019/4/25
      *
      * @param  postId 帖子id
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
     * @param  id 帖子的id
     * @return  HashMap
     */
    public HashMap getPostAndComment(Integer id){
        Post post = postRepository.findById(id).orElse(null);

        List<PostImage> postimage = postImageRepostiry.findByPostId(id);
        List<String> imageUrl = new ArrayList<>();
        if(!postimage.isEmpty()){
            for (PostImage i : postimage){
                imageUrl.add(i.getImageUrl());
            }
        }
        HashMap hashMap = new HashMap();
        List<Comment> commentList = this.getCommentByPostID(id);
        post.setViewNum(post.getViewNum()+1);
        hashMap.put("comments",commentList);
        hashMap.put("post",post);
        hashMap.put("postImages", imageUrl);
        postRepository.save(post);
        return hashMap;
    }


    /**
     * 获取最新的num条贴子
     * @author fishkk
     * @since 2019/4/25
     *
     * @param  num 帖子数
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
        return postRepository.findPostsByPostType(postType.toString());
    }

    public List<Post>  findPostByKeyword(String keyword, Integer userId){
        Search search=new Search(userId, keyword, new Date(), false);
        searchRepository.save(search);
        return postRepository.findPostsByKeyword(keyword);
    }

    public List<Post>  findPostByKeyword(String keyword){
        return postRepository.findPostsByKeyword(keyword);
    }


    /**
     * 结贴
     * @author fishkk
     * @since 2019/4/24
     *
     * @param userId 用户id
     * @param  postId 帖子id
     * @param  commentId 评论的id
     */
    public void sumbitPost(Integer userId ,Integer postId ,Integer commentId){
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment == null){
            throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
        }
        //获取获得积分的用户
        Integer user1 = comment.getUserId();
        User userget =  userRepository.findById(user1).orElse(null);
        if(userget == null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }
        //获取贴子积分
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null){
            throw new PostException(PostResultEnum.NOT_FOUND);
        }
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
     * @param  userId 用户id
     * @return  Boolean
     */
    public Boolean isRightPoints(Post post, Integer userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }
        return user.getPoints()<post.getPoints();
    }

    public List<String> hotWord(){
        List x = new ArrayList();
      //  x.add("二手书交易");
       // x.add("面试");
       // x.add("实习");
       // x.add("生活");
       // x.add("运动");
       // x.add("学习");
        return  x;
    }
}
