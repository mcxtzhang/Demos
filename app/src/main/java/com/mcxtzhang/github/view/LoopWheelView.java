package com.mcxtzhang.github.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 介绍：自定义循环滚动选择View
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/8/26.
 */
public class LoopWheelView extends View {
    private static final String TAG = "zxt/LoopWheelView";
    private int unit = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());//每格的宽度

    private String[] datas = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14"};
    private int mIndex;//当前处于第几个

    private Paint mPaint;

    public LoopWheelView(Context context) {
        this(context, null);
    }

    public LoopWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private int mWidth, mHeight;

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        mIndex = 0;
        setClickable(true);
/*        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex++;
                invalidate();
            }
        });*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw() called with: " + "canvas = [" + canvas + "]");

        int count = mHeight / unit;
        if (count % 2 == 0) {
            count = -1;//如果是偶数，减一成奇数
        }
        float contentHeight = unit * count;
        float gap = mHeight - contentHeight;
        setBackgroundColor(Color.WHITE);
        for (int i = 0; i < count; i++) {
            //canvas.drawRect(getPaddingLeft(), getPaddingTop() + (gap / 2) + unit * i, width - getPaddingRight(), getPaddingTop() + (gap / 2) + unit * (i + 1), paint);
            mPaint.setColor(Color.BLACK);
            if (i == count / 2) {
                mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, getResources().getDisplayMetrics()));
            } else {
                mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
            }
            Rect bound = new Rect();
            mPaint.getTextBounds(datas[(i + mIndex) % datas.length], 0, datas[(i + mIndex) % datas.length].length(), bound);

            canvas.drawText(datas[(i + mIndex) % datas.length], mWidth / 2 - bound.width() / 2, getPaddingTop() + gap / 2 + unit * i + unit / 2, mPaint);
        }
    }

    private float lastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                scrollBy(0, (int) (lastY - event.getRawY()));
                lastY = event.getRawY();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
