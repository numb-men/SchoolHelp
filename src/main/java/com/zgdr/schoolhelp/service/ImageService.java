package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.Comment;
import com.zgdr.schoolhelp.domain.HeadImage;
import com.zgdr.schoolhelp.domain.Post;
import com.zgdr.schoolhelp.domain.RollImage;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.ImageResultEnum;
import com.zgdr.schoolhelp.exception.GlobalException;
import com.zgdr.schoolhelp.exception.ImageException;
import com.zgdr.schoolhelp.repository.CommentRepository;
import com.zgdr.schoolhelp.repository.HeadImageRepository;
import com.zgdr.schoolhelp.repository.PostRepository;
import com.zgdr.schoolhelp.repository.RollImageRepository;
import com.zgdr.schoolhelp.utils.QiniuCloudUtil;
import com.zgdr.schoolhelp.utils.UploadImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 用户头像表service层
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/2
 */
@Service
public class ImageService {
    @Resource
    private HeadImageRepository headImageRepository;

    @Resource
    private RollImageRepository rollImageRepository;

    @Resource
    private UserService userService;

    @Resource
    private PostRepository postRepository;

    @Resource
    private CommentRepository commentRepository;

    /**
     * 上传头像到七牛云，并保存url到数据库，如果数据库中有该用户头像，则更新
     * @author yangji
     * @since 2019/5/2
     *
     * @param   image 图片文件
     * @param  userId 用户id
     */
    public void uploadHeadImage(MultipartFile image, Integer userId){
        UploadImageUtil uploadImageUtil=new UploadImageUtil();
        QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
        HeadImage fheadImage = headImageRepository.findByUserId(userId);
        if(fheadImage==null){
            //上传头像，并将图片url保存到数据库
            HeadImage headImage=new HeadImage(uploadImageUtil.uploadImage(image), userId);
            headImageRepository.save(headImage);
        }else{
            try{
                String key = fheadImage.getImageUrl();
                //删除已存在七牛云的旧头像,如果当前头像是默认头像则不删除
                if(!key.equals("http://ps0mdxwdu.bkt.clouddn.com/74d5deb3-0921-4e74-acef-0b1fee696c05")){
                    qiniuUtil.delete(key);
                }
                //更新数据库的新图片的url
                HeadImage headImage=new HeadImage(uploadImageUtil.uploadImage(image),userId);
                headImage.setImageId(fheadImage.getImageId());
                headImageRepository.save(headImage);

                // 修改关联的帖子的头像
                List<Post> posts = postRepository.findAllByUserId(userId);
                for (Post post : posts) {
                    post.setHeadImageUrl(headImage.getImageUrl());
                }
                postRepository.saveAll(posts);

                // 修改关联的评论的头像
                List<Comment> comments = commentRepository.findAllByUserId(userId);
                for (Comment comment : comments) {
                    comment.setHeadImageUrl(headImage.getImageUrl());
                }
                commentRepository.saveAll(comments);

            }catch(IOException e){
                throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
            }
        }
    }


    /**
     * 返回用户的头像
     * @author yangji
     * @since 2019/5/2
     *
     * @param  userId 用户id
     * @return imageUrl 图片的url
     */
    public String getHeadImage(Integer userId){
        HeadImage headImage = headImageRepository.findByUserId(userId);
        return headImage.getImageUrl();
    }

    /**
     * 上传轮播板图片的接口
     * @author yangji
     * @since 2019/5/2
     *
     * @param  image 上传的图片文件
     * @param  rollImage 轮播板的信息
     * @param  userId 管理员的id
     */
    public void uploadRollImage(MultipartFile image, RollImage rollImage, Integer userId){
        if(!userService.checkPower(userId)){
            //检查是否拥有管理员权限
            throw new ImageException(ImageResultEnum.NO_POWER);
        }
        RollImage frollImage = rollImageRepository.
                findByImageIndexAndImagePosition(rollImage.getImageIndex(),rollImage.getImagePosition());
        UploadImageUtil uploadImageUtil=new UploadImageUtil();
        QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
        if(frollImage==null){
            //该轮播版没传过图片
            rollImage.setImageUrl( uploadImageUtil.uploadImage(image));
            rollImageRepository.save(rollImage);

        }else{
            //该轮播版传过图片，更新
            try {
                String key = frollImage.getImageUrl();
                //删除存放在七牛云的旧图片
                qiniuUtil.delete(key);
                frollImage.setImageUrl(uploadImageUtil.uploadImage(image));
                rollImageRepository.save(frollImage);
            }catch (IOException e){
                throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
            }
        }
    }

    /**
     * 返回用户的头像
     * @author yangji
     * @since 2019/5/2
     *
     * @param  position 轮播板位置
     * @return List<RollImage>
     */
    public List<RollImage> getRollImage(Boolean position){
       List<RollImage> list=rollImageRepository.findAllByImagePosition(position);
     if(list.isEmpty()){
         throw new ImageException(ImageResultEnum.NO_RESOURCE);
     }else{
         return list;
     }
    }
}
