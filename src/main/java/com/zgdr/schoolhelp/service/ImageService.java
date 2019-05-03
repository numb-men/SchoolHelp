package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.HeadImage;
import com.zgdr.schoolhelp.domain.RollImage;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.ImageResultEnum;
import com.zgdr.schoolhelp.exception.GlobalException;
import com.zgdr.schoolhelp.exception.ImageException;
import com.zgdr.schoolhelp.repository.HeadImageRepository;
import com.zgdr.schoolhelp.repository.RollImageRepository;
import com.zgdr.schoolhelp.utils.QiniuCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private HeadImageRepository headImageRepository;

    @Autowired
    private RollImageRepository rollImageRepository;

    @Autowired
    private UserService userService;

    /**
    * @Description: 上传头像到七牛云，并保存url到数据库，如果数据库中有该用户头像，则更新
    * @Param:MultipartFile image, Integer userId
    * @return:
    * @Author:yangji
    * @Date: 2019/5/2
    */
    public void uploadHeadImage(MultipartFile image, Integer userId){
        if (image.isEmpty()) {
             throw new ImageException(ImageResultEnum.EMPTY_FILE);//文件为空
        }
        QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
        HeadImage headImage = headImageRepository.findByUserId(userId);
        if(headImage==null){//该用户没有上传过头像
            try {
                byte[] bytes = image.getBytes();
                String imageName = UUID.randomUUID().toString();//为图片产生个随机名字
                try {
                    //使用base64方式上传到七牛云
                    String url = qiniuUtil.put64image(bytes, imageName);//存储图片并返回图片储存的url
                    HeadImage headImage1 = new HeadImage(url, userId);
                    headImageRepository.save(headImage1);//记录存到数据库
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
            }
        }else{//用户上传过图片，更新
            String key=headImage.getImageUrl();//获取旧图片的url
            try{
                qiniuUtil.delete(key);//删除存在七牛云的旧图片
                byte[] bytes = image.getBytes();
                String imageName = UUID.randomUUID().toString();//为图片产生个随机名字
                try {
                    //使用base64方式上传到七牛云
                    String url = qiniuUtil.put64image(bytes, imageName);//存储图片并返回图片储存的url
                    headImage.setImageUrl(url);
                    headImageRepository.save(headImage);//记录存到数据库
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (IOException e){
                throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
            }
        }
    }

    /**
    * @Description:  返回用户的头像
    * @Param:
    * @return:
    * @Author:yangji
    * @Date: 2019/5/2
    */
    public String getHeadImage(Integer userId){
        HeadImage headImage = headImageRepository.findByUserId(userId);
        return headImage.getImageUrl();
    }


    /**
    * @Description: 上传轮播板图片的接口
    * @Param:
    * @return:
    * @Author:yangji
    * @Date: 2019/5/2
    */
   public void uploadRollImage(MultipartFile image, RollImage rollImage, Integer userId){
        if(!userService.checkPower(userId)){//检查是否拥有管理员权限
            throw new ImageException(ImageResultEnum.NO_POWER);
        }
        RollImage rollImage1 = rollImageRepository.
                findByImageIndexAndImagePosition(rollImage.getImageIndex(),rollImage.getImagePosition());
        if (image.isEmpty()) {
            throw new ImageException(ImageResultEnum.EMPTY_FILE);//文件为空
        }
        QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
        if(rollImage1==null){//该位置没有图片
            try {
                byte[] bytes = image.getBytes();
                String imageName = UUID.randomUUID().toString();//为图片产生个随机名字
                try {
                    //使用base64方式上传到七牛云
                    String url = qiniuUtil.put64image(bytes, imageName);//存储图片并返回图片储存的url
                    rollImage.setImageUrl(url);
                    rollImageRepository.save(rollImage);//记录存到数据库
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
            }
        }else{//更新操作
            String key=rollImage1.getImageUrl();//获取旧图片的url
            try{
                qiniuUtil.delete(key);//删除存在七牛云的旧图片
                byte[] bytes = image.getBytes();
                String imageName = UUID.randomUUID().toString();//为图片产生个随机名字
                try {
                    //使用base64方式上传到七牛云
                    String url = qiniuUtil.put64image(bytes, imageName);//存储图片并返回图片储存的url
                    rollImage1.setImageUrl(url);
                    rollImageRepository.save(rollImage1);//记录存到数据库
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (IOException e){
                throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
            }
        }
    }
    public List<RollImage> getRollImage(Boolean position){
       List<RollImage> list=rollImageRepository.findAllByImagePosition(position);
     if(list.isEmpty()){
         throw new ImageException(ImageResultEnum.NO_RESOURCE);
     }else{
         return list;
     }
    }
}
