package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.*;
import com.zgdr.schoolhelp.repository.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @ProjectName: schoolhelp
 * @Package: com.zgdr.schoolhelp.service
 * @ClassName: QuesnaireServiceTest
 * @Author: yangji
 * @Description:
 * @Date: 2019/4/26 20:45
 * @Version: 1.0
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuesnaireServiceTest {
    @Autowired
    private QuesnaireService quesnaireService;

    @Autowired
    private QuesnaireRepository quesnaireRepository;
    @Autowired
    private  QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private  ShortQuestionAnswerRepository shortQuestionAnswerRepository;
    @Autowired
    private ChoiceQuestionAnswerRepository choiceQuestionAnswerRepository;

    Quesnaire quesnaire=new Quesnaire(2,"DSAC ",20,1,100,new Date(),new Date());
    @Test
    public void creatQuesnaire() {
        quesnaireService.creatQuesnaire(quesnaire,quesnaire.getUserId());
    }
    @Test
    public void getQuestionByQuesnaireId(){
        System.out.println( quesnaireService.getQuestionByQuesnaireId(336).toString());

    }
    @Test
    public void getAllQuesnaire(){
        System.out.println(quesnaireService.getAllQuesnaire().toString()); ;

    }
    /* @Test
   * public void deleteQuesnaire(){
         quesnaireService.deleteQuesnaireById(377, 350);//级联删除，会级联删除问题和问题的选项
     }*/
    @Test
    public  void  updateQuesnaire(){
        quesnaireRepository.save(quesnaire);
        Quesnaire quesnaire1=quesnaireRepository.findById(226).orElse(null);
        Option option=new Option("鸡你太美");
        Option option1=optionRepository.findById(228).orElse(null);
        option1.setContent("ggegegeggegegge");
        Question question=questionRepository.findById(331).orElse(null);
        question.setTitle("哈哈哈哈啊哈");
        List<Option> set1=new ArrayList<>();
        set1.add(option1);
        set1.add(option);
        question.setOptions(set1);
        List<Question> set=new ArrayList<>();
        set.add(question);
        quesnaire1.setQuestions(set);
        quesnaireService.updateQuesnaire(quesnaire.getQuesnaireId(),quesnaire1,quesnaire.getUserId());
    }
    /*  @Test
      public void doReply(){
        QuesnaireAnswer quesnaireAnswer=new QuesnaireAnswer(336,338,new Date());

        QuestionAnswer questionAnswer=new QuestionAnswer(1,"选择题");
        QuestionAnswer questionAnswer1=new QuestionAnswer(2,"简答题");
        ChoiceQuestionAnswer choiceQuestionAnswer=new ChoiceQuestionAnswer(1);
        ShortQuestionAnswer shortQuestionAnswer1=new ShortQuestionAnswer("嘿嘿嘿");

        List<ChoiceQuestionAnswer> list1=new ArrayList<>();
        List<ShortQuestionAnswer> list3=new ArrayList<>();
        list1.add(choiceQuestionAnswer);
        list3.add(shortQuestionAnswer1);
        questionAnswer.setQuestionAnswers(list1);
        questionAnswer.setShortQuestionAnswers(list3);

       List<QuestionAnswer> list=new ArrayList<>();
       list.add(questionAnswer);
       list.add(questionAnswer1);
       quesnaireAnswer.setQuestionAnswers(list);
       quesnaireService.doReply(336, quesnaireAnswer);
    }*/
    @Test
    public void cutoffQuesnaire(){
        quesnaireService.cutoffQuesnaire(5,336);
    }
}