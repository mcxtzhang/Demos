package anlaiye.com.cn.performancedemo.monitor.framemetrics.sumduration;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.AbstractDpFrameMonitor;

/**
 * Created by zhangxutong on 2018/2/5.
 */

public class SumDurationMonitor extends AbstractDpFrameMonitor<SumDurationFrameListener> {


    private static class InnerHolder {
        static final SumDurationMonitor INSTANCE = new SumDurationMonitor();
    }

    public static SumDurationMonitor getInstance() {
        return InnerHolder.INSTANCE;
    }

    @Override
    public SumDurationFrameListener createMonitor() {
        return new SumDurationFrameListener();
    }

    @Override
    public void report(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            SumDurationFrameListener listener = frameMetricsAvailableListenerMap.get(activity);
            Log.d(TAG, "report() called with: listener.getAvgFpsInSeconds() = [" + listener.getAvgFpsInSeconds() + "]");

        }
    }


}
