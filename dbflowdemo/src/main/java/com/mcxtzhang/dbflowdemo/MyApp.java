package com.mcxtzhang.dbflowdemo;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 17/01/11.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
