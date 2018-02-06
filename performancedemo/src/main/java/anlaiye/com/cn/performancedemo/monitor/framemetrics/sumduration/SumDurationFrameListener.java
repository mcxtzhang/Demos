package anlaiye.com.cn.performancedemo.monitor.framemetrics.sumduration;

import android.util.Log;
import android.view.FrameMetrics;
import android.view.Window;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.AbstractDpFrameMonitor;
import anlaiye.com.cn.performancedemo.monitor.framemetrics.IFrameListener;

/**
 * Created by zhangxutong on 2018/2/5.
 */

public class SumDurationFrameListener implements IFrameListener {
    private static final String TAG = "butter/SumDuration";

    private double sumOfTotalDuration = 0;
    private long frameCount = 0L;

    private double minFps = Double.MAX_VALUE;
    private double[] mDurationsBuffer;
    private int mBufferIndex;

    public SumDurationFrameListener() {
        mDurationsBuffer = new double[60];
    }

    @Override
    public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int dropCountSinceLastInvocation) {
        FrameMetrics frameMetricsCopy = new FrameMetrics(frameMetrics);
        double totalDurationMs = (0.000001 * frameMetricsCopy.getMetric(FrameMetrics.TOTAL_DURATION));
        Log.d(TAG, "onFrameMetricsAvailable() called with: totalDurationMs = [" + totalDurationMs + "], dropCountSinceLastInvocation = [" + dropCountSinceLastInvocation + "]");
        if (1 == frameMetricsCopy.getMetric(FrameMetrics.FIRST_DRAW_FRAME)) {
            Log.e(TAG, "onFrameMetricsAvailable: FIRST_DRAW_FRAME is 1 , ignore this frame");
            return;
        }
        //小于就补齐16ms
        if (totalDurationMs < AbstractDpFrameMonitor.DEFAULT_WARNING_LEVEL_MS) {
            totalDurationMs = AbstractDpFrameMonitor.DEFAULT_WARNING_LEVEL_MS;
        }

        frameCount++;
        sumOfTotalDuration += totalDurationMs;
        Log.d(TAG, "sumOfTotalDuration:" + sumOfTotalDuration + ", frameCount:" + frameCount);

        //每60帧计算一次minFps
        mDurationsBuffer[mBufferIndex++] = totalDurationMs;
        if (mBufferIndex >= mDurationsBuffer.length) {
            double sum = 0;
            for (double duration : mDurationsBuffer) {
                sum += duration;
            }
            double fps = mDurationsBuffer.length / sum * 1000;
            minFps = Math.min(minFps, fps);
            Log.d(TAG, "60 frames, fps:" + fps + ", minFps:" + minFps);
            //reset
            mBufferIndex = 0;
        }

    }

    public double getAvgFpsInSeconds() {
        return frameCount / sumOfTotalDuration * 1000;
    }

    public double getMinFpsInSeconds() {
        return minFps;
    }
}
