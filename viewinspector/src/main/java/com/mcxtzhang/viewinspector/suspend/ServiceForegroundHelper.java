package com.mcxtzhang.viewinspector.suspend;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by huangyongzheng on 17/9/7.
 */

public class ServiceForegroundHelper {
    private static final int FOREGROUND_SERVICE_NOTIFICATION_ID = -37201;

    /**
     * 兼容Android8.0，使用该方法替代 {@link Context#startService(Intent)}
     * Android 8.0 不允许其创建后台服务的情况下使用startService()，否则抛出IllegalStateException。
     * https://developer.android.google.cn/about/versions/oreo/android-8.0-changes.html#back-all
     *
     * @param context
     * @param serviceIntent 启动service所需的Intent
     */
    public static void startService(@NonNull Context context, @NonNull Intent serviceIntent) {
        if (context == null || serviceIntent == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        } else {
            context.startService(serviceIntent);
        }
    }

    /**
     * Make this service run in the foreground
     * https://developer.android.google.cn/guide/components/activities/process-lifecycle.html
     *
     * @param service
     */
    public static void startForeground(@NonNull Service service) {
        if (service == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder builder = new Notification.Builder(service, "default")
                        .setContentTitle("")
                        .setContentText("");
                Notification notification = builder.build();
                service.startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, notification);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
