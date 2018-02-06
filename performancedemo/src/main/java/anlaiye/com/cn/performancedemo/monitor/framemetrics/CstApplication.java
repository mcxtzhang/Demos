package anlaiye.com.cn.performancedemo.monitor.framemetrics;

import android.app.Application;

/**
 * Created by zhangxutong on 2018/2/2.
 */

public class CstApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(/*ActivityFrameMetrics.getInstance()*/ ButterFactory.getFrameMonitor());
    }
}
