package com.zgdr.schoolhelp.utils;

import java.util.List;

/**
 * StringUtil
 * 处理字符串的一个工具类
 *
 * @author hengyumo
 * @version 1.0
 * @since 2019/4/27
 */
public class StringUtil {
    public static String listToString(List<String> list, char separator) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s).append(separator);
        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }
}
