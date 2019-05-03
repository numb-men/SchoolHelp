package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.enums.QuesnaireResultEnum;
import com.zgdr.schoolhelp.exception.QuesnaireException;
import com.zgdr.schoolhelp.repository.QuesnaireAnswerRepository;
import com.zgdr.schoolhelp.repository.QuesnaireRepository;
import com.zgdr.schoolhelp.repository.QuestionAnswerRepository;
import com.zgdr.schoolhelp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.*;

/**
 * 问卷service
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/26
 */
@Transactional
@Service
public class QuesnaireService {

    @Autowired
    private QuesnaireRepository quesnaireRepository;

    @Autowired
    private OptionService optionService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuesnaireAnswerRepository quesnaireAnswerRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    /**
    * @Description:新建问卷
    * @Param:
    * @return:  问卷
    * @Author:yangji
    * @Date: 2019/4/26
    */
    public Quesnaire creatQuesnaire(Quesnaire quesnaire, Integer userId){
            User user=userService.readUserById(userId);
            if(quesnaire.getPostPoint()>user.getPoints()){//发布问卷的积分多于自身拥有的积分
                throw new QuesnaireException(QuesnaireResultEnum.LESS_POINTS);
            }
            return quesnaireRepository.save(quesnaire);
    }

    /**
    * @Description:  根据问卷ID获取一张问卷的详细信息（问题）
    * @Param:  Integer id
    * @return:  一个List<Question>,属于该问卷的问题
    * @Author:yangji
    * @Date: 2019/4/27
    */
    public  Quesnaire getQuestionByQuesnaireId(Integer id) throws  QuesnaireException{
        Quesnaire quesnaire=quesnaireRepository.findById(id).orElse(null);
        if(quesnaire==null) {
            throw  new QuesnaireException(QuesnaireResultEnum.UNEXIST_QUESNAIRE);
        }
            return quesnaire;
    }

    /**
    * @Description: 获取所有的问卷列表,按时间排序（最新的在最前面）
    * @Param:
    * @return:  List<Quesnaire>
    * @Author:yangji
    * @Date: 2019/4/27
    */
    public List<Quesnaire> getAllQuesnaire() {
        return quesnaireRepository.findAll(new Sort(Sort.Direction.DESC, "publishTime"));
    }

    /** 
    * @Description: 根据问卷的ID删除问卷，级联删除，会删除问题表和选项表中属于该问卷的部分 
    * @Param:  
    * @return:  
    * @Author:yangji
    * @Date: 2019/4/27 
    */ 
    public void deleteQuesnaireById(Integer id, Integer userId) throws QuesnaireException{
        Quesnaire quesnaire=quesnaireRepository.findById(id).orElse(null);
        if(quesnaire==null){
            throw  new QuesnaireException(QuesnaireResultEnum.UNEXIST_QUESNAIRE);
        }
        if(quesnaire.getUserId().intValue()==userId.intValue()){
            quesnaireRepository.delete(quesnaire);
            if(!quesnaireAnswerRepository.findAllByQuesnaireId(id).isEmpty()){
                quesnaireAnswerRepository.deleteAllByQuesnaireId(id);//删除该问卷的采集的回答
            }
        }else{
            throw new QuesnaireException(QuesnaireResultEnum.NO_POWER_TO_DELETE);
        }
    }

   /** 
   * @Description:更新问卷
   * @Param:
   * @return:  
   * @Author:yangji
   * @Date: 2019/4/27 
   */  
    public  Quesnaire updateQuesnaire(Integer id,Quesnaire newQuesnaire, Integer userId){
        Quesnaire quesnaire=quesnaireRepository.findById(id).orElse(null);
        if(quesnaire==null){
            throw  new QuesnaireException(QuesnaireResultEnum.UNEXIST_QUESNAIRE);
        }
        if(userId.intValue()==quesnaire.getUserId().intValue()){
            newQuesnaire.setQuesnaireId(id);
            return quesnaireRepository.save(newQuesnaire);
        }else{
            throw new QuesnaireException(QuesnaireResultEnum.NO_POWER_TO_UPDATE);
        }
    }

    /** 
    * @Description: 用户填写问卷
    * @Param:  
    * @return:  
    * @Author:yangji
    * @Date: 2019/4/29 
    */ 
    public void doReply(Integer quesnaireId, QuesnaireAnswer quesnaireAnswer){
        Integer userId=quesnaireAnswer.getUserId();
       if(quesnaireAnswerRepository.findByUserIdAndQuesnaireId(userId,quesnaireId)!=null){
            throw new QuesnaireException(QuesnaireResultEnum.NO_REPEAT_REPLY);
        }
        Quesnaire quesnaire=quesnaireRepository.findById(quesnaireId).orElse(null);
        if(quesnaire.getFinishNum()==null){ //问卷已采集份数加一
           quesnaire.setFinishNum(1);
        }else{
            quesnaire.setFinishNum(quesnaire.getFinishNum()+1);
        }
        quesnaireRepository.save(quesnaire);
        quesnaireAnswerRepository.saveAndFlush(quesnaireAnswer);
    }

    /**
    * @Description:截止问卷
    * @Param:问卷的id
    * @return:
    * @Author:yangji
    * @Date: 2019/4/30
    */
    public void cutoffQuesnaire(Integer userId, Integer quesnaireId){
        Quesnaire quesnaire=quesnaireRepository.findById(quesnaireId).orElse(null);
        if(quesnaire.getUserId().equals(userId)){
            quesnaire.setFinishTime(new Date());//把问卷的截止日期设为now
            quesnaireRepository.save(quesnaire);
        }else{
            throw new QuesnaireException(QuesnaireResultEnum.NO_POWER_TO_UPDATE);
        }
    }
}
