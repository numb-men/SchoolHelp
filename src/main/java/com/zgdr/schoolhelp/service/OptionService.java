package com.zgdr.schoolhelp.service;

import com.zgdr.schoolhelp.domain.Option;
import com.zgdr.schoolhelp.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 选项service
 *
 *
 * @author yangji
 * @version 1.0
 * @since 2019/4/27
 */
@Service
public class OptionService {
    @Autowired
    private OptionRepository optionRepository;


    /**
    * @Description: 保存前端传来的一个选项list
    * @Param:  List< Option> list
    * @return:  List<Option>
    * @Author:yangji
    * @Date: 2019/4/27
    */
    public List<Option> creatOption(List< Option> list){
       return optionRepository.saveAll(list);
    }


    /**
    * @Description: 根据问题的id来得到该道问题选项
    * @Param:
    * @return:
    * @Author:yangji
    * @Date: 2019/4/27
    */
    public  List<Option> getOptionById(Integer id){
        return optionRepository.findAllByQuestionId(id);
    }
}
