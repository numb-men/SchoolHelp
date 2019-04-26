package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.exception.AccountException;
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
 * AccountServiceTest
 * 测试AccountService
 *
 * @author hengyumo
 * @since 2019/4/27
 * @version 1.0
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名字典顺序进行顺序测试
public class AccountServiceTest {


    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    private static User user;

    @Before
    public void setUp() throws Exception {
        user = new User("name", "12345678901", "12345678901", true, new Date(),
                100, 10, 5, true, true,
                true, new Date(), new Date()
        );
        userRepository.save(user);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = AccountException.class) // 期望抛出异常
    public void test1Register() {
        // 用户已存在
        accountService.register("12345678901", "12345678901");
    }

    @Test(expected = AccountException.class)
    public void test2Login() {
        // 用户不存在
        accountService.login("12345678902", "12345678901");
    }

    @Test(expected = AccountException.class)
    public void test3Login() {
        // 密码错误
        accountService.login("12345678901", "1234567890");
    }

    @Test(expected = AccountException.class)
    public void test4ChangePassword() {
        // 旧密码错误
        accountService.changePassword(user.getId(), "1234567890", "12345678901");
    }
}