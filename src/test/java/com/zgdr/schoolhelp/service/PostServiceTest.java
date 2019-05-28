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
@Ignore // 忽略测试
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    private static Logger logger = LoggerFactory.getLogger(PostServiceTest.class);

    private static Comment comment;

    private static Post post;

    private static User user;

    private static User user1;

    private HeadImage headImage;

    private HeadImage headImage1;

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

        user = new User("name", "13956708901", "123456d78", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
        user1 = new User("name", "13605678903", "12345378", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
        user = userRepository.saveAndFlush(user);
        user1 = userRepository.saveAndFlush(user1);
        headImage = new HeadImage("yudguysgueyf",user.getId());
        headImage1 = new HeadImage("yudguysgueyf",user1.getId());
        headImage = headImageRepository.save(headImage);
        headImage1 = headImageRepository.save(headImage1);
        comment = new Comment(user.getId(),16,"sadas","halo",new Date(),headImage1.getImageUrl());
        post = new Post(user.getId(),223,"SCHAVCHAACSCY","CASVCYUAGUCYGUACAWSCW",
                10,user.getName(),0, 0,0,0,
                "1",new Date(),headImage.getImageUrl());


    }
    @After
    public void delete(){
        userRepository.delete(user);
        userRepository.delete(user1);
        headImageRepository.delete(headImage);
        headImageRepository.delete(headImage1);
    }
    @Test
    public void getUserAllComment(){
        User user2=userRepository.save(user);
        comment=commentRepository.save(comment);
        List<Comment> list= postService.getUserAllComment(user2.getId());
        for (Comment comment1:list){
            Assert.assertEquals(user2.getId(),comment1.getUserId());
        }
        commentRepository.delete(comment);
    }

    @Test
    public void  createComment(){
        Post post1 = postRepository.save(post);
        comment.setPostId(post1.getPostId());
       Comment comment1 = postService.createComment(comment,user.getId());
        Assert.assertEquals("halo",
                commentRepository.findById(comment1.getCommentId()).orElse(null).getCommentContent());
        commentRepository.delete(comment1);
        postRepository.delete(post1);
    }

    @Test
    public  void createPost(){
       Post post1=postService.createPost(post, user.getId(),null);
        Assert.assertEquals("CASVCYUAGUCYGUACAWSCW",
                postRepository.findByPostIdIn(post.getPostId()).getContent());
        postRepository.delete(post1);
    }

    @Test
    public void getPostPage(){
        Assert.assertEquals(10,postService.getPostPage(0,1).getSize());
    }

    @Test
    public void readPostById(){
       Post post1=postService.createPost(post, user.getId(),null);
        Assert.assertEquals("CASVCYUAGUCYGUACAWSCW",
                postService.readPostById(post.getPostId()).getContent());
        postRepository.delete(post1);
    }

    @Test
    public  void addPostApproval(){
        Post post1=postService.createPost(post, user.getId(),null);
        Approval approval = new Approval(user.getId(),post1.getPostId(),new Date());
        postService.addPostApproval(approval,user.getId());
        Approval approval1 = approvalRepository.findByPostIdAndUserId(post1.getPostId(), user.getId());
       //如果approval1为null，删除会报错
        approvalRepository.delete(approval1);
        postRepository.delete(post1);
    }

    @Test
    public  void deletePostById(){
        Post post1 = postService.createPost(post, user.getId(),null);
        postService.deletePostById( user.getId(), post1.getPostId());
        Assert.assertEquals(null,  postRepository.findById(post1.getPostId()).orElse(null));
    }

    @Test
    public void updatePost(){
       Post post1 = postService.createPost(post, user.getId(),null);
        postService.updatePost(post1.getPostId(), "hkashcjuhackhakschk");
        post1 = postRepository.findByPostIdIn(post.getPostId());
        Assert.assertEquals("hkashcjuhackhakschk", post1.getContent());
        postRepository.delete(post1);
    }

   @Test
    public void createReport(){
        Post post1 = postService.createPost(post, user.getId(),null);
        Report report = new Report(user.getId(),post1.getPostId(),"bascvbsjbvkbvjhdfjhasbcjhabsc",new Date());
        postService.createReport(report,user.getId());
        Report report1= reportRepository.findByUserIdAndPostId(user.getId(), post1.getPostId());
        Assert.assertEquals("bascvbsjbvkbvjhdfjhasbcjhabsc", report1.getReportDes());
        reportRepository.delete(report1);
        postRepository.delete(post1);

    }

    @Test
    public void getPostAndComment(){
        Post post1 = postService.createPost(post, user.getId(),null);
        comment.setPostId(post1.getPostId());
        comment = postService.createComment(comment,user.getId());
        HashMap hashMap=postService.getPostAndComment(post1.getPostId());
        Post post2 = (Post) hashMap.get("post");
        Assert.assertEquals("CASVCYUAGUCYGUACAWSCW", post2.getContent());
        postRepository.delete(post1);
        commentRepository.delete(comment);
    }

    @Test
    public  void getListByKeyword(){
        Post post1 = postService.createPost(post, user.getId(),null);
        String keyword = "UCYGUACAWSCW";
        List<Post> list = postService.findPostByKeyword(keyword);
        for(Post post2 : list){
           Assert.assertEquals(false, post2.getTitle().indexOf(keyword)==-1) ;
        }
        postRepository.delete(post1);
    }

    @Test
    public void findByPostType(){
        Post post1 = postService.createPost(post, user.getId(),null);
        List<Post> postList = postService.findPostsByPostType(1);
        for(Post post2 : postList){
            Assert.assertEquals("1", post2.getPostType());
        }
        postRepository.delete(post1);
    }

    @Test
    public  void submitPost(){
        Post post1=postService.createPost(post, user.getId(),null);
        comment.setPostId(post1.getPostId());
        comment.setUserId(user1.getId());
        comment = postService.createComment(comment,user1.getId());
        postService.sumbitPost(user.getId(),post1.getPostId(), comment.getCommentId());
        user1 = userRepository.findById(user1.getId()).orElse(null);
        Assert.assertEquals("110",user1.getPoints().toString());
        postRepository.delete(post1);
        commentRepository.delete(comment);
    }
}

