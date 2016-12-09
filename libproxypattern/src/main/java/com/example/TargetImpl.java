package com.example;

/**
 * 介绍：委托类
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/9.
 */

public class TargetImpl implements ITarget {
    @Override
    public void thing1() {
        System.out.println("thing1");
        sleep(110);
    }

    @Override
    public void thing2() {
        System.out.println("thing2");
        sleep(120);
    }

    @Override
    public void thing3() {
        System.out.println("thing3");
        sleep(130);
    }

    private static void sleep(long millSeconds) {
        try {
            Thread.sleep(millSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
