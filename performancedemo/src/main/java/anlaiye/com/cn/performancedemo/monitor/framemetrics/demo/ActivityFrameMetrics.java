package anlaiye.com.cn.performancedemo.monitor.framemetrics.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.FrameMetrics;
import android.view.Window;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActivityFrameMetrics implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "TAG/AF";
    private static final float DEFAULT_WARNING_LEVEL_MS = (float) TimeUnit.SECONDS.toMillis(1) / 60;//16.67ms
    private static final float DEFAULT_ERROR_LEVEL_MS = DEFAULT_WARNING_LEVEL_MS * 2;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        startFrameMetrics(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.e(TAG, "onActivityResumed() called with: activity = [" + activity + "]");

    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.e(TAG, "onActivityPaused() called with: activity = [" + activity + "]");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e(TAG, "onActivityStopped() called with: activity = [" + activity + "]");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e(TAG, "onActivityDestroyed() called with: activity = [" + activity + "]");
        stopFrameMetrics(activity);
    }

    private float warningLevelMs;
    private float errorLevelMs;
    private boolean showWarning;
    private boolean showError;

    private Map<Activity, Window.OnFrameMetricsAvailableListener> frameMetricsAvailableListenerMap = new HashMap<>();

    private ActivityFrameMetrics() {
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void startFrameMetrics(final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Window.OnFrameMetricsAvailableListener listener = new Window.OnFrameMetricsAvailableListener() {

                private int allFrames = 0;
                private int jankyFrames = 0;

                long sumOfTotalDuration = 0L;
                long frameCount = 0L;

                @Override
                public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int dropCountSinceLastInvocation) {
                    FrameMetrics frameMetricsCopy = new FrameMetrics(frameMetrics);
                    allFrames++;
                    float totalDurationMs = (float) (0.000001 * frameMetricsCopy.getMetric(FrameMetrics.TOTAL_DURATION));
                    Log.d(TAG, "onFrameMetricsAvailable() called with: totalDurationMs = [" + totalDurationMs + "], DEFAULT_WARNING_LEVEL_MS = [" + DEFAULT_WARNING_LEVEL_MS + "], dropCountSinceLastInvocation = [" + dropCountSinceLastInvocation + "]");

                    if (totalDurationMs > warningLevelMs) {
                        jankyFrames++;
                        String msg = String.format("Janky frame detected on %s with total duration: %.2fms\n", activity.getClass().getName(), totalDurationMs);
                        float layoutMeasureDurationMs = (float) (0.000001 * frameMetricsCopy.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION));
                        float drawDurationMs = (float) (0.000001 * frameMetricsCopy.getMetric(FrameMetrics.DRAW_DURATION));
                        float gpuCommandMs = (float) (0.000001 * frameMetricsCopy.getMetric(FrameMetrics.COMMAND_ISSUE_DURATION));
                        float othersMs = totalDurationMs - layoutMeasureDurationMs - drawDurationMs - gpuCommandMs;
                        float jankyPercent = (float) jankyFrames / allFrames * 100;
                        msg += String.format("Layout/measure: %.2fms, draw:%.2fms, gpuCommand:%.2fms others:%.2fms\n",
                                layoutMeasureDurationMs, drawDurationMs, gpuCommandMs, othersMs);
                        msg += "Janky frames: " + jankyFrames + "/" + allFrames + "(" + jankyPercent + "%)";
                        if (showError && totalDurationMs > errorLevelMs) {
                            Log.e(TAG, msg);
                        } else if (showWarning && totalDurationMs > warningLevelMs) {
                            Log.w(TAG, msg);
                        }
                    }


                    frameCount++;
                    if (totalDurationMs < warningLevelMs) {//小于就补齐16ms
                        totalDurationMs = warningLevelMs;
                    }
                    sumOfTotalDuration += totalDurationMs;

                }
            };
            activity.getWindow().addOnFrameMetricsAvailableListener(listener, new Handler(Looper.getMainLooper()));
            frameMetricsAvailableListenerMap.put(activity, listener);
        } else {
            Log.w(TAG, "FrameMetrics can work only with Android SDK 24 (Nougat) and higher");
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void stopFrameMetrics(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener = frameMetricsAvailableListenerMap.get(activity);
            if (onFrameMetricsAvailableListener != null) {
                activity.getWindow().removeOnFrameMetricsAvailableListener(onFrameMetricsAvailableListener);
                frameMetricsAvailableListenerMap.remove(activity);
            }
        }
    }

    public void report(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            Window.OnFrameMetricsAvailableListener listener = frameMetricsAvailableListenerMap.get(activity);
            //listener.
        }
    }

    public static class Builder {
        private float warningLevelMs = DEFAULT_WARNING_LEVEL_MS;
        private float errorLevelMs = DEFAULT_ERROR_LEVEL_MS;
        private boolean showWarnings = true;
        private boolean showErrors = true;

        public Builder() {
        }

        public Builder warningLevelMs(float warningLevelMs) {
            this.warningLevelMs = warningLevelMs;
            return this;
        }

        public Builder errorLevelMs(float errorLevelMs) {
            this.errorLevelMs = errorLevelMs;
            return this;
        }

        public Builder showWarnings(boolean show) {
            this.showWarnings = show;
            return this;
        }

        public Builder showErrors(boolean show) {
            this.showErrors = show;
            return this;
        }

        public ActivityFrameMetrics build() {
            ActivityFrameMetrics activityFrameMetrics = new ActivityFrameMetrics();
            activityFrameMetrics.warningLevelMs = this.warningLevelMs;
            activityFrameMetrics.errorLevelMs = this.errorLevelMs;
            activityFrameMetrics.showError = this.showErrors;
            activityFrameMetrics.showWarning = this.showWarnings;
            return activityFrameMetrics;
        }
    }

    private static class InnerHolder {
        static final ActivityFrameMetrics INSTANCE = new ActivityFrameMetrics.Builder().build();
    }

    public static ActivityFrameMetrics getInstance() {
        return InnerHolder.INSTANCE;
    }
}