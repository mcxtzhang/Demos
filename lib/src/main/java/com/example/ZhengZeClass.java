package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/8/31.
 */
public class ZhengZeClass {
    public static void main(String[] arg) {

        Pattern regex1 = Pattern.compile("^(17[0-9]|13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
        Matcher matcher = regex1.matcher("186163203845");
        boolean isMatched = matcher.matches();
        System.out.print("isMatched:"+isMatched);
    }
}
