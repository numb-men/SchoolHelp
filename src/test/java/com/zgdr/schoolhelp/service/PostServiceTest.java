package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.exception.PostException;
import com.zgdr.schoolhelp.repository.*;
import org.junit.*;
import org.junit.runner.RunWith;
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

    private static User user1;

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
        user1 = new User("name", "12345678903", "12345378", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
        user = userRepository.saveAndFlush(user);
        user1 = userRepository.saveAndFlush(user1);
        headImage = new HeadImage("yudguysgueyf",user.getId());
        headImageRepository.save(headImage);
        comment = new Comment(user.getId(),16,"sadas","halo",new Date(),null);
        post = new Post(user.getId(),223,"SCHAVCHAACSCY","CASVCYUAGUCYGUACAWSCW",
                0,user.getName(),0, 0,0,0,
                "1",new Date(),null);
    }
    @After
    public void delete(){
        userRepository.delete(user);
        userRepository.delete(user1);
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
    public void getPostAndComment(){
        post=postService.createPost(post, user.getId(),null);
        comment.setPostId(post.getPostId());
        comment = postService.createComment(comment,user.getId());
        HashMap hashMap=postService.getPostAndComment(post.getPostId());
        Post post1 = (Post) hashMap.get("post");
        Assert.assertEquals("CASVCYUAGUCYGUACAWSCW", post1.getContent());
        postRepository.delete(post);
        commentRepository.delete(comment);
    }

    @Test
    public  void getListByKeyword(){
        post=postService.createPost(post, user.getId(),null);
        String keyword = "UCYGUACAWSCW";
        List<Post> list = postService.findPostByKeyword(keyword);
        for(Post post1:list){
           Assert.assertEquals(false, post1.getContent().indexOf(keyword)==-1) ;
        }
        postRepository.delete(post);
    }

    @Test
    public void findByPostType(){
        post=postService.createPost(post, user.getId(),null);
        List<Post> postList = postService.findPostsByPostType(1);
        for(Post post1:postList){
            Assert.assertEquals("1", post1.getPostType());
        }
        postRepository.delete(post);
    }

    @Test
    public  void submitPost(){
        post=postService.createPost(post, user.getId(),null);
        comment.setPostId(post.getPostId());
        comment.setUserId(user1.getId());
        comment = postService.createComment(comment,user.getId());
        postService.sumbitPost(user.getId(),post.getPostId(), comment.getCommentId());
        Assert.assertEquals("100",user.getPoints().toString());
        postRepository.delete(post);
        commentRepository.delete(comment);
    }
}

