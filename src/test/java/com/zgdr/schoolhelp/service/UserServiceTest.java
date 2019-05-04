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

    private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);


    private User newUser(){
        return new User("name", "12345678901", "12345678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
    }

    private Post newPost(){
        return new Post(1, 2, "帖子标题", "帖子内容", 12,"ads", 200,
                10, 3, 1, "求职", new Date());
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void Test1CreateUser() {
        User user = newUser();
        User returnUser = userService.createUser(user);
        Assert.assertEquals(user.getName(), returnUser.getName());
    }

    @Test
    public void Test2UpdateUser() {
        User user = newUser();
        user.setPhone("12345678921");
        userService.createUser(user);
        user.setName("name2");
        userService.updateUser(user);
        Assert.assertEquals("name2", user.getName());
        logger.info(user.toString());
        logger.info("id = {}", user.getId());
    }

    @Test
    public void Test3ReadUser() {
        User user = newUser();
        user.setPhone("12345678922");
        userService.createUser(user);
        logger.info(user.toString());
        Assert.assertTrue(userService.readUserById(user.getId()).isOnline());
    }

    @Test
    public void Test4DeleteUserById() {
        List<User> users = userService.getAll();

        for (User user : users){
            userService.deleteUserById(user.getId());
        }
    }

    @Test
    public void getHelpedPosts() {
        User user1 = newUser();
        User user2 = newUser();
        user2.setPhone("12345678912");
        userService.createUser(user1);
        userService.createUser(user2);
        Post post = newPost();
        post.setUserId(user1.getId());
        post.setHelpUserId(user2.getId());
        postRepository.save(post);
        // 是否正确找出来helped的帖子id
        Assert.assertEquals(post.getPostId(), userService.getHelpedPosts(user2.getId()).get(0));
    }

    @Test
    public void checkPower() {
        User user = newUser();
        user.setRole(false);
        userService.createUser(user);
        Assert.assertFalse(userService.checkPower(user.getId()));
    }

    @Test
    public void getUserPoints() {
        User user = newUser();
        user.setPoints(100);
        userService.createUser(user);
        Assert.assertEquals((Integer)100, userService.getUserPoints(user.getId()));
    }

    @Test
    public void addUserPoint() {
        User user = newUser();
        user.setPoints(100);
        userService.createUser(user);
        userService.addUserPoint(user.getId(), -20);
        Assert.assertEquals((Integer)80, userService.getUserPoints(user.getId()));
    }

    @Test
    public void getUserSetting() {
        User user = newUser();
        userService.createUser(user);
        Setting setting = new Setting(user.getId(), "特殊", true, true);
        userService.updateUserSetting(user.getId(), setting);

        Assert.assertTrue(userService.getUserSetting(user.getId()).isHidePersonalData());
    }

    @Test
    public void updateUserSetting() {

        User user = newUser();
        userService.createUser(user);
        Setting setting = new Setting(user.getId(), "特殊", true, true);
        userService.updateUserSetting(user.getId(), setting);

        Assert.assertEquals("特殊", userService.getUserSetting(user.getId()).getTheme());
    }


    @Test
    public void collectPost() {
        User user1 = newUser();
        User user2 = newUser();
        user2.setPhone("12345678912");
        userService.createUser(user1);
        userService.createUser(user2);
        Post post = newPost();
        post.setUserId(user1.getId());
        post.setHelpUserId(user2.getId());
        postRepository.save(post);
        userService.collectPost(user1.getId(), post.getPostId());
        Integer postIdFound = userService.getUserCollects(user1.getId()).get(0);
        Assert.assertEquals(post.getPostId(), postIdFound);
    }

    @Test
    public void getUserCollects() {
        User user1 = newUser();
        User user2 = newUser();
        user2.setPhone("12345678912");
        userService.createUser(user1);
        userService.createUser(user2);
        Post post = newPost();
        post.setUserId(user1.getId());
        post.setHelpUserId(user2.getId());
        postRepository.save(post);
        userService.collectPost(user2.getId(), post.getPostId());
        Integer postIdFound = userService.getUserCollects(user2.getId()).get(0);
        Assert.assertEquals(post.getPostId(), postIdFound);
    }

    @Test
    public void deleteUserCollect() {
        User user1 = newUser();
        User user2 = newUser();
        user2.setPhone("12345678912");
        userService.createUser(user1);
        userService.createUser(user2);
        Post post = newPost();
        post.setUserId(user1.getId());
        post.setHelpUserId(user2.getId());
        postRepository.save(post);
        Integer collectId = userService.collectPost(user2.getId(), post.getPostId());
        userService.deleteUserCollect(user2.getId(), collectId);
        Integer foundSize = userService.getUserCollects(user2.getId()).size();
        // 删除后收藏的帖子数目为0
        Assert.assertEquals((Integer)0, foundSize);
    }

    @Test
    public void getUserSearchHistory() {
        User user = newUser();
        userService.createUser(user);
        Search search = new Search();
        search.setUserId(user.getId());
        search.setContent("暑假实习");
        searchRepository.save(search);

        String contentFound = userService.getUserSearchHistory(user.getId()).get(0);
        Assert.assertEquals("暑假实习", contentFound);
    }

    @Test
    public void hideUserSearchHistory() {
        User user = newUser();
        userService.createUser(user);
        Search search = new Search();
        search.setUserId(user.getId());
        search.setContent("暑假实习");
        searchRepository.save(search);

        userService.hideUserSearchHistory(user.getId());
        // 隐藏后返回的搜索历史为空
        Integer foundSize = userService.getUserSearchHistory(user.getId()).size();
        Assert.assertEquals((Integer)0, foundSize);
    }
}