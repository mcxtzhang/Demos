package anlaiye.com.cn.performancedemo.monitor.framemetrics.instantfps;

import android.util.Log;
import android.view.FrameMetrics;
import android.view.Window;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.AbstractDpFrameMonitor;

/**
 * Created by zhangxutong on 2018/2/5.
 */

public class InstantFpsFrameListener implements Window.OnFrameMetricsAvailableListener {
    private static final String TAG = "butter/InstantFps";
    long mFrameCount = 0;
    double sumOfInstantFps = 0;

    @Override
    public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int dropCountSinceLastInvocation) {
        FrameMetrics frameMetricsCopy = new FrameMetrics(frameMetrics);
        double totalDurationMs = (double) (0.000001 * frameMetricsCopy.getMetric(FrameMetrics.TOTAL_DURATION));
        Log.i(TAG, "onFrameMetricsAvailable() called with: totalDurationMs = [" + totalDurationMs + "], dropCountSinceLastInvocation = [" + dropCountSinceLastInvocation + "]");
        if (1 == frameMetricsCopy.getMetric(FrameMetrics.FIRST_DRAW_FRAME)) {
            Log.e(TAG, "onFrameMetricsAvailable: FIRST_DRAW_FRAME is 1 , ignore this frame");
            return;
        }
        if (totalDurationMs < AbstractDpFrameMonitor.DEFAULT_WARNING_LEVEL_MS) {//小于就补齐16ms
            totalDurationMs = AbstractDpFrameMonitor.DEFAULT_WARNING_LEVEL_MS;
        }

        mFrameCount++;
        double instantFps = 1 / totalDurationMs * 1000;
        sumOfInstantFps = sumOfInstantFps + instantFps;
        Log.i(TAG, "sumOfInstantFps:" + sumOfInstantFps + ", mFrameCount:" + mFrameCount);
    }

    public double getAvgFpsInSeconds() {
        return sumOfInstantFps / mFrameCount;
    }
}
