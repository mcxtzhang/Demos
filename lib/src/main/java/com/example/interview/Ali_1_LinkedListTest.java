package com.example.interview;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Intro:标号1-n的n个人首尾相接，1到3报数，报到3的退出，求最后一个人的标号
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/13.
 * History:
 */

public class Ali_1_LinkedListTest {
    public static void main(String[] args) {
        int n = 10;
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        Iterator<Integer> iterator = list.iterator();
        int index = 1;
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println("我是:" + next + ",我现在报数:" + index);
            if (index == 3) {
                System.out.println("!!!!我是:" + next + ",我现在退出:" + index);
                iterator.remove();
                //删除以后才有可能是最后一个
                if (list.size() == 1) {
                    if (iterator.hasNext()) {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!终于找到你:" + iterator.next());
                    } else {
                        System.out.println("???????????????????????终于找到你:" + list.get(0));
                    }
                    break;
                }
            }
            index++;
            if (index == 4) {
                index = 1;
            }
            if (!iterator.hasNext() && list.size() > 0) {
                System.out.println("毅种循环");
                iterator = list.iterator();
            }

        }


    }
}
