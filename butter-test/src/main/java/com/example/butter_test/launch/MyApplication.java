package com.example.butter_test.launch;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;


/**
 * Created by zhangxutong on 2017/12/4.
 */

public class MyApplication extends Application {
    private static final String TAG = LaunchTimeTestActivity.TAG;

    @Override
    protected void attachBaseContext(Context base) {
        Log.w(TAG, "attachBaseContext: " + System.currentTimeMillis());
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //app 启动加入延时
        Log.d(TAG, "Application onCreate() called and start delay");
        //SystemClock.sleep(5000);
        Log.d(TAG, "Application onCreate() end");
    }
}
