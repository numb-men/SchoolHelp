package com.zgdr.schoolhelp.service;

import com.alibaba.fastjson.JSONObject;
import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.enums.PostResultEnum;
import com.zgdr.schoolhelp.enums.UserResultEnum;
import com.zgdr.schoolhelp.exception.PostException;
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

    @Resource
    private ApprovalRepository approvalRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private ReportRepository reportRepository;

    @Resource
    private AttentionRepository attentionRepository;

    @Resource
    private MessgaeRepository messgaeRepository;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private SchoolRepository schoolRepository;

    @Resource
    private CollegeRepository collegeRepository;

    @Resource
    private MajorRepository majorRepository;

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
        if (collectRepository.findByPostIdAndUserIdIn(postId, userId) != null){
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

    /**
     * 获取当前用户的所有帖子信息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 用户id
     * @return java.util.List<java.lang.Integer> postId列表
     **/
    public List<Integer> getUserPosts(Integer userId){
        List<Post> posts = postRepository.findAllByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Post post : posts){
            postIds.add(post.getPostId());
        }
        return postIds;
    }

    /**
     * 获取当前用户点赞过的所有帖子信息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 点赞用户id
     * @return java.util.List<java.lang.Integer> postId列表
     **/
    public List<Integer> getUserApprovalPosts(Integer userId){
        List<Approval> approvals = approvalRepository.findAllByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Approval approval : approvals){
            postIds.add(approval.getPostId());
        }
        return postIds;
    }

    /**
     * 获取当前用户评论过的帖子
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 评论用户id
     * @return java.util.List<java.lang.Integer> postId列表
     **/
    public List<Integer> getUserCommentPosts(Integer userId){
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Comment comment : comments){
            postIds.add(comment.getPostId());
        }
        return postIds;
    }

    /**
     * 获取当前用户举报过的帖子
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 举报用户id
     * @return java.util.List<java.lang.Integer> postId列表
     **/
    public List<Integer> getUserReportPosts(Integer userId){
        List<Report> reports = reportRepository.findAllByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Report report : reports){
            postIds.add(report.getPostId());
        }
        return postIds;
    }

    /**
     * 当前用户关注对应的用户
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param attentionUserId 关注者id
     * @param beAttentionUserId 被关注者id
     * @return Integer Attention列表
     **/
    public Integer attentionUser(Integer attentionUserId,Integer beAttentionUserId){
        User user =  userRepository.findById(beAttentionUserId).orElse(null);
        if (user == null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }
        if (attentionUserId == beAttentionUserId )
        {
            throw new UserException(UserResultEnum.CANT_ATTENTION_YOUSELF);
        }
        Attention attention = new Attention();
        attention.setAttentionUserId(attentionUserId);
        attention.setBeAttentionUserId(beAttentionUserId);
        attentionRepository.saveAndFlush(attention);
        return attention.getAttentionId();
    }

    /**
     * 当前用户取消关注对应的用户
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param attentionUserId 关注者id
     * @param beAttentionUserId 将取消的关注者id
     * @return  null
     */
    public Object deleteUserAttention (Integer attentionUserId,Integer beAttentionUserId){
        if (attentionUserId == beAttentionUserId )
        {
            throw new UserException(UserResultEnum.CANT_ATTENTION_YOUSELF);
        }
        Attention attention = attentionRepository.findByAttentionUserIdAndBeAttentionUserId(attentionUserId,beAttentionUserId);

        attentionRepository.delete(attention);
        return null;
    }

    /**
     * 新建给对应用户的新消息
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param accept 接受者
     * @param messageContent 消息内容
     *  @param send 接受者
     * @return Integer Message列表
     **/
    public Integer newMessage(Integer accept,String messageContent,Integer send){
        if (accept == send){
            throw new UserException(UserResultEnum.CANT_SEND_SELF);
        }
        User user =  userRepository.findById(accept).orElse(null);
        if (user == null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }
        Message message = new Message();
        message.setAccet(accept);
        message.setMessageContent(messageContent);
        message.setSend(send);
        messgaeRepository.saveAndFlush(message);
        return message.getMessageId();
    }

    /**
     * 举报对应帖子
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 举报用户ID
     * @param postId 被举报帖子ID
     * @return Integer Report列表
     **/

    public Integer reportPost(Integer userId,Integer postId){
        if (postRepository.findByPostIdIn(postId) == null){
            throw new UserException(UserResultEnum.CAN_FAND_POST);
        }
        if (reportRepository.findByUserIdAndPostId(userId,postId)!=null){
            throw new UserException(UserResultEnum.HANAV_REPORT);
        }

        Report report = new Report();
        report.setUserId(userId);
        report.setPostId(postId);
        reportRepository.saveAndFlush(report);
        return report.getReportId();
    }

    /**
     * 申请学生认证
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 学生认证的用户id
     * @return java.util.List<java.lang.Integer>
     **/
    /*
    public Integer studentCertification(Integer userId,Integer studentNum){
        Student student = new Student();
        student.setUserId(userId);
        student.setStudentNum(studentNum);
        studentRepository.saveAndFlush(student);
        return student.getSchoolId();
    }
   */
    public Integer studentCertification(Integer userId,Integer studentNum, String relaname, Integer schoolId,
                                        Integer collegeId, Integer majorId, String IdCard){
        if (studentNum == null){
            throw new UserException(UserResultEnum.SCHOOLNAME_NULL);
        }
        if (relaname == null){
            throw new UserException(UserResultEnum.NAME_NULL);
        }

        if (schoolId == null){
            throw new UserException(UserResultEnum.CHOSE_SCHOOL);
        }
        School school = schoolRepository.findById(schoolId).orElse(null);
        if (school == null){   //学校不存在
            throw new UserException(UserResultEnum.SCHOOL_NULL);
        }

        if (collegeId == null){
            throw new UserException(UserResultEnum.CHOSE_COLLEGE);
        }
        College college = collegeRepository.findById(collegeId).orElse(null);
        if (college == null){   //学院不存在
            throw new UserException(UserResultEnum.COLLEGE_NULL);
        }

        if (majorId == null){
            throw new UserException(UserResultEnum.CHOSE_MAJOR);
        }
        Major major = majorRepository.findById(majorId).orElse(null);
        if (major == null){//专业不存在
            throw new UserException(UserResultEnum.MAJOR_NULL);
        }

        if (IdCard == null){
            throw new UserException(UserResultEnum.IDCARD_ERROR);
        }

        String idCard = IdCard;
        int len = idCard.length();
        if(len != 18){
            throw new UserException(UserResultEnum.IDCARD_ERROR);
        }

        int f = 1;//用于判断身份前十七位是否是数字
        char[] chars = idCard.toCharArray();
        for (int i=0;i<len -1; i++){
            if (Character.isDigit(chars[i])){

            }
            else {
                f = 0;
                break;
            }
        }
        int []num = new int[17];
        int []W = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
        int nums = 0;
        if (f==1)
        {
            for (int i=0;i<17;i++){
                num[i] = chars[i] - '0';
                nums = nums  +  num[i] * W[i];
            }
            if (chars[17] == 'x'){
                nums = nums + 10;
            }else {
                nums = nums + (chars[17] - '0');
            }

        }else {
            throw new UserException(UserResultEnum.IDCARD_ERROR);
        }
        if (nums % 11 == 1){  //身份校验正确

        }else {
            throw new UserException(UserResultEnum.IDCARD_ERROR);
        }

        Student student = new Student();
        student.setUserId(userId);
        student.setStudentNum( studentNum);
        student.setRelaname(relaname);
        student.setSchoolId(schoolId);
        student.setCollegeId(collegeId);
        student.setMajorId(majorId);
        student.setIdCard(IdCard);
        studentRepository.saveAndFlush(student);
        return null;
    }

    /**
     * 更新当前用户的资料
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @param userId 用户id
     * @param user
     * @return null
     */
    public Object updateUser(Integer userId, User user) {

        user.setId(userId);
        user.setPassword(user.getPassword());

        int n = 0;
        int ch = 0;
        int len = user.getPassword().length();
        if (len <8 || len >225){
            throw new UserException(UserResultEnum.PASSWORD_MIN_MAX);
        }
        char []chars = user.getPassword().toCharArray();
        for(int i=0; i<len; i++){
            if(Character.isDigit(chars[i])){
                n++;
            }else if (Character.isLowerCase(chars[i])){
                ch++;
            }
        }
        if (n == len){
            throw new UserException(UserResultEnum.PASSWORD_IS_JUST_NUM);
        }
        if (ch == len){
            throw new UserException(UserResultEnum.PASSWOR_IS_JUST_ZM);
        }

        userRepository.save(user);
        return null;
    }

    /**
     * 获取对应用户的帖子列表
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @return
     **/
    public List<Integer> getPosts(Integer userId){
        User user =  userRepository.findById(userId).orElse(null);
        if (user == null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }
        List<Post> posts = postRepository.findAllByUserId(userId);
        List<Integer> postIds = new ArrayList<>();
        for (Post post : posts){
            postIds.add(post.getPostId());
        }
        return postIds;
    }
}
