package com.mcxtzhang.viewinspector.suspend;

import android.os.Build;
import android.support.annotation.NonNull;
import android.view.WindowManager;

/**
 * Created by tianbei on 2017/9/8.
 */

public class WindowManagerLayoutParamsUtils {

    /**
     * 兼容Android 8.0 设置窗口类型
     *
     * @param layoutParams
     * @param type
     */
    public static void setType(@NonNull WindowManager.LayoutParams layoutParams, int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (type == WindowManager.LayoutParams.TYPE_PHONE || type == WindowManager.LayoutParams.TYPE_PRIORITY_PHONE
                    || type == WindowManager.LayoutParams.TYPE_SYSTEM_ALERT || type == WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
                    || type == WindowManager.LayoutParams.TYPE_SYSTEM_ERROR) {
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            }
        }
        if (layoutParams != null) {
            layoutParams.type = type;
        }
    }

}
