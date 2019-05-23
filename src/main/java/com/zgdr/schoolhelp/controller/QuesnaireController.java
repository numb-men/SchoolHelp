package com.zgdr.schoolhelp.controller;

import com.zgdr.schoolhelp.annotation.UserLoginToken;
import com.zgdr.schoolhelp.domain.Quesnaire;
import com.zgdr.schoolhelp.domain.QuesnaireAnswer;
import com.zgdr.schoolhelp.domain.Result;
import com.zgdr.schoolhelp.service.QuesnaireService;
import com.zgdr.schoolhelp.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 新建问卷
     * @author yangji
     * @since 2019/4/27
     *
     * @param  quesnaire
     * @return result
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
     * 根据问卷ID获得问卷的所有问题
     * @author yangji
     * @since 2019/4/27
     *
     * @param  id
     * @return result
     */
    @GetMapping("")
    public Result getQuesnaireById(@RequestParam(value = "id") Integer id){
        return Result.success(quesnaireService.getQuestionByQuesnaireId(id));
    }

    /**
     * 获取所有的问卷，并按时间排序
     * @author yangji
     * @since 2019/4/27
     *
     * @return result
     */
    @GetMapping("/")
    public Result getAllQuesnaire(){
        return  Result.success(quesnaireService.getAllQuesnaire());
    }

    /**
     * 删除问卷，同时会删除问题表和选项表中属于该问卷的内容
     * @author yangji
     * @since 2019/4/27
     *
     * @return result
     */
    @DeleteMapping("")
    public Result deleteQuesnaireById(@RequestParam(value = "id") Integer id,
                                      HttpServletRequest httpServletRequest){
        Integer userId= TokenUtil.getUserIdByRequest(httpServletRequest);
        quesnaireService.deleteQuesnaireById(id, userId);
        return Result.success(null);
    }

    /**
     * 修改问卷
     * @author yangji
     * @since 2019/4/30
     *
     * @param  id
     * @return result
     */
    @PutMapping("")
    public Result updateQuesnaire(@RequestParam(value = "id") Integer id,
                                  @Valid Quesnaire quesnaire,
                                  HttpServletRequest httpServletRequest){
        Integer userId= TokenUtil.getUserIdByRequest(httpServletRequest);
        quesnaireService.updateQuesnaire(id, quesnaire, userId);
        return Result.success(null);
    }

    /**
     * 回答问卷
     * @author yangji
     * @since 2019/4/30
     *
     * @param  quesnaireId 问卷的id
     * @param   quesnaireAnswer 问卷回答结果
     * @return result
     */
    @PostMapping("/reply")
    public Result doReply(@Valid QuesnaireAnswer quesnaireAnswer,
                          @RequestParam(value = "id") Integer quesnaireId){
        quesnaireService.doReply(quesnaireId,quesnaireAnswer);
        return Result.success(null);
    }

    /**
     * 截止问卷
     * @author yangji
     * @since 2019/4/30
     *
     * @param  quesnaireId 问卷的id
     * @return result
     */
    @GetMapping("/submit")
    public Result doCutoff(@RequestParam(value = "id") Integer quesnaireId,
                           HttpServletRequest httpServletRequest){
        Integer userId= TokenUtil.getUserIdByRequest(httpServletRequest);
        quesnaireService.cutoffQuesnaire(userId, quesnaireId);
        return Result.success(null);
    }
}
