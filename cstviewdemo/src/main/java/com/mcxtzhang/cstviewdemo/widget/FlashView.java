package com.mcxtzhang.cstviewdemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/21.
 */

public class FlashView extends View {

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;


    public FlashView(Context context) {
        super(context);
    }

    public FlashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
        mPath.moveTo(200, 200);
        mPath.lineTo(200, 500);
        mPath.lineTo(500, 500);
        mPath.lineTo(500, 200);
        mPath.close();
        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, false);

        mDst = new Path();

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(10000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.start();
            }
        });
    }

    private float fraction;
    private Path mDst;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(mPath, mPaint);


        mDst.reset();
        // 硬件加速的BUG
        mDst.lineTo(0, 0);
        mPathMeasure.getSegment(mPathMeasure.getLength() * fraction - mPathMeasure.getLength() / 10, mPathMeasure.getLength() * fraction , mDst, true);
        mPaint.setColor(Color.WHITE);
        canvas.drawPath(mDst, mPaint);
    }
}
