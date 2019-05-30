package com.zgdr.schoolhelp.service;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.PostResultEnum;
import com.zgdr.schoolhelp.enums.UserResultEnum;
import com.zgdr.schoolhelp.exception.GlobalException;
import com.zgdr.schoolhelp.exception.PostException;
import com.zgdr.schoolhelp.exception.UserException;
import com.zgdr.schoolhelp.repository.*;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import javax.validation.constraints.Max;
import java.util.*;

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

    @Resource
    private HeadImageRepository headImageRepository;


    /**
     * 判断用户Id列表中的用户是否已经认证
     * @author hengyumo
     * @since 2019/5/30
     *
     * @param userIds 用户Id列表
     * @return java.util.List<java.lang.Boolean>
     */
    public List<Boolean> checkCertified(List<Integer> userIds){
        List<Boolean> result = new ArrayList<>();
        for (Integer userId : userIds){
            User user = userRepository.getUserById(userId);
            if (user != null){
                result.add(user.getCertified());
            }
            else {
                throw new UserException(UserResultEnum.ID_NOT_FOUND);
            }
        }
        return result;
    }

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
        Date date = new Date();
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setPostId(postId);
        collect.setCollectTime(date);
        collectRepository.saveAndFlush(collect);
        return collect.getCollectId();
    }

    /**
     * 获取用户收藏列表
     * @author hengyumo
     * @since 2019/5/27
     *
     * @param userId 用户id
     * @return jsonObjects
     */
    public Object getUserCollects(Integer userId) {
        List<Collect> collects = collectRepository.findByUserId(userId);
        List<JSONObject> result = new ArrayList<>();
        for (Collect collect : collects){
            JSONObject jsonObject = new JSONObject();
            Post post = postRepository.findByPostIdIn(collect.getPostId());
            jsonObject.put("postId",post.getPostId());
            jsonObject.put("title",post.getTitle());
            jsonObject.put("content",post.getContent());
            jsonObject.put("collectTime",collect.getCollectTime());

            Integer postUserId = post.getUserId();
            HeadImage headImage = headImageRepository.getHeadImageByUserId(postUserId);
            jsonObject.put("imageUrl",(headImage == null?
                    "http://ps0mdxwdu.bkt.clouddn.com/74d5deb3-0921-4e74-acef-0b1fee696c05": headImage.getImageUrl()));
            jsonObject.put("name",post.getUserName());

            result.add(jsonObject);
        }

        return result;
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
        // 收藏数减一
        User user = userRepository.findById(userId).orElse(null);
        user.setCollectPostNum(user.getCollectPostNum() - 1);
        userRepository.saveAndFlush(user);

        collectRepository.delete(collect);
        return null;
    }

    /**
     * 获取用户搜索历史，按时间倒序
     * @author hengyumo
     * @since 2019/4/27
     *
     * @param userId 用户id
     * @return java.util.List<JSONobject> 搜索历史
     */
    public List<JSONObject> getUserSearchHistory(Integer userId) {
        List<Search> searches = searchRepository.findAllByUserIdOrderBySearchTimeDesc(userId);
        List<JSONObject> searchContents = new ArrayList<>();
        for (Search search : searches) {
            if (! search.isHided()) {
                // 如果搜索历史未被隐藏
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("searchId", search.getSearchId());
                jsonObject.put("userId", search.getUserId());
                jsonObject.put("content", search.getContent());
                jsonObject.put("searchTime", search.getSearchTime());
                searchContents.add(jsonObject);
            }
        }
        return searchContents;
    }

    /**
     * 隐藏对应搜索记录
     * @author yangji
     * @since 2019/5/28
     *
     * @param searchId
     */
    public Search hideOneSearch(Integer searchId, Integer userId){

        Search search = searchRepository.findById(searchId).orElse(null);
        if(search == null){
            throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
        }
        if(!userId.equals(search.getUserId())){
            throw new GlobalException(GlobalResultEnum.NOT_POWER);
        }
        search.setHided(true);
        return searchRepository.save(search);

    }

    /**
     * 删除用户某条搜索
     * @author hengyumo
     * @since 2019/5/28
     *
     * @param userId 用户Id
     * @param searchIdDeleted 待删除的搜索Id
     * @return null
     */
    public Object deleteASearchHistory(Integer userId, Integer searchIdDeleted) {
        Search search = searchRepository.findBySearchIdIn(searchIdDeleted);
        if (search == null){
            throw new UserException(UserResultEnum.COLLLECT_ID_NOT_FOUND); // 收藏不存在
        } else if (! search.getUserId().equals(userId)){
            throw new UserException((UserResultEnum.NO_POWER)); // 只能删除自己的收藏
        }

        search.setHided(true);
        searchRepository.save(search);
        return null;
    }

    /**
     * 获取对应用户的所有评论，按时间倒序
     * @author hengyumo
     * @since 2019/5/28
     *
     * @param userId 用户Id
     * @return List<Comment>
     */
    public List<Comment> getUserComments(Integer userId) {
        return commentRepository.findAllByUserIdOrderByCommentTimeDesc(userId);
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
        if (attentionUserId.equals(beAttentionUserId))
        {
            throw new UserException(UserResultEnum.CANT_ATTENTION_YOUSELF);
        }
        if (attentionRepository.findByAttentionUserIdAndBeAttentionUserId(attentionUserId,beAttentionUserId)!=null){
            throw new UserException(UserResultEnum.REPEATED_ATTENTION);
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
        if (attentionUserId.equals(beAttentionUserId))
        {
            throw new UserException(UserResultEnum.CANT_ATTENTION_YOUSELF);
        }
        Attention attention = attentionRepository.findByAttentionUserIdAndBeAttentionUserId(attentionUserId,beAttentionUserId);
        if (attention == null) {
            throw new UserException(UserResultEnum.CANT_CANCEL_ATTENTION);
        }
        // 关注数-1
        User user = userRepository.findById(attentionUserId).orElse(null);
        user.setFollowNum(user.getFollowNum() - 1);
        userRepository.saveAndFlush(user);

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
        if (accept.equals(send)){
            throw new UserException(UserResultEnum.CANT_SEND_SELF);
        }
        User user =  userRepository.findById(accept).orElse(null);
        if (user == null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }
        Date date = new Date();
        Message message = new Message();
        message.setAccet(accept);
        message.setMessageContent(messageContent);
        message.setSendTime(date);
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

    public Integer reportPost(Integer userId,Integer postId,String reportDes){
        if (postRepository.findByPostIdIn(postId) == null){
            throw new UserException(UserResultEnum.CAN_FAND_POST);
        }
        if (reportRepository.findByUserIdAndPostId(userId,postId)!=null){
            throw new UserException(UserResultEnum.HANAV_REPORT);
        }
        if (reportDes != null && "".equals(reportDes)){
            throw new UserException(UserResultEnum.REPORT_NULL);
        }
        Report report = new Report();
        report.setUserId(userId);
        report.setPostId(postId);
        report.setReportDes(reportDes);
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
        int []w = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
        int nums = 0;
        if (f==1)
        {
            for (int i=0;i<17;i++){
                num[i] = chars[i] - '0';
                nums = nums  +  num[i] * w[i];
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
     * @param
     * @return null
     */
    public Object updateUser(Integer userId,String name,String phone,String sex,String studentNum,String major, String college,String mail) {

        if (name == null){
            throw new UserException(UserResultEnum.NAME_NULL);
        }
        if (sex == null){
            throw new UserException(UserResultEnum.SEX_NULL);
        }
        /*
        Student student = studentRepository.findAllByUserId(userId).get(0);
        if (student == null){
            throw new UserException(UserResultEnum.NO_REGISTER);
        }
        */
        if (studentNum == null){
            throw new UserException(UserResultEnum.SCHOOLNAME_NULL);
        }

        if (major == null){
            throw new UserException(UserResultEnum.MAJOR_NULL);
        }
        /*
        Major major = majorRepository.findById(majorId).orElse(null);

        if (major == null){//专业不存在
            throw new UserException(UserResultEnum.MAJOR_NULL);
        }
        */

        if (college == null){
            throw new UserException(UserResultEnum.COLLEGE_NULL);
        }
        /*
        College college = collegeRepository.findById(collegeId).orElse(null);

        if (college == null){   //学院不存在
            throw new UserException(UserResultEnum.COLLEGE_NULL);
        }
        */
        if(mail == null){
            throw new UserException(UserResultEnum.NO_MAIL);
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            throw new UserException(UserResultEnum.USER_NOT_FIND);
        }

        user.setMajor(major);
        user.setId(userId);
        user.setName(name);
        user.setPhone(phone);
        user.setCollege(college);
        user.setStudentNum(studentNum);
        if ("男".equals(sex)){
            user.setSex(true);
        }else if("女".equals(sex)){
            user.setSex(false);
        }else {
            throw new UserException(UserResultEnum.ERROR_SEX);
        }
        user.setMail(mail);
        userRepository.saveAndFlush(user);



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",user.getId());
        jsonObject.put("name",user.getName());
        jsonObject.put("phone",user.getPhone());
        jsonObject.put("sex",user.getSex()?"男":"女");
        jsonObject.put("mail",user.getMail());
        jsonObject.put("studentNum",user.getStudentNum());
        jsonObject.put("major",user.getMajor());
        jsonObject.put("college",user.getCollege());

        return jsonObject;

    }

    /**
     * 获取对应用户的帖子列表
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @return postIds
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

    /**
     * 获取当前用户的关注列表
     * @author 星夜、痕
     * @since 2019/5/27
     *
     * @return jsonObjects
     **/

    public Object getUserAttention(Integer usrId){

        List<Attention> attentions = attentionRepository.findAllByAttentionUserId(usrId);
        List<Integer> beAttentionUserIds = new ArrayList<>();

        for (Attention attention : attentions){
            beAttentionUserIds.add(attention.getBeAttentionUserId());
        }
        List<User> users =  userRepository.findAllById(beAttentionUserIds);


        List<JSONObject> jsonObjects = new ArrayList<>();

        List<HeadImage> headImages = new ArrayList<>();

        for (User user : users){
            HeadImage headImage = headImageRepository.getHeadImageByUserId(user.getId());
            headImages.add(headImage);
        }

        int i = 1;
        for (User user : users){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",user.getId());
            jsonObject.put("name",user.getName());
            jsonObject.put("followNum",user.getFollowNum());
            jsonObject.put("isCertified", user.getCertified());

            HeadImage headImage = headImages.get(i);
            jsonObject.put("imageUrl",headImage.getImageUrl());
            jsonObjects.add(jsonObject);
        }

        return  jsonObjects;

    }

    /**
     * 获取当前用户与对应用户的所有消息
     * @author 星夜、痕
     * @since 2019/5/28
     *
     * @return jsonObjects
     **/
    public Object correspondingMessage(Integer send,Integer accept){

        User sendUser =  userRepository.findById(send).orElse(null);
        User acceptUser =  userRepository.findById(accept).orElse(null);
        if (send.equals(accept)){
            throw new UserException(UserResultEnum.SEND_SELF);
        }
        if (acceptUser == null){
            throw new UserException(UserResultEnum.ID_NOT_FOUND);
        }

        //消息记录
        //消息发送时间
        //当前用户发送给对应用户的消息
        List<Message> sendUserMessages = messgaeRepository.findBySendAndAccet(send,accept);

        //对应用户发送给当前用户的消息
        List<Message> acceptUserMessage = messgaeRepository.findBySendAndAccet(accept,send);

        //将两个消息合并在 allMessage中
        sendUserMessages.addAll(acceptUserMessage);
        List<Message> allMessage =  sendUserMessages;

        Collections.sort(allMessage, new Comparator<Message>() {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @Override
            public int compare(Message o1, Message o2) {

                return (o1.getSendTime().toString()).compareTo(o2.getSendTime().toString());
            }
        });
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (Message message : allMessage){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("messageId",message.getMessageId());
            jsonObject.put("send",message.getSend());
            jsonObject.put("accept",message.getAccet());
            jsonObject.put("headImageUrl",headImageRepository.getHeadImageByUserId(send).getImageUrl());
            jsonObject.put("messageContent",message.getMessageContent());
            jsonObject.put("sendTime",message.getSendTime());
            jsonObject.put("state",message.isState());
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    /**
     * 获取用户的全部信息
     * @author hengyumo
     * @since 2019/5/29
     *
     * @param userId 用户Id
     * @return com.alibaba.fastjson.JSONObject
     */
    public JSONObject getUserAllData(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        HeadImage headImage = headImageRepository.getHeadImageByUserId(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("headImageUrl", headImage.getImageUrl());
        jsonObject.put("id", userId);
        jsonObject.put("name", user.getName());
        jsonObject.put("phone", user.getPhone());
//        jsonObject.put("password", user.getPassword());
        jsonObject.put("sex", user.getSex()?"男":"女");
        jsonObject.put("college", user.getCollege());
        jsonObject.put("major", user.getMajor());
        jsonObject.put("studentNum", user.getStudentNum());
        jsonObject.put("mail", user.getMail());
        jsonObject.put("birthdate", user.getBirthdate());
        jsonObject.put("points", user.getPoints());
        jsonObject.put("collectPostNum", user.getCollectPostNum());
        jsonObject.put("followNum", user.getFollowNum());
        jsonObject.put("postNum", user.getPostNum());
        jsonObject.put("commentNum", user.getCommentNum());
        jsonObject.put("role", user.getRole());
        jsonObject.put("isCertified", user.getCertified());
        jsonObject.put("isOnline", user.getOnline());
//        jsonObject.put("registerTime", user.getPassword());
//        jsonObject.put("lastTime", user.getPassword());

        return jsonObject;
    }

    /**
     * 获取对应用户的个人信息（非隐私部分）
     * @author 星夜、痕
     * @since 2019/4/29
     *
     * @return jsonObject
     */

    public Object getUser(Integer selfUserId, Integer userId){

        User user =  userRepository.findById(userId).orElse(null);
        HeadImage headImage = headImageRepository.findByUserId(userId);
        if (user == null){
            throw new UserException(UserResultEnum.USER_NOT_FIND);
        }
        if (headImage == null){
            throw new UserException(UserResultEnum.NO_AVATAR_EXISTS);
        }
        if (user.getName() == null || user.getCollectPostNum() == null || user.getFollowNum() == null||
                user.getPostNum() ==null || user.getCommentNum()==null){
            throw new UserException(UserResultEnum.INCOMPLETE_USER_INFORMATION);
        }

        JSONObject jsonObject = new JSONObject();

        if (selfUserId == -1){
            jsonObject.put("status", "游客");
        }
        else {
            jsonObject.put("status", "用户");
            Attention attention = attentionRepository.findByAttentionUserIdAndBeAttentionUserId(selfUserId, userId);
            jsonObject.put("hasFollow", attention!=null);
        }

        jsonObject.put("id",user.getId());
        jsonObject.put("name",user.getName());
        jsonObject.put("collectPostNum",user.getCollectPostNum());
        jsonObject.put("followNum",user.getFollowNum());
        Integer followerNum = attentionRepository.countAttentionByBeAttentionUserIdIn(userId);
        jsonObject.put("followerNum", followerNum);
        jsonObject.put("postNum",user.getPostNum());
        jsonObject.put("commentNum",user.getCommentNum());
        jsonObject.put("certified",user.getCertified());
        jsonObject.put("online",user.getOnline());
        jsonObject.put("sex",user.getSex()?"男":"女");
        jsonObject.put("college", user.getCollege());
        jsonObject.put("major", user.getMajor());
        jsonObject.put("headImageUrl",headImage.getImageUrl());
        return jsonObject;
    }
}
