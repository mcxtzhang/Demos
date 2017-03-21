package com.mcxtzhang.github.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/20.
 * History:
 */

public class CstTouchViewGroup extends ViewGroup {
    public CstTouchViewGroup(Context context) {
        super(context);
    }

    public CstTouchViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CstTouchViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CstTouchViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("TAG1", "onLayout() called with: changed = [" + getChildCount());
        getChildAt(0).layout(getPaddingLeft(), getPaddingTop(),
                getPaddingLeft() + 300, getPaddingTop() + 300);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TAG1", "onTouchEvent() called with: event = [" + event + "]");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("TAG1", "onInterceptTouchEvent() called with: ev = [" + ev + "]");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("TAG1", "dispatchTouchEvent() called with: ev = [" + ev + "]");
        return super.dispatchTouchEvent(ev);
    }



    @Override
    public boolean isTransitionGroup() {
        Log.d("TAG1", "isTransitionGroup() called");
        return true;
    }
}
