package anlaiye.com.cn.performancedemo.monitor.framemetrics.sumduration;

import android.util.Log;
import android.view.FrameMetrics;
import android.view.Window;

import java.text.DecimalFormat;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.AbstractDpFrameMonitor;

/**
 * Created by zhangxutong on 2018/2/5.
 */

public class SumDurationFrameListener implements Window.OnFrameMetricsAvailableListener {
    private static final String TAG = "butter/SumDuration";

    double sumOfTotalDuration = 0;
    long frameCount = 0L;


    @Override
    public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int dropCountSinceLastInvocation) {
        FrameMetrics frameMetricsCopy = new FrameMetrics(frameMetrics);
        double totalDurationMs = (double) (0.000001 * frameMetricsCopy.getMetric(FrameMetrics.TOTAL_DURATION));
        Log.d(TAG, "onFrameMetricsAvailable() called with: totalDurationMs = [" + totalDurationMs + "], dropCountSinceLastInvocation = [" + dropCountSinceLastInvocation + "]");
        if (1 == frameMetricsCopy.getMetric(FrameMetrics.FIRST_DRAW_FRAME)) {
            Log.e(TAG, "onFrameMetricsAvailable: FIRST_DRAW_FRAME is 1 , ignore this frame");
            return;
        }
        if (totalDurationMs < AbstractDpFrameMonitor.DEFAULT_WARNING_LEVEL_MS) {//小于就补齐16ms
            totalDurationMs = AbstractDpFrameMonitor.DEFAULT_WARNING_LEVEL_MS;
        }

        frameCount++;
        sumOfTotalDuration += totalDurationMs;
        Log.d(TAG, "sumOfTotalDuration:" + sumOfTotalDuration + ", frameCount:" + frameCount);
    }

    public double getAvgFpsInSeconds() {
        return getAvgFpsInMills() * 1000;
    }

    public double getAvgFpsInMills() {
        return frameCount / sumOfTotalDuration;
    }
}
