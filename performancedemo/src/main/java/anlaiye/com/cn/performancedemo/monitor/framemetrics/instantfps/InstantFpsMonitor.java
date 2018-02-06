package anlaiye.com.cn.performancedemo.monitor.framemetrics.instantfps;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.AbstractDpFrameMonitor;

/**
 * Created by zhangxutong on 2018/2/5.
 */

public class InstantFpsMonitor extends AbstractDpFrameMonitor<InstantFpsFrameListener> {

    private static class InnerHolder {
        static final InstantFpsMonitor INSTANCE = new InstantFpsMonitor();
    }

    public static InstantFpsMonitor getInstance() {
        return InstantFpsMonitor.InnerHolder.INSTANCE;
    }

    @Override
    public void report(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            InstantFpsFrameListener listener = frameMetricsAvailableListenerMap.get(activity);
            Log.i(TAG, "report() called with: listener.getAvgFpsInSeconds() = [" + listener.getAvgFpsInSeconds() + "],getMinFpsInSeconds:" + listener.getMinFpsInSeconds());

        }
    }

    @Override
    public InstantFpsFrameListener createMonitor() {
        return new InstantFpsFrameListener();
    }
}
