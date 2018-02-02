package anlaiye.com.cn.performancedemo;

import android.app.Application;

import anlaiye.com.cn.performancedemo.monitor.ActivityFrameMetrics;

/**
 * Created by zhangxutong on 2018/2/2.
 */

public class CstApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityFrameMetrics.Builder().build());
    }
}
