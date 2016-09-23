package com.mcxtzhang.cstviewdemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/22.
 */

public class FlashTextView extends View {
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private Path mPath;
    private Path mDst;
    private PathEffect mPathEffect;


    public FlashTextView(Context context) {
        super(context);
    }

    public FlashTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(50);

        mPath = new Path();
        mPath.moveTo(200, 200);
        mPath.lineTo(200, 500);
        mPath.lineTo(500, 200);
        mPath.lineTo(500, 500);

        mDst = new Path();

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, false);


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mFraction = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.start();
            }
        });
    }

    private float mFraction=0.5f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(mPath, mPaint);
        canvas.drawTextOnPath("俺来也俺来也俺来也俺来也俺来也俺来也俺来也俺来也俺来也俺来也", mPath, 0, 0, mPaint);


        //mPathEffect = new DashPathEffect(new float[]{mPathMeasure.getLength(), mPathMeasure.getLength()}, mFraction * mPathMeasure.getLength());
        mPaint.setColor(Color.WHITE);
        //mPaint.setPathEffect(mPathEffect);

        mDst.reset();
        mDst.moveTo(0, 0);
        mPathMeasure.getSegment(mPathMeasure.getLength() * mFraction, mPathMeasure.getLength() * mFraction + mPathMeasure.getLength() / 10, mDst, true);
        canvas.drawTextOnPath("俺来也", mDst, 0, 0, mPaint);


    }
}
