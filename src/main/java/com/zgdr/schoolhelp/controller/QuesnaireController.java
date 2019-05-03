package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Quesnaire;
import com.zgdr.schoolhelp.domain.QuesnaireAnswer;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.enums.GlobalResultEnum;
import com.zgdr.schoolhelp.enums.QuesnaireResultEnum;
import com.zgdr.schoolhelp.service.QuesnaireService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 问卷的controller层
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/26
 */
@RestController
@RequestMapping(value="/questnaire")
public class QuesnaireController {

    @Autowired
    private QuesnaireService quesnaireService;

    /**
    * @Description: 新建问卷
    * @Param:  Quesnaire quesnaire
    * @return:
    * @Author:yangji
    * @Date: 2019/4/27
    */
    @UserLoginToken
    @PostMapping("/")
    public Result creatQuesnaire(@Valid Quesnaire quesnaire,
                                 HttpServletRequest httpServletRequest){
        Integer userId= TokenUtil.getUserIdByRequest(httpServletRequest);
        if(userId.intValue()==quesnaire.getUserId().intValue()){
            quesnaireService.creatQuesnaire(quesnaire, userId);
            return  Result.success(null);
        }
        return  Result.error(-5,"创建问卷失败");
    }

    /** 
    * @Description:  根据问卷ID获得问卷的所有问题
    * @Param:  问卷的ID
    * @return:  属于该问卷的所有文题
    * @Author:yangji
    * @Date: 2019/4/27 
    */
    @GetMapping("")
    public Result getQuesnaireById(@RequestParam(value = "id") Integer id){
        return Result.success(quesnaireService.getQuestionByQuesnaireId(id));
    }

    /**
    * @Description: 获取所有的问卷，并按时间排序
    * @Param:
    * @return:  Result中的DATA为一个List<Quesnaire>
    * @Author:yangji
    * @Date: 2019/4/27
    */
    @GetMapping("/")
    public Result getAllQuesnaire(){
        return  Result.success(quesnaireService.getAllQuesnaire());
    }

    /**
    * @Description: 删除问卷，同时会删除问题表和选项表中属于该问卷的内容
    * @Param:
    * @return:
    * @Author:yangji
    * @Date: 2019/4/27
    */
    @DeleteMapping("")
    public Result deleteQuesnaireById(@RequestParam(value = "id") Integer id,
                                      HttpServletRequest httpServletRequest){
        Integer userId=TokenUtil.getUserIdByRequest(httpServletRequest);
        quesnaireService.deleteQuesnaireById(id, userId);
        return Result.success(null);
    }

    /**
    * @Description: 修改问卷
    * @Param:  问卷的id，和完整的修改后的问卷的信息
    * @return:
    * @Author:yangji
    * @Date: 2019/4/30
    */
    @PutMapping("")
    public Result updateQuesnaire(@RequestParam(value = "id") Integer id,
                                  @Valid Quesnaire quesnaire,
                                  HttpServletRequest httpServletRequest){
        Integer userId=TokenUtil.getUserIdByRequest(httpServletRequest);
        quesnaireService.updateQuesnaire(id, quesnaire, userId);
        return Result.success(null);
    }


    /**
    * @Description: 回答问卷
    * @Param:  问卷的id，和回答的结果
    * @return:
    * @Author:yangji
    * @Date: 2019/4/30
    */
    @PostMapping("/reply")
    public Result doReply(@Valid QuesnaireAnswer quesnaireAnswer,
                          @RequestParam(value = "id") Integer quesnaireId){
        quesnaireService.doReply(quesnaireId,quesnaireAnswer);
        return Result.success(null);
    }


    /**
    * @Description: 截止问卷
    * @Param:  问卷的id
    * @return:
    * @Author:yangji
    * @Date: 2019/4/30
    */
    @GetMapping("/submit")
    public Result doCutoff(@RequestParam(value = "id") Integer quesnaireId,
                           HttpServletRequest httpServletRequest){
        Integer userId=TokenUtil.getUserIdByRequest(httpServletRequest);
        quesnaireService.cutoffQuesnaire(userId, quesnaireId);
        return Result.success(null);
    }
}
