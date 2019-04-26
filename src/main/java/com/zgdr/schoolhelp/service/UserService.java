package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.User;
import com.zgdr.schoolhelp.enums.UserResultEnum;
import com.zgdr.schoolhelp.exception.UserException;
import com.zgdr.schoolhelp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserService
 * TODO
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 获取所有用户
     * @author hengyumo
     * @since 2019/4/17
     *
     * @return List<User>
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * 增加用户
     * @author hengyumo
     * @since 2019/4/17
     * 
     * @param user 用户
     * @return User
     */
    public User createUser(User user){
        return userRepository.save(user);
    }

    /**
     * 修改用户
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param user 用户
     * @return User
     */
    public User updateUser(User user) {
        this.readUserById(user.getId());
        return userRepository.save(user);
    }

    /**
     * 查找用户
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param id 用户id
     * @return User
     */
    public User readUserById(Integer id) throws UserException {
        User user =  userRepository.findById(id).orElse(null);
        if (user == null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }
        return user;
    }

    /**
     * 删除用户
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param id 用户id
     */
    public void deleteUserById(Integer id) throws UserException{
        User user = this.readUserById(id);
        userRepository.delete(user);
//        userRepository.findById(id).ifPresent( value ->{
//            userRepository.delete(value);
//        });
    }
}
