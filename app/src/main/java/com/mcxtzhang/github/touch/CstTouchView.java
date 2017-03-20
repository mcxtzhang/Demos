package com.mcxtzhang.github.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/20.
 * History:
 */

public class CstTouchView extends View {
    public CstTouchView(Context context) {
        super(context);
    }

    public CstTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CstTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CstTouchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TAG", "onTouchEvent() called with: event = [" + event + "]");
        return true;

        //return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("TAG", "dispatchTouchEvent() called with: event = [" + event + "]");
        return super.dispatchTouchEvent(event);
    }
}
