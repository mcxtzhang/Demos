package com.mcxtzhang.viewinspector.suspend;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DebugSuspendViewService extends Service {
    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    Handler handler = new Handler();
    DebugSuspendViewManager mDebugSuspendViewManager;
    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceForegroundHelper.startForeground(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mDebugSuspendViewManager == null) {
            mDebugSuspendViewManager = DebugSuspendViewManager.INSTANCE;
        }

        // 开启定时器，每隔0.5秒刷新一次
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Service被终止的同时也停止定时器继续运行
        timer.cancel();
        timer = null;

        mDebugSuspendViewManager.removeSmallWindow(getApplicationContext());
    }

    /**
     * 判断当前程序是否正在运行
     */
    private boolean isAppRunning() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return isAppRunningBelowL();
        } else {
            return isAppRunningUpL();
        }
    }

    /**
     * android L版本以下判断
     */
    private boolean isAppRunningBelowL() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1);
        if (list == null) {
            return false;
        }
        String pkgName = getPackageName();
        for (RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(pkgName)
                    && info.baseActivity.getPackageName().equals(pkgName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * android L版本（包括L）以上判断
     */
    private boolean isAppRunningUpL() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final int PROCESS_STATE_TOP = 2;
        Field field = null;
        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
        } catch (Exception ex) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();
        if (appList == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo app : appList) {
            if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && app.importanceReasonCode == ActivityManager.RunningAppProcessInfo.REASON_UNKNOWN) {
                try {
                    int state = field.getInt(app);
                    if (state == PROCESS_STATE_TOP) {
                        return app.pid == android.os.Process.myPid();
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前界面是否是桌面
     */
    private boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        if (rti == null) {
            return false;
        }
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            boolean isAppRunning = isAppRunning();
            // 当前程序正在运行, 没有悬浮窗显示，则创建悬浮窗。
            if (isAppRunning && !mDebugSuspendViewManager.isWindowShowing()) {
                handler.removeCallbacks(mCreateWindowRunnable);
                handler.post(mCreateWindowRunnable);
            } else if (!isAppRunning && mDebugSuspendViewManager.isWindowShowing()) {
                handler.removeCallbacks(mRemoveWindowRunnabe);
                handler.post(mRemoveWindowRunnabe);
            }
        }

    }

    private final CreateWindowRunnable mCreateWindowRunnable = new CreateWindowRunnable();
    private final RemoveWindowRunnabe mRemoveWindowRunnabe = new RemoveWindowRunnabe();

    class CreateWindowRunnable implements Runnable {
        @Override
        public void run() {
            mDebugSuspendViewManager.createSmallWindow(getApplicationContext());
        }
    }

    class RemoveWindowRunnabe implements Runnable {
        @Override
        public void run() {
            mDebugSuspendViewManager.removeSmallWindow(getApplicationContext());
        }
    }
}
