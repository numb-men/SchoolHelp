package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.enums.QuesnaireResultEnum;
import com.zgdr.schoolhelp.exception.QuesnaireException;
import com.zgdr.schoolhelp.repository.QuesnaireAnswerRepository;
import com.zgdr.schoolhelp.repository.QuesnaireRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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

    @Resource
    private QuesnaireRepository quesnaireRepository;

    @Resource
    private UserService userService;

    @Resource
    private QuesnaireAnswerRepository quesnaireAnswerRepository;


    /**
     * 新建问卷
     * @author yangji
     * @since 2019/4/26
     *
     * @param   quesnaire 问卷信息
     * @param   userId 创建问卷的用户id
     * @return Quesnaire
     */
    public Quesnaire creatQuesnaire(Quesnaire quesnaire, Integer userId){
            User user=userService.readUserById(userId);
            //发布问卷的积分多于自身拥有的积分
            if(quesnaire.getPostPoint()>user.getPoints()){
                throw new QuesnaireException(QuesnaireResultEnum.LESS_POINTS);
            }
            return quesnaireRepository.save(quesnaire);
    }

    /**
     * 根据问卷ID获取一张问卷的详细信息（问题）
     * @author yangji
     * @since 2019/4/27
     *
     * @param   id 问卷id
     * @return Quesnaire
     */
    public  Quesnaire getQuestionByQuesnaireId(Integer id) throws  QuesnaireException{
        Quesnaire quesnaire=quesnaireRepository.findById(id).orElse(null);
        if(quesnaire==null) {
            throw  new QuesnaireException(QuesnaireResultEnum.UNEXIST_QUESNAIRE);
        }
            return quesnaire;
    }

    /**
     * 获取所有的问卷列表,按时间排序（最新的在最前面）
     * @author yangji
     * @since 2019/4/27
     *
     * @return List<Quesnaire>
     */
    public List<Quesnaire> getAllQuesnaire() {
        return quesnaireRepository.findAll(new Sort(Sort.Direction.DESC, "publishTime"));
    }


    /**
     * 根据问卷的ID删除问卷，级联删除，会删除问题表和选项表中属于该问卷的部分
     * @author yangji
     * @since 2019/4/27
     *
     * @param   id 问卷id
     * @param   userId 请求删问卷的用户id
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
     * 更新问卷
     * @author yangji
     * @since 2019/4/27
     *
     * @param   id 问卷id
     * @param   newQuesnaire 新的问卷信息
     * @param   userId 请求更新问卷的用户id
     *  @return Quesnaire
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
     * 用户填写问卷
     * @author yangji
     * @since 2019/4/29
     *
     * @param   quesnaireId 问卷id
     * @param   quesnaireAnswer 填写的问卷答案
     */
    public void doReply(Integer quesnaireId, QuesnaireAnswer quesnaireAnswer){
        Integer userId=quesnaireAnswer.getUserId();
       if(quesnaireAnswerRepository.findByUserIdAndQuesnaireId(userId,quesnaireId)!=null){
            throw new QuesnaireException(QuesnaireResultEnum.NO_REPEAT_REPLY);
        }
        Quesnaire quesnaire=quesnaireRepository.findById(quesnaireId).orElse(null);
        //问卷已采集份数加一
        if(quesnaire.getFinishNum()==null){
           quesnaire.setFinishNum(1);
        }else{
            quesnaire.setFinishNum(quesnaire.getFinishNum()+1);
        }
        quesnaireRepository.save(quesnaire);
        quesnaireAnswerRepository.saveAndFlush(quesnaireAnswer);
    }

    /**
     * 截止问卷
     * @author yangji
     * @since 2019/4/30
     *
     * @param   userId 截止问卷的用户id
     * @param  quesnaireId  问卷的id
     */
    public void cutoffQuesnaire(Integer userId, Integer quesnaireId){
        Quesnaire quesnaire=quesnaireRepository.findById(quesnaireId).orElse(null);
        if(quesnaire.getUserId().equals(userId)){
            //把问卷的截止日期设为now
            quesnaire.setFinishTime(new Date());
            quesnaireRepository.save(quesnaire);
        }else{
            throw new QuesnaireException(QuesnaireResultEnum.NO_POWER_TO_UPDATE);
        }
    }
}
