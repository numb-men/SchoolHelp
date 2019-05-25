package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.exception.PostException;
import com.zgdr.schoolhelp.repository.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @创建者 fishkk
 * @创建时间 描述
 */
//@Ignore // 忽略测试
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    private static Logger logger = LoggerFactory.getLogger(PostServiceTest.class);

    private static Comment comment;

    private static Post post;

    private static User user;

    private HeadImage headImage;

    @Resource
    private PostRepository postRepository;

    @Resource
    private PostService postService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private HeadImageRepository headImageRepository;

    @Resource
    private ApprovalRepository approvalRepository;

    @Resource
    private ReportRepository reportRepository;

    @Before
    public void setup(){

        user = new User("name", "12345678901", "12345678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
        user = userRepository.saveAndFlush(user);
        headImage = new HeadImage("yudguysgueyf",user.getId());
        headImageRepository.save(headImage);
        comment = new Comment(user.getId(),16,"sadas","halo",new Date(),null);
        post = new Post(user.getId(),223,"SCHAVCHAACSCY","CASVCYUAGUCYGUACAWSCW",
                0,user.getName(),0, 0,0,0,
                "求职",new Date(),null);
    }
    @After
    public void delete(){
        userRepository.delete(user);
        headImageRepository.delete(headImage);
    }

    @Test
    public void  createComment(){
        comment = postService.createComment(comment,user.getId());
        Assert.assertEquals("halo",
                commentRepository.findById(comment.getCommentId()).orElse(null).getCommentContent());
        commentRepository.delete(comment);
    }

    @Test
    public  void createPost(){
        post=postService.createPost(post, user.getId(),null);
        Assert.assertEquals("CASVCYUAGUCYGUACAWSCW",
                postRepository.findByPostIdIn(post.getPostId()).getContent());
        postRepository.delete(post);
    }

    @Test
    public void getPostPage(){
        Assert.assertEquals(10,postService.getPostPage(0).getSize());
    }

    @Test
    public void readPostById(){
        post=postService.createPost(post, user.getId(),null);
        Assert.assertEquals("CASVCYUAGUCYGUACAWSCW",
                postService.readPostById(post.getPostId()).getContent());
        postRepository.delete(post);
    }

    @Test
    public  void addPostApproval(){
        post=postService.createPost(post, user.getId(),null);
        Approval approval = new Approval(user.getId(),post.getPostId(),new Date());
        postService.addPostApproval(approval,user.getId());
        Approval approval1 = approvalRepository.findByPostIdAndUserId(post.getPostId(), user.getId());
       //如果approval1为null，删除会报错
        approvalRepository.delete(approval1);
        postRepository.delete(post);
    }

    @Test(expected = PostException.class)//期望抛出异常
    public  void deletePostById(){
        post = postService.createPost(post, user.getId(),null);
        postService.deletePostById(post.getPostId(), user.getId());
        postRepository.findById(post.getPostId()).orElse(null);
    }

    @Test
    public void updatePost(){
        post = postService.createPost(post, user.getId(),null);
        postService.updatePost(post.getPostId(), "hkashcjuhackhakschk");
        post = postRepository.findByPostIdIn(post.getPostId());
        Assert.assertEquals("hkashcjuhackhakschk", post.getContent());
        postRepository.delete(post);
    }

   @Test
    public void createReport(){
        post = postService.createPost(post, user.getId(),null);
        Report report = new Report(user.getId(),post.getPostId(),"bascjhasbcjhabsc",new Date());
        postService.createReport(report,user.getId());
        Report report1= reportRepository.findByUserIdAndPostId(user.getId(), post.getPostId());
        Assert.assertEquals("bascjhasbcjhabsc", report1.getReportDes());
        reportRepository.delete(report1);
        postRepository.delete(post);

    }

    @Test
    public void getList(){

        postService.getCommentByPostID(16);
        postService.getApprovalList(16);
        postService.getCommentUserList(16);
        postService.getLastPostByNum(16);
        postService.getPostAndComment(16);
        postService.getReportUserList(16);
    }

    @Test
    public void getPostAndComment(){
        Integer postId = 16;
        HashMap hashMap = postService.getPostAndComment(postId);
        logger.info(hashMap.toString());
    }

    @Test
    public void getLastPostByNum(){
        Integer num = 3 ;
        List<Post> list = postService.getLastPostByNum(num);
        logger.info(list.toString());
    }

    @Test
    public  void getListByKeyword(){
        String keyword = "ww";
        List<Post> list = postService.findPostByKeyword(keyword);
        logger.info(list.toString());
    }

    @Test
    public  void submitPost(){

    }
}

