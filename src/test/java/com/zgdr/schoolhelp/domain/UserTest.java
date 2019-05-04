package com.zgdr.schoolhelp.domain;

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
import java.util.Optional;

/**
 * UserTest
 *
 * @author hengyumo
 * @since 2019/4/17
 * @version 1.0
 */
@Ignore //忽略测试
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //按方法名字典顺序进行顺序测试
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    private final static Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @After
    public void tearDown() {
        logger.info("Tear down.");
    }

    /**
     * 测试用户Entity
     * @author hengyumo
     * @since 2019/4/17
     */
    @Test
    public void test1Insert() {
        User user = newUser();
        User saved = userRepository.save(user);
        Assert.assertEquals(user.getPhone(), saved.getPhone());
    }

    /**
     * 测试查找
     * @author hengyumo
     * @since 2019/4/17
     */
    @Test
    public void test2Find() {
        User user = newUser();
        user.setPhone("12345678921");
        userRepository.save(user);
        Optional<User> found = userRepository.findById(user.getId());
        found.ifPresent( value -> { //如果查找到了数据
            Assert.assertEquals(user.getPhone(), value.getPhone());
        });
    }

    /**
     * 测试更新
     * @author hengyumo
     * @since 2019/4/17
     */
    @Test
    public void test3Update() {
        User user = newUser();
        user.setPhone("12345678922");
        String oldName = user.getName();
        userRepository.save(user);
        user.setName("nameChanged");
        userRepository.save(user);
        Assert.assertNotEquals(oldName, user.getName());
    }

    /**
     * 测试删除
     * @author hengyumo
     * @since 2019/4/17
     */
    @Test
    public void test4Detete() {
        List<User> users = userRepository.findAll();

        for (User user : users){
            userRepository.delete(user);
        }
        List<User> nowUsers = userRepository.findAll();
        Assert.assertEquals(0, nowUsers.size());
    }

    private User newUser(){
        return new User("name", "12345678901", "12345678", true, new Date(),
                100, 10, 5, 1, 2,true, true,
                true, new Date(), new Date());
    }
}