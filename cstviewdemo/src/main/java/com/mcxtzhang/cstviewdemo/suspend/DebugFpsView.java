package com.mcxtzhang.cstviewdemo.suspend;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zhangxutong on 2018/3/29.
 */

public class DebugFpsView extends LinearLayout {
    private static final String TAG = "DebugFpsView";

    public DebugFpsView(Context context) {
        super(context);
        init();
    }

    public DebugFpsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public DebugFpsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        TextView tv = new TextView(getContext());
        tv.setText("123");
        addView(tv);
    }

    private PointF mDownPoint;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent() called with: event = [" + event + "]");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = event.getRawX() - mDownPoint.x;
                float offsetY = event.getRawY() - mDownPoint.y;
                offsetLeftAndRight((int) offsetX);
                offsetTopAndBottom((int) offsetY);
                Log.d(TAG, "dispatchTouchEvent() called with: offsetX = [" + offsetX + "], offsetY = [" + offsetY + "]");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        mDownPoint = new PointF(event.getRawX(), event.getRawY());

        return true;
    }
}
