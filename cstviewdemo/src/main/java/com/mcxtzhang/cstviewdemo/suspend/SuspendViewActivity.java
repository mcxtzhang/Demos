package com.mcxtzhang.cstviewdemo.suspend;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.WindowManager;

import com.mcxtzhang.cstviewdemo.R;

public class SuspendViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend_view);
/*
        ViewGroup vg = (ViewGroup) findViewById(R.id.root);

        vg.addView(new DebugFpsView(this));*/

        /*View decorView = getWindow().getDecorView();
        decorView = decorView.findViewById(android.R.id.content);
        Log.d("TAG", "onCreate() called with: decorView = [" + decorView + "]");
        if (decorView instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) decorView;
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            frameLayout.addView(new DebugFpsView(this), frameLayout.getChildCount() - 1,lp );
        }*/

        createSmallWindow(this);

    }

    void createSmallWindow(final Context context) {
        WindowManager windowManager = getWindowManager(context);

        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (smallWindow == null) {
            smallWindow = new DebugFpsView(context);
            if (smallWindowParams == null) {
                smallWindowParams = new WindowManager.LayoutParams();
                //在版本>24后，https://android-developers.blogspot.com/2016/12/welcoming-android-711-nougat.html
                //在版本<19时，会遇到Toast类型无法进行交互
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <= 23)
                    smallWindowParams.type = WindowManager.LayoutParams.TYPE_TOAST;
                else {
                    WindowManagerLayoutParamsUtils.setType(smallWindowParams, WindowManager.LayoutParams.TYPE_PHONE);
                }
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.START | Gravity.TOP;
                smallWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                smallWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }
        }
        //smallWindow.setParams(smallWindowParams);

        try {
            if (Build.VERSION.SDK_INT > 24 && !Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                context.startActivity(intent);
//                    smallWindow = null;
//
            } else
                windowManager.addView(smallWindow, smallWindowParams);
        } catch (WindowManager.BadTokenException e) {
            smallWindow = null;//捕获异常后，重置为null，保证下次能够add。
            e.printStackTrace();
        } catch (IllegalStateException e) {//解决个案，模拟器中有时会出现 查看WindowManagerGlobal.addView()
            // windowManager.removeView(smallWindow);
            e.printStackTrace();
        }

    }


    /**
     * 小悬浮窗View的参数
     */
    private static WindowManager.LayoutParams smallWindowParams;
    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;
    /**
     * 小悬浮窗View的实例
     */
    private DebugFpsView smallWindow;

    private WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
}
