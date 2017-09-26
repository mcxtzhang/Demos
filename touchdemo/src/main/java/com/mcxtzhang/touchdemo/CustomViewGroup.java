package com.mcxtzhang.touchdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://bLog.csdn.net/zxt0601
 * Created:   2017/8/12.
 * History:
 */

public class CustomViewGroup extends LinearLayout {

    private static final String TAG = "zxt/CustomViewGroup";

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
       Log.w(TAG, "dispatchTouchEvent() called with: ev = [" + ev + "]");
        //super.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

/*    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
       Log.w(TAG, "onInterceptTouchEvent() called with: ev = [" + ev + "]");

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
            default:
                return true;
        }
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       Log.w(TAG, "onTouchEvent() called with: event = [" + event + "]");
        super.onTouchEvent(event);
        return false;
    }
/*

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure() called with: widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e(TAG, "onLayout() called with: changed = [" + changed + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw() called with: canvas = [" + canvas + "]");
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e(TAG, "dispatchDraw() called with: canvas = [" + canvas + "]");
        super.dispatchDraw(canvas);
    }*/
}
