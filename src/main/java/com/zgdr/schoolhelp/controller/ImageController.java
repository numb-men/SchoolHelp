package com.zgdr.schoolhelp.controller;

import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.domain.RollImage;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.service.ImageService;
import com.zgdr.schoolhelp.utils.QiniuCloudUtil;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 上传图片到七牛云的Controller
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/1
 */
@RestController
@RequestMapping(value = "")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
    * @Description: 上传头像的接口，
    * @Param:  MultipartFile image
    * @return:
    * @Author:yangji
    * @Date: 2019/5/2
    */
    @ResponseBody
    @PostMapping(value = "/uploadimg/head")
    public Result uploadImage(@RequestParam MultipartFile image,
                            HttpServletRequest httpServletRequest){
        Integer userId= TokenUtil.getUserIdByRequest(httpServletRequest);
        imageService.uploadHeadImage(image, userId);
        return Result.success(null);
    }

    /**
    * 返回用户的头像
    * @param
    * @return:用户头像的图片的url
    * @Author:yangji
    * @Date: 2019/5/2
    */
    @GetMapping(value = "download/head")
    public Result getHeadImage(HttpServletRequest httpServletRequest){
        Integer userId=TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success( imageService.getHeadImage(userId));
    }


    /**
    * @Description: 上传轮播版的图片
    * @Param:
    * @return:
    * @Author:yangji
    * @Date: 2019/5/2
    */
    @PostMapping(value = "uploadimage/roll")
    public Result uploadRollImage(@RequestParam MultipartFile image,
                                  @Valid RollImage rollImage,
                                  HttpServletRequest httpServletRequest){
        Integer userId=TokenUtil.getUserIdByRequest(httpServletRequest);
        imageService.uploadRollImage(image,rollImage,userId);
        return Result.success(null);
    }
    @GetMapping("download/roll")
    public Result getRollImage(@RequestParam(value = "imagePosition") Boolean position){
        return Result.success(imageService.getRollImage(position));
    }
}
