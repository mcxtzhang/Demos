package com.mcxtzhang.viewinspector.suspend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcxtzhang.viewinspector.R;
import com.mcxtzhang.viewinspector.suspend.full.AttributeViewerManager;

import java.lang.reflect.Field;
import java.util.Map;

public class DebugSuspendView extends LinearLayout {

    /**
     * 记录小悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录小悬浮窗的高度
     */
    public static int viewHeight;

    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager windowManager;

    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    private View layoutView;

    private int screenWidth;
    private int screenHeight;

    private TextView mContentTv;

    private UpdateContentRunnable mUpdateContentRunnable;

    private ResetContentRunnable mResetContentRunnable;

    private static class UpdateContentRunnable implements Runnable {

        TextView contentTv;
        String content;

        public UpdateContentRunnable(TextView contentTv) {
            this.contentTv = contentTv;
            this.content = "";
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public void run() {
            contentTv.setText(content);
        }
    }

    private static class ResetContentRunnable implements Runnable {
        TextView contentTv;

        public ResetContentRunnable(TextView contentTv) {
            this.contentTv = contentTv;
        }

        @Override
        public void run() {
            contentTv.setText("adadasdasdasd");
        }
    }

    public DebugSuspendView(final Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater.from(context).inflate(R.layout.dolphin_debug_panel, this);
        layoutView = findViewById(R.id.small_window_layout);
        mContentTv = findViewById(R.id.tvContent);
        viewWidth = layoutView.getLayoutParams().width;
        viewHeight = layoutView.getLayoutParams().height;

        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();

        mUpdateContentRunnable = new UpdateContentRunnable(mContentTv);
        mResetContentRunnable = new ResetContentRunnable(mContentTv);
    }

    public void updateContent(Map<String, String> monitorDatas) {
        if (null == monitorDatas) return;
        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : monitorDatas.entrySet()) {
            sb.append(entry.getKey())
                    .append(":")
                    .append(entry.getValue());
        }
        mContentTv.removeCallbacks(mResetContentRunnable);
        mUpdateContentRunnable.setContent(sb.toString());
        mContentTv.post(mUpdateContentRunnable);
        mContentTv.postDelayed(mResetContentRunnable, 5000);

/*        viewWidth = mContentTv.getMeasuredWidth();
        viewHeight = mContentTv.getMeasuredHeight();*/
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchBegin();
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                touchEnd();
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (Math.abs(xDownInScreen - xInScreen) < 3
                        && Math.abs(yDownInScreen - yInScreen) < 3) {
                    openBigWindow();
                }
                AttributeViewerManager.INSTANCE.createSmallWindow(getContext());
                break;
            default:
                break;
        }
        return true;
    }

    private void touchBegin() {
        layoutView.getBackground().setAlpha(125);
    }

    private void touchEnd() {
        layoutView.getBackground().setAlpha(255);

        int centerSceenX = screenWidth / 2;
        int x = (int) (xInScreen - xInView);
        int centerViewX = x + viewWidth / 2;
        if (centerViewX < centerSceenX) {
            x = 0;
        } else {
            x = screenWidth - viewWidth;
        }
        xInScreen = x + xInView;
        updateViewPosition();
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        mParams.windowAnimations = 0;
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 打开大悬浮窗，同时关闭小悬浮窗。
     */
    private void openBigWindow() {
//        Intent intent = new Intent("com.dianping.action.VIEW");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setData(Uri.parse("dianping://debugpanel"));
//        DPApplication.instance().startActivity(intent);
        // DebugSuspendViewManager.createBigWindow(getContext());
        // DebugSuspendViewManager.removeSmallWindow(getContext());
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

}
