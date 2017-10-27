package anlaiye.com.cn.performancedemo.monitor;

import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;
import android.view.Choreographer;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/10/26.
 * History:
 */

public class PerformanceMonitorUtils {
    private static final String TAG = "zxt/PerformanceMonitorUtils";

    public static void monitorMainLooper() {

        Looper.getMainLooper().setMessageLogging(new Printer() {
            boolean isStarted = false;
            long lastTime;

            @Override
            public void println(String x) {
                //只有looper里有msg 才会执行
                if (isStarted) {
                    isStarted = false;
                    Log.d("TAG", "本次主线程 操作执行时间 : x = [" + (System.currentTimeMillis() - lastTime) + "]");
                } else {
                    isStarted = true;
                    lastTime = System.currentTimeMillis();
                }
            }
        });
    }

    public static void monitorChoreoGrapher() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //一直会执行, 这里的消息其实是插入到主线程的handler里执行的，所以如果这里有任务，那么主线程的Handler也会一直运行。 上个方法monitorMainLooper也会一直运行
            Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
                long lastTime;

                @Override
                public void doFrame(long frameTimeNanos) {
                    long gap = ((frameTimeNanos - lastTime) / 1000000);
                    Log.d(TAG, "doFrame: frameTimeNanos = [" + frameTimeNanos + "]" + "lasttime:" + lastTime + ", gap:" + gap);
                    if (gap > 16) {
                        long count = (gap - 16) / 16;
                        Log.e(TAG, "丢帧 : frameTimeNanos = [" + frameTimeNanos + "]" + "lasttime:" + lastTime + ", gap:" + gap + ",丢了几帧:" + count);

                    }
                    lastTime = frameTimeNanos;
                    Choreographer.getInstance().postFrameCallback(this);
                }
            });
        }
    }
}
