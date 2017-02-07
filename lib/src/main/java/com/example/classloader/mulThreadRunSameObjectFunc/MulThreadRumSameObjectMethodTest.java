package com.example.classloader.mulThreadRunSameObjectFunc;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/7.
 * History:
 */

public class MulThreadRumSameObjectMethodTest {
    public static void main(String[] args) {
        final TestSingObject testSingObject = new TestSingObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("begin run method in subThread");
                testSingObject.method1();
            }
        }).start();
        System.out.println("begin run method in mainThread");
        testSingObject.method1();
    }
}
