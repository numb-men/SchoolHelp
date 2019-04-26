package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.User;
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

import static org.junit.Assert.*;

/**
 * UserServiceTest
 * TODO
 *
 * @author hengyumo
 * @since 2019/4/17
 * @version 1.0
 */
@Ignore //忽略测试
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //按方法名字典顺序进行顺序测试
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    private static User user;

    @Before
    public void setUp() throws Exception {
        user = new User("name", "12345678901", "123456", true, new Date(),
                100, 10, 5, true, true,
                true, new Date(), new Date()
        );
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void Test1CreateUser() {
        userService.createUser(user);
        Assert.assertEquals((Integer)1, user.getId());
        logger.info("id = {}", user.getId());
    }

    @Test
    public void Test2UpdateUser() {
        user.setName("name2");
        userService.updateUser(user);
        Assert.assertEquals("name2", user.getName());
        logger.info(user.toString());
        logger.info("id = {}", user.getId());
    }

    @Test
    public void Test3ReadUser() {
        logger.info(user.toString());
        Assert.assertTrue(userService.readUserById(1).isOnline());
    }

    @Test
    public void Test4DeleteUserById() {
        logger.info(user.toString());
        userService.deleteUserById(1);
    }
}