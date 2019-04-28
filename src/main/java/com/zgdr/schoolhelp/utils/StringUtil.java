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

    /**
     * 将List<String>使用分隔符分隔并合并
     * @author hengyumo
     * @since 2019/4/23
     *
     * @param list 要处理的List<String>
     * @param separator 分隔符
     * @return String 处理好的字符串
     */
    public static String listToString(List<String> list, char separator) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s).append(separator);
        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }

    /**
     * 返回由大小写字母和数字组成的随机字符串
     * @author hengyumo
     * @since 2019/4/26
     *
     * @param length 所需随机字符串的长度
     * @return String 生成的字符串
     */
    public static String getRandomString(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++){
            int number = random.nextInt(str.length());
            stringBuilder.append(str.charAt(number));
        }
        return stringBuilder.toString();
    }

    /**
     * 校验密码
     * 含且仅含大小写字母及数字且长度在8-255位
     * @author hengyumo
     * @since 2019/4/29
     *
     * @param str 待判断的字符串
     * @return boolean
     */
    public static boolean isGoodPasseord(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]{8,255}$";

        return isDigit && isLetter && str.matches(regex);
    }
}
