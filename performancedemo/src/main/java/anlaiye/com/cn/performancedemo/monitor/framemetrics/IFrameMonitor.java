package anlaiye.com.cn.performancedemo.monitor.framemetrics;

import android.app.Activity;
import android.app.Application;

/**
 * Created by zhangxutong on 2018/2/5.
 */

public interface IFrameMonitor extends Application.ActivityLifecycleCallbacks {
    void startMonitor(Activity activity);

    void stopMonitor(Activity activity);

    void report(Activity activity);
}
