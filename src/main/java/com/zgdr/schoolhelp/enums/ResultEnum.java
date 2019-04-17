package com.zgdr.schoolhelp.enums;

import java.lang.reflect.ParameterizedType;

/**
 * ResultEnum
 * 通过一个接口实现了将多种不同的响应状态枚举使用统一的接口调用
 * （枚举是无法继承的，这里实现模拟了枚举继承）
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/17
 */
public interface ResultEnum {

    /* 获取响应结果的状态码 */
    Integer getCode();

    /* 获取响应结果的消息 */
    String getMsg();
}
