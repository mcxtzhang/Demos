package com.mcxtzhang.hotfixdemo;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/4/14.
 * History:
 */

public class BaseApplicatioin extends MultiDexApplication {

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplicatioin application = (BaseApplicatioin) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        refWatcher = LeakCanary.install(this);

    }
}
