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
import java.util.Optional;

/**
 * UserTest
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
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    private final static Logger logger = LoggerFactory.getLogger(UserTest.class);

    private User user;

    @Before
    public void setUp() {
        /*  public User(String name, String mobile, String password, byte sex, Date birthdate,
            Integer points,Integer collectPostNum, Integer fallowNum, byte role, boolean isCertified,
            boolean isOnline, Date registerTime, Date lastTime) */
        user = new User("name", "12345678901", "123456", true, new Date(),
                100, 10, 5, true, true,
                true, new Date(), new Date()
        );
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
    public void test1Intert() {
        User saved = userRepository.save(user);
        Assert.assertEquals(user.toString(), saved.toString());
    }

    /**
     * 测试查找
     * @author hengyumo
     * @since 2019/4/17
     */
    @Test
    public void test2Find() {
        Optional<User> found = userRepository.findById(1);
        found.ifPresent( value -> { //如果查找到了数据
            user.setId(1);
            Assert.assertEquals(user.toString(), value.toString());
        });
    }

    /**
     * 测试更新
     * @author hengyumo
     * @since 2019/4/17
     */
    @Test
    public void test3Update() {
        user.setName("nameChanged");
        userRepository.save(user);
    }

    /**
     * 测试删除
     * @author hengyumo
     * @since 2019/4/17
     */
    @Test
    public void test4Detete() {
        userRepository.deleteById(1);
    }
}