package com.mcxtzhang.github;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/10.
 * History:
 */

public class CstApplication extends Application {
    private static final String TAG = "zxt/CstApplication";

    public CstApplication() {
        Log.d(TAG, "CstApplication() called" + Process.myPid());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "attachBaseContext() called with: base = [" + base + "]");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called" + Process.myPid());
    }
}
