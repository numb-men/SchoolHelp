package com.zgdr.schoolhelp.utils;

import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.ImageResultEnum;
import com.zgdr.schoolhelp.exception.GlobalException;
import com.zgdr.schoolhelp.exception.ImageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 上传头像的工具类
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/2
 */
public class UploadImageUtil {

    /**
     * 上传头像到七牛云
     * @author yangji
     * @since 2019/5/2
     *
     * @param   image 图片文件
     */
    public String uploadImage(MultipartFile image){
        if (image.isEmpty()) {
            //文件为空
            throw new ImageException(ImageResultEnum.EMPTY_FILE);
        }
        QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
        String url;
            try {
                byte[] bytes = image.getBytes();
                //为图片产生个随机名字
                String imageName = UUID.randomUUID().toString();
                try {
                    //使用base64方式上传到七牛云
                    //存储图片并返回图片储存的url
                    url = qiniuUtil.put64image(bytes, imageName);
                    //记录存到数据库
                    return url;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);
            }
        throw new GlobalException(GlobalResultEnum.UNKNOW_ERROR);

    }
}
