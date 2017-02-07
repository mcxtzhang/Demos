package com.example.classloader.mulThreadRunSameObjectFunc;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/7.
 * History:
 */

public class TestSingObject {
    int i;

    public TestSingObject() {
        i = 1;
    }

    public void method1() {
        int innerI = 10;
        i++;
        innerI++;
        System.out.println("enter,current i:" + i + ",innerI:" + innerI + "," + Thread.currentThread());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("exit,current i:" + i + ",innerI:" + innerI + "," + Thread.currentThread());
    }
}
