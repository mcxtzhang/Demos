package com.example.interview;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Intro:给定一个字符串，求第一个不重复的字符    abbcad -> c
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/13.
 * History:
 */

public class Ali_2_LinkedMapTest {

    public static void main(String[] args) {
        String src = "abbcad";
        LinkedHashMap<Character, Integer> linkedHashMap = new LinkedHashMap<>(src.length());
        for (char c : src.toCharArray()) {
            Integer integer = linkedHashMap.get(c);
            if (integer == null) {
                linkedHashMap.put(c, 1);
            } else {
                linkedHashMap.put(c, ++integer);
            }
        }

        for (Map.Entry<Character, Integer> mapItem : linkedHashMap.entrySet()) {
            if (mapItem.getValue().equals(1)){
                System.out.println("找到了"+mapItem.getKey());
                break;
            }
        }


    }
}
