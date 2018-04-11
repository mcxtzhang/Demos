package com.mcxtzhang.cstviewdemo.suspend;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zhangxutong on 2018/3/29.
 */

public class DebugFpsView extends LinearLayout {
    private static final String TAG = "DebugFpsView";

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mScreenMiddleX;

    public DebugFpsView(Context context) {
        super(context);
        init(context);
    }

    public DebugFpsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DebugFpsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = mWindowManager.getDefaultDisplay().getWidth();
        mScreenHeight = mWindowManager.getDefaultDisplay().getHeight();
        mScreenMiddleX = mScreenWidth / 2;

        TextView tv = new TextView(getContext());
        tv.setText("123");
        addView(tv);

        setBackgroundColor(Color.RED);
    }

    private PointF mDownPoint;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent() called with: event = [" + event + "]");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownPoint = new PointF(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = event.getRawX() - mDownPoint.x;
                float offsetY = event.getRawY() - mDownPoint.y;
                offsetLeftAndRight((int) offsetX);
                offsetTopAndBottom((int) offsetY);
                Log.d(TAG, "dispatchTouchEvent() called with: offsetX = [" + offsetX + "], offsetY = [" + offsetY + "]");
                mDownPoint = new PointF(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float viewX = event.getRawX();
                if (viewX < mScreenMiddleX) {
                    offsetLeftAndRight(0);
                } else {
                    offsetLeftAndRight(mScreenWidth - getWidth());
                }
                break;
        }

        return true;
    }

    public void setParams(WindowManager.LayoutParams lp) {
        this.mLayoutParams = lp;
    }

    private void updatePosition() {

    }
}
