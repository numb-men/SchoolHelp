package com.zgdr.schoolhelp.utils;

import java.util.List;
import java.util.Random;

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

    public static String getRandomString(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++){
            int number = random.nextInt(62);
            stringBuilder.append(str.charAt(number));
        }
        return stringBuilder.toString();
    }
}
