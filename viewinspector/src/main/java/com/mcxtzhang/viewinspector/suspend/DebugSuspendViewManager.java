package com.mcxtzhang.viewinspector.suspend;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import java.util.Map;


public enum DebugSuspendViewManager {
    INSTANCE;

    /**
     * 小悬浮窗View的参数
     */
    private static LayoutParams smallWindowParams;
    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;
    /**
     * 小悬浮窗View的实例
     */
    private DebugSuspendView smallWindow;

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    void createSmallWindow(final Context context) {
        WindowManager windowManager = getWindowManager(context);

        int screenWidth = ViewUtils.getScreenWidthPixels(context);
        int screenHeight = ViewUtils.getScreenHeightPixels(context);
        if (smallWindow == null) {
            smallWindow = new DebugSuspendView(context);
            if (smallWindowParams == null) {
                smallWindowParams = new LayoutParams();
                //在版本>24后，https://android-developers.blogspot.com/2016/12/welcoming-android-711-nougat.html
                //在版本<19时，会遇到Toast类型无法进行交互
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <= 23)
                    smallWindowParams.type = LayoutParams.TYPE_TOAST;
                else {
                    WindowManagerLayoutParamsUtils.setType(smallWindowParams, LayoutParams.TYPE_PHONE);
                }
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.START | Gravity.TOP;
                smallWindowParams.width = DebugSuspendView.viewWidth;
                smallWindowParams.height = DebugSuspendView.viewHeight;
                smallWindowParams.x = 0;
                smallWindowParams.y = screenHeight / 2;
            }
        }
        smallWindow.setParams(smallWindowParams);

        try {
            if (Build.VERSION.SDK_INT > 24 && !Settings.canDrawOverlays(context)) {
                smallWindow = null;
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                context.startActivity(intent);
            } else
                windowManager.addView(smallWindow, smallWindowParams);
        } catch (WindowManager.BadTokenException e) {
            smallWindow = null;//捕获异常后，重置为null，保证下次能够add。
            e.printStackTrace();
        } catch (IllegalStateException e) {//解决个案，模拟器中有时会出现 查看WindowManagerGlobal.addView()
            // windowManager.removeView(smallWindow);
            e.printStackTrace();
        } catch (Exception e) {
            smallWindow = null;
            e.printStackTrace();
        }

    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    void removeSmallWindow(Context context) {
        if (smallWindow != null && smallWindow.getParent() != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }


    final Map<String, String> mMonitorDatas = new android.support.v4.util.ArrayMap<>();

    public void updateFps(float fps) {
        try {
            mMonitorDatas.put("Fps", String.valueOf(fps));
        } catch (Exception e) {
        }
        updateViewContent(mMonitorDatas);
    }

    public void updateViewContent(Map<String, String> monitorDatas) {
        if (smallWindow != null) {
            smallWindow.updateContent(monitorDatas);
        }
    }


    boolean isWindowShowing() {
        return smallWindow != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }


}