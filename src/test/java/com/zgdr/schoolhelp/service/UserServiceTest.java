package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.Search;
import com.zgdr.schoolhelp.domain.Setting;
import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.repository.*;
import com.zgdr.schoolhelp.repository.UserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * UserServiceTest
 *
 * @author hengyumo
 * @since 2019/4/17
 * @version 1.0
 */
@Ignore // 忽略测试
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名字典顺序进行顺序测试
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private MessgaeRepository messgaeRepository;

    @Autowired
    private AttentionRepository attentionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private CommentRepository commentRepository;

    private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);


    private User newUser(){
        return new User("name", "13009348901", "12345678dqw", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
    }

    private Post newPost(){
        return new Post(1, 2, "帖子vsvvs标题", "帖子内容sdvsvseddvs", 12,"ads", 200,
                10, 3, 1, "求职", new Date(),"asd");
    }

    /**
     *newMessage
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    private Message newMessage(){
        return new Message(1,2,"今天也要加油！","交友",new Date());
    }

    /**
     *newAttention
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    private Attention newAttenton(){
        return new Attention(1,2,new Date());
    }

    /**
     *newuserFind
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/


    /**
     *newStudent
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    public  Student newStudent(){
        return new Student(221600205,"chenhongbao",1,1,1,"532526199705310517",new Date());
    }

    /**
     *newApproval
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    public Approval newApproval(){
        return  new Approval(1,1,new Date());
    }

    /**
     *newComment
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    public Comment newComment(){
        return new Comment(1,1,"叫爸爸","用户1",new Date(),"vsdvdfbfgn");
    }

    /**
     *newAttention
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    public Report newReport(){
        return new Report(1,1,"帖vhd csjhdvbjhsdvbsjhdbvjsbdv 子涉及违法营销信息",new Date());
    }

    @Before
    public void start(){

    }
    @After
    public void delete(){

    }


    @Test
    public void Test1CreateUser() {
        User user = newUser();
        User returnUser = userService.createUser(user);
        Assert.assertEquals(user.getName(), returnUser.getName());
        userRepository.delete(returnUser);
    }

    @Test
    public void Test2UpdateUser() {
        User user = newUser();
        user.setPhone("12345678921");
        userService.createUser(user);
        user.setName("name2");
        user=userService.updateUser(user);
        Assert.assertEquals("name2", user.getName());
        logger.info(user.toString());
        logger.info("id = {}", user.getId());
        userRepository.delete(user);
    }

    @Test
    public void Test3ReadUser(){
        User user = newUser();
        user.setPhone("12345678922");
        user = userService.createUser(user);
        logger.info(user.toString());
        Assert.assertTrue(userService.readUserById(user.getId()).isOnline());
        userRepository.delete(user);
    }

    @Test
    public void Test4DeleteUserById() {
        User user = newUser();
        user = userService.createUser(user);
        userService.deleteUserById(user.getId());
        Assert.assertEquals(null,userRepository.findById(user.getId()).orElse(null));
        userRepository.delete(user);
    }

    @Test
    public void getHelpedPosts() {
        User user1 = newUser();
        User user2 = newUser();
        user2.setPhone("13045678912");
        user1=userService.createUser(user1);
        user2=userService.createUser(user2);
        Post post = newPost();
        post.setUserId(user1.getId());
        post.setHelpUserId(user2.getId());
        post=postRepository.save(post);
        // 是否正确找出来helped的帖子id
        Assert.assertEquals(post.getPostId(), userService.getHelpedPosts(user2.getId()).get(0));
        postRepository.delete(post);
        userRepository.delete(user1);
        userRepository.delete(user2);
    }

    @Test
    public void checkPower() {
        User user = newUser();
        user.setRole(false);
        user=userService.createUser(user);
        Assert.assertFalse(userService.checkPower(user.getId()));
        userRepository.delete(user);
    }

    @Test
    public void getUserPoints() {
        User user = newUser();
        user.setPoints(100);
        user = userService.createUser(user);
        Assert.assertEquals((Integer)100, userService.getUserPoints(user.getId()));
        userRepository.delete(user);
    }

    @Test
    public void addUserPoint() {
        User user = newUser();
        user.setPoints(100);
        user = userService.createUser(user);
        userService.addUserPoint(user.getId(), -20);
        Assert.assertEquals((Integer)80, userService.getUserPoints(user.getId()));
        userRepository.delete(user);
    }

    @Test
    public void getUserSetting() {
        User user = newUser();
        user = userService.createUser(user);
        Setting setting = new Setting(user.getId(), "特殊", true, true);
        userService.updateUserSetting(user.getId(), setting);
        Assert.assertTrue(userService.getUserSetting(user.getId()).isHidePersonalData());
        userRepository.delete(user);
    }

    @Test
    public void updateUserSetting() {

        User user = newUser();
        user=userService.createUser(user);
        Setting setting = new Setting(user.getId(), "特殊", true, true);
        userService.updateUserSetting(user.getId(), setting);
        Assert.assertEquals("特殊", userService.getUserSetting(user.getId()).getTheme());
        userRepository.delete(user);
    }

/*
    @Test
    public void collectPost() {
        User user1 = newUser();
        User user2 = newUser();
        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);
        Post post = newPost();
        post.setUserId(user1.getId());
        post.setHelpUserId(user2.getId());
        post = postRepository.save(post);
        userService.collectPost(user1.getId(), post.getPostId());
        Integer postIdFound = userService.getUserCollects(user1.getId()).get(0);
        Assert.assertEquals(post.getPostId(), postIdFound);
        userRepository.delete(user1);
        userRepository.delete(user2);
        postRepository.delete(post);
    }
*/
/*
    @Test
    public void getUserCollects() {
        User user1 = newUser();
        User user2 = newUser();
        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);
        Post post = newPost();
        post.setUserId(user1.getId());
        post.setHelpUserId(user2.getId());
        post = postRepository.save(post);
        userService.collectPost(user2.getId(), post.getPostId());
        Integer postIdFound = userService.getUserCollects(user2.getId()).get(0);
        Assert.assertEquals(post.getPostId(), postIdFound);
        userRepository.delete(user1);
        userRepository.delete(user2);
        postRepository.delete(post);
    }
*/
/*
    @Test
    public void deleteUserCollect() {
        User user1 = newUser();
        User user2 = newUser();
        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);
        Post post = newPost();
        post.setUserId(user1.getId());
        post.setHelpUserId(user2.getId());
        post = postRepository.save(post);
        Integer collectId = userService.collectPost(user2.getId(), post.getPostId());
        userService.deleteUserCollect(user2.getId(), collectId);
        Integer foundSize = userService.getUserCollects(user2.getId()).size();
        // 删除后收藏的帖子数目为0
        Assert.assertEquals((Integer)0, foundSize);
        userRepository.delete(user1);
        userRepository.delete(user2);
        postRepository.delete(post);
    }
*/
    @Test
    public void getUserSearchHistory() {
        User user = newUser();
        user = userService.createUser(user);
        Search search = new Search();
        search.setUserId(user.getId());
        search.setContent("暑假实习");
        search = searchRepository.save(search);
        String contentFound = userService.getUserSearchHistory(user.getId()).get(0).getString("content");
        Assert.assertEquals("暑假实习", contentFound);
        userRepository.delete(user);
        searchRepository.delete(search);
    }

    @Test
    public void hideOneSearch(){
        User user = newUser();
        user = userService.createUser(user);
        Search search = new Search(user.getId(), "哪个食堂好吃", new Date(), false);
        search = searchRepository.save(search);
        search = userService.hideOneSearch(search.getSearchId(), user.getId());
        Assert.assertEquals(true,search.isHided());
        userRepository.delete(user);
        searchRepository.delete(search);
    }

    @Test
    public void hideUserSearchHistory() {
        User user = newUser();
        user = userService.createUser(user);
        Search search = new Search();
        search.setUserId(user.getId());
        search.setContent("暑假实习");
        search = searchRepository.save(search);

        userService.hideUserSearchHistory(user.getId());
        // 隐藏后返回的搜索历史为空
        Integer foundSize = userService.getUserSearchHistory(user.getId()).size();
        Assert.assertEquals((Integer)0, foundSize);
        userRepository.delete(user);
        searchRepository.delete(search);
    }

    /**
     * 测试
     * 获取当前用户的所有帖子信息
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    @Test
    public void getUserPosts(){
        User user1 = newUser();
        User user2 = newUser();

        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);
        Post post = newPost();
        post.setUserId(user1.getId());
        post.setUserId(user2.getId());
        post = postRepository.save(post);
        // 是否正确找出来当前用户的所有帖子id
        Assert.assertEquals(post.getPostId(),userService.getUserPosts(user2.getId()).get(0));
        userRepository.delete(user1);
        userRepository.delete(user2);
        postRepository.delete(post);
    }

    /**
     * 测试
     * 获取当前用户点赞过的所有帖子信息
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/

    @Test
    public void getUserApprovalPosts(){
        User user1 = newUser();
        User user2 = newUser();

        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);
        Approval approval = newApproval();
        approval.setUserId(user1.getId());
        approval = approvalRepository.save(approval);
        Assert.assertEquals(userService.getUserApprovalPosts(user1.getId()).get(0),approval.getPostId());
        userRepository.delete(user1);
        userRepository.delete(user2);
        approvalRepository.delete(approval);
    }



    /**
     * 测试
     * 获取当前用户评论过的帖子
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/

    @Test
    public void getUserCommentPosts(){
        User user1 = newUser();
        User user2 = newUser();

        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);

        Comment comment = newComment();
        comment.setUserId(user1.getId());
        comment = commentRepository.save(comment);

        // 是否正确找出来评论过的帖子id
        Assert.assertEquals(userService.getUserCommentPosts(user1.getId()).get(0),comment.getPostId());
        userRepository.delete(user1);
        userRepository.delete(user2);
        commentRepository.delete(comment);
    }


    /**
     * 测试
     * 获取当前用户举报过的帖子
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/

    @Test
    public void getUserReportPosts(){
        User user1 = newUser();
        User user2 = newUser();

        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);

        Report report = newReport();
        report.setUserId(user1.getId());
        report = reportRepository.save(report);
        // 是否正确找出来举报过的帖子id
        Assert.assertEquals(report.getPostId(),userService.getUserReportPosts(user1.getId()).get(0));
        userRepository.delete(user1);
        userRepository.delete(user2);
        reportRepository.delete(report);
    }

    /**
     * 测试
     * 当前用户关注对应的用户
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/

    @Test
    public void attentionUser(){
        User user1 = newUser();
        User user3 = new User("name", "13900760869", "12345678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());

        user1 = userService.createUser(user1);
        user3 = userService.createUser(user3);
        Integer attentionIdFound = userService.attentionUser(user1.getId(),user3.getId());
        Assert.assertEquals(attentionIdFound,attentionRepository.getOne(attentionIdFound).getAttentionId());
        userRepository.delete(user1);
        userRepository.delete(user3);
    }


    /**
     * 测试
     * 当前用户取消关注对应的用户
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    @Test
    public void deleteUserAttention(){
        User user1 = newUser();
        User user2 = newUser();

        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);

        Attention attention = newAttenton();
        attention.setAttentionUserId(user1.getId());
        attention.setAttentionUserId(user2.getId());
        attention = attentionRepository.save(attention);

        Assert.assertEquals(null, userService.deleteUserAttention(user2.getId(),attention.getBeAttentionUserId()));
        userRepository.delete(user1);
        userRepository.delete(user2);
        attentionRepository.delete(attention);

    }

    /**
     * 测试
     * 新建给对应用户的新消息
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    @Test
    public void theNewMessage(){
        User user1 = newUser();
        User user2 = newUser();
        User user3 = new User("name", "13600760869", "12345678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);
        user3 = userService.createUser(user3);


        Integer message1Id =  userService.newMessage(user1.getId(),"user1创建成功",user3.getId());

        Assert.assertEquals(message1Id,messgaeRepository.getOne(message1Id).getMessageId());
        userRepository.delete(user1);
        userRepository.delete(user2);
        userRepository.delete(user3);
    }

    /**
     * 测试
     * 举报对应帖子
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/

    @Test
    public void reportPost(){
        User user1 = newUser();
        User user2 = newUser();
        User user3 = new User("name", "13600760869", "12345678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
        user2.setPhone("13045678912");
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);
        user3 = userService.createUser(user3);
        Post post = newPost();
        post = postRepository.save(post);
        Integer report1Id =  userService.reportPost(user3.getId(),post.getPostId(),"消息内veiuhruaivbaerb vear容存在问题");
        Assert.assertEquals(report1Id,reportRepository.getOne(report1Id).getReportId());
        userRepository.delete(user1);
        userRepository.delete(user2);
        userRepository.delete(user3);
        postRepository.delete(post);
    }

    /**
     * 测试
     * 申请学生认证
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
/*
    @Test
    public void studentCertification(){
        User user3 = new User("name", "98765432112", "12345678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
        userService.createUser(user3);

        Student student = new  Student(221600205,"chenhongbao",1,1,1,"532526199705310517",new Date());
        student.setUserId(user3.getId());
        studentRepository.save(student);

        Assert.assertEquals(null,userService.studentCertification(user3.getId(),student.getStudentNum(),student.getRelaname(),student.getSchoolId(),
                student.getCollegeId(),student.getMajorId(),student.getIdCard()));

    }
*/
    /**
     * 测试
     * 更新当前用户的资料
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/
    @Test
    public void updateUser(){
        User user1 = newUser();
        user1.setPassword("dfsfs32132132");
        user1 = userService.createUser(user1);

        User user2 = new User("name", "13645678901", "12345678fdsasfaf", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());

        userService.updateUser(user1.getId(),user2);

        Assert.assertEquals("name",user1.getName());
        userRepository.delete(user1);
    }

    /**
     * 测试
     * 获取对应用户的帖子列表
     *
     * @author 星夜、痕
     * @since 2019/5/24
     **/

    @Test
    public void getPosts(){
        User user2 = new User("name", "13645678901", "12345678fdsasfaf", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
        user2 = userService.createUser(user2);

        Post post = newPost();
        post.setUserId(user2.getId());
        post = postRepository.save(post);

        Integer postIdFound = userService.getPosts(user2.getId()).get(0);
        Assert.assertEquals(post.getPostId(), postIdFound);
        userRepository.delete(user2);
        postRepository.delete(post);
    }

}