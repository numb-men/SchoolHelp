package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.domain.RollImage;
import com.zgdr.schoolhelp.service.ImageService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 上传图片到七牛云的Controller
 *
 * @author yangji
 * @version 1.0
 * @since 2019/5/1
 */
@RestController
@RequestMapping(value = "")
public class ImageController {

    @Resource
    private ImageService imageService;

    /**
     * 上传头像的接口，
     * @author yangji
     * @since 2019/5/2
     *
     * @param  image 图片文件
     * @return result
     */
    @UserLoginToken
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
     * @author yangji
     * @since 2019/5/2
     *
     * @return result
     */
    @UserLoginToken
    @GetMapping(value = "download/head")
    public Result getHeadImage(HttpServletRequest httpServletRequest){
        Integer userId= TokenUtil.getUserIdByRequest(httpServletRequest);
        return Result.success( imageService.getHeadImage(userId));
    }

    /**
     * 上传轮播版的图片
     * @author yangji
     * @since 2019/5/2
     *
     * @param  image 图片文件
     * @return result
     */
    @UserLoginToken
    @PostMapping(value = "uploadimage/roll")
    public Result uploadRollImage(@RequestParam MultipartFile image,
                                  @Valid RollImage rollImage,
                                  HttpServletRequest httpServletRequest){
        Integer userId= TokenUtil.getUserIdByRequest(httpServletRequest);
        imageService.uploadRollImage(image,rollImage,userId);
        return Result.success(null);
    }
    @GetMapping("download/roll")
    public Result getRollImage(@RequestParam(value = "imagePosition") Boolean position){
        return Result.success(imageService.getRollImage(position));
    }
}
