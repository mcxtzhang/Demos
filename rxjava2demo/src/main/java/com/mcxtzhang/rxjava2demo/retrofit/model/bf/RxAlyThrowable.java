package com.mcxtzhang.rxjava2demo.retrofit.model.bf;

/**
 * 介绍：为了适配Rxjava 抛出的Throwable
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/1/6.
 */

public class RxAlyThrowable extends Throwable {
    private String mAlyMsg;

    public RxAlyThrowable(String message) {
        super();
        mAlyMsg = message;
    }
}
