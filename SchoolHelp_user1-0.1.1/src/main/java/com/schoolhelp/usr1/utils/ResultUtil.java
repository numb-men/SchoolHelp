package com.schoolhelp.usr1.utils;

import com.schoolhelp.usr1.domain.Result;

/**
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/27
 **/
public class ResultUtil {
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }


    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
