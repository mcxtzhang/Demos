package anlaiye.com.cn.performancedemo.monitor.framemetrics;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangxutong on 2018/2/5.
 */

public abstract class AbstractDpFrameMonitor<T extends Window.OnFrameMetricsAvailableListener> implements IFrameMonitor {
    protected static final String TAG = "TAG/AF";
    public static final float DEFAULT_WARNING_LEVEL_MS = (float) TimeUnit.SECONDS.toMillis(1) / 60;//16.67ms
    public static final float DEFAULT_ERROR_LEVEL_MS = DEFAULT_WARNING_LEVEL_MS * 2;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        startMonitor(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i(TAG, "onActivityResumed() called with: activity = [" + activity + "]");

    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i(TAG, "onActivityPaused() called with: activity = [" + activity + "]");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.i(TAG, "onActivityStopped() called with: activity = [" + activity + "]");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i(TAG, "onActivityDestroyed() called with: activity = [" + activity + "]");
        stopMonitor(activity);
    }

    protected float warningLevelMs;
    protected float errorLevelMs;
    protected boolean showWarning;
    protected boolean showError;

    protected Map<Activity, T> frameMetricsAvailableListenerMap = new HashMap<>();


    @Override
    public void startMonitor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            T listener = createMonitor();
            activity.getWindow().addOnFrameMetricsAvailableListener(listener, new Handler(Looper.getMainLooper()));
            frameMetricsAvailableListenerMap.put(activity, listener);
        } else {
            Log.w(TAG, "FrameMetrics can work only with Android SDK 24 (Nougat) and higher");
        }
    }

    public abstract T createMonitor();

    @Override
    public void stopMonitor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener = frameMetricsAvailableListenerMap.get(activity);
            if (onFrameMetricsAvailableListener != null) {
                activity.getWindow().removeOnFrameMetricsAvailableListener(onFrameMetricsAvailableListener);
                frameMetricsAvailableListenerMap.remove(activity);
            }
        }
    }

}
