package anlaiye.com.cn.performancedemo.monitor.framemetrics.demo;

import android.app.Application;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.ButterFactory;

/**
 * Created by zhangxutong on 2018/2/2.
 */

public class CstApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(/*ActivityFrameMetrics.getInstance()*/ ButterFactory.getSumDurationMonitor());
        registerActivityLifecycleCallbacks(/*ActivityFrameMetrics.getInstance()*/ ButterFactory.getInstantFpsMonitor());
    }
}
