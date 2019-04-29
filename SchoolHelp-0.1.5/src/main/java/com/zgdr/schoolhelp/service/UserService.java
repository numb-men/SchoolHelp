package com.zgdr.schoolhelp.service;

import com.alibaba.fastjson.JSONObject;
import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.enums.UserResultEnum;
import com.zgdr.schoolhelp.exception.UserException;
import com.zgdr.schoolhelp.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * UserService
 * 用户管理
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private SettingRepository settingRepository;

    @Resource
    private CollectRepository collectRepository;

    @Resource
    private SearchRepository searchRepository;

    @Resource
    private PostRepository postRepository;

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
        return userRepository.saveAndFlush(user);
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
        return userRepository.saveAndFlush(user);
    }

    /**
     * 查找用户
     * @author hengyumo
     * @since 2019/4/17
     *
     * @param id 用户id
     * @return User
     */
    public User readUserById(Integer id){
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
    public void deleteUserById(Integer id){
        User user = this.readUserById(id);
        userRepository.delete(user);
//        userRepository.findById(id).ifPresent( value ->{
//            userRepository.delete(value);
//        });
    }


    /**
     * 获取用户帮助成功的帖子列表
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户id
     * @return java.util.List<java.lang.Integer> postId列表
     */
    public List<Integer> getHelpedPosts(Integer userId) {
        List<Post> posts = postRepository.findAllByHelpedUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Post post : posts) {
            postIds.add(post.getPostId());
        }

        return postIds;
    }

    /**
     * 确认用户是否有管理员权限
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 待确认的用户id
     * @return boolean 结果
     */
    public boolean checkPower(Integer userId) {
        User user = this.readUserById(userId);
        return user != null && user.getRole();
    }

    /**
     * 获取用户积分
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户id
     * @return java.lang.Integer 积分数
     */
    public Integer getUserPoints(Integer userId) {
        User user = readUserById(userId);
        return user.getPoints();
    }


    /**
     * 增加/减少用户积分
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户id
     * @param pointNum 增加/减少的积分数
     * @return Integer 增加/减少后的积分数
     */
    public Integer addUserPoint(Integer userId, Integer pointNum) {
        User user = readUserById(userId);
        Integer prePoints = user.getPoints();
        if (prePoints + pointNum < 0){
            throw new UserException(UserResultEnum.POINTS_NOT_ENOUGH);
        }
        user.setPoints(prePoints + pointNum);
        userRepository.save(user);

        return user.getPoints();
    }

    /**
     * 获取用户设置
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户id
     * @return Setting 设置信息
     */
    public Setting getUserSetting(Integer userId) {
        Setting setting = settingRepository.findByUserIdIn(userId);
        if (setting == null){
            setting = new Setting(userId, "默认", true, true);
            settingRepository.save(setting);
        }
        return setting;
    }

    /**
     * 更新用户设置
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户id
     * @param setting 新设置信息
     * @return null
     */
    public Object updateUserSetting(Integer userId, Setting setting) {
        setting.setUserId(userId);
        settingRepository.save(setting);
        return null;
    }

    /**
     * 收藏帖子
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户Id
     * @param postId 要收藏的帖子Id
     * @return Integer collectId
     */
    public Integer collectPost(Integer userId, Integer postId) {
        if (postRepository.findByPostIdIn(postId) == null){
            throw new UserException(UserResultEnum.POST_NOT_FOUND);
        }
        if (collectRepository.findByPostIdAndUserIdIn(userId, postId) != null){
            throw new UserException(UserResultEnum.REPEAT_COLLECT);
        }
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setPostId(postId);
        collectRepository.saveAndFlush(collect);
        return collect.getCollectId();
    }

    /**
     * 获取用户收藏列表
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户id
     * @return java.util.List<java.lang.Integer> 帖子postId列表
     */
    public List<Integer> getUserCollects(Integer userId) {
        List<Collect> collects = collectRepository.findByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Collect collect : collects) {
            postIds.add(collect.getPostId());
        }
        return postIds;
    }

    /**
     * 删除用户的某个收藏
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId  用户id
     * @param collectIdDeleted 待删除的收藏id
     * @return null
     */
    public Object deleteUserCollect(Integer userId, Integer collectIdDeleted) {
        Collect collect = collectRepository.findByCollectIdIn(collectIdDeleted);

        if (collect == null){
            throw new UserException(UserResultEnum.COLLLECT_ID_NOT_FOUND); // 收藏不存在
        } else if (! collect.getUserId().equals(userId)){
            throw new UserException((UserResultEnum.NO_POWER)); // 只能删除自己的收藏
        }

        collectRepository.delete(collect);
        return null;
    }

    /**
     * 获取用户搜索历史，按时间倒序
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户id
     * @return java.util.List<java.lang.String> 搜索历史
     */
    public List<String> getUserSearchHistory(Integer userId) {
        List<Search> searches = searchRepository.findAllByUserIdOrderBySearchTimeDesc(userId);
        List<String> searchContents = new ArrayList<>();
        for (Search search : searches) {
            if (! search.isHided()) {
                // 如果搜索历史未被隐藏
                searchContents.add(search.getContent());
            }
        }
        return searchContents;
    }

    /**
     * 隐藏用户的搜索历史
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户id
     * @return null
     */
    public Object hideUserSearchHistory(Integer userId) {
        List<Search> searches = searchRepository.findAllByUserIdAndIsHidedFalse(userId);
        for (Search search : searches) {
            search.setHided(true);
        }
        searchRepository.saveAll(searches);
        return null;
    }
}
