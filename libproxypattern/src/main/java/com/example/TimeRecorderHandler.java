package com.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/9.
 */

public class TimeRecorderHandler implements InvocationHandler {
    private Object target;

    public TimeRecorderHandler() {
    }

    public TimeRecorderHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //
        long start = System.currentTimeMillis();
        Object invoke = method.invoke(target, args);
        System.out.println(method.getName() + " cost time is:" + (System.currentTimeMillis() - start));
        return invoke;
    }
}
