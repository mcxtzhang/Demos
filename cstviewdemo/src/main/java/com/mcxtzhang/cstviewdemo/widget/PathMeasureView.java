package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * 介绍：PathMeasure's Demo
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/15.
 */

public class PathMeasureView extends View {

    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDst;
    private float mLength;

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private PathEffect mEffect;

    public PathMeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
/*        mPathMeasure = new PathMeasure();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(100, 100);
        mPath.lineTo(100, 500);
        mPath.lineTo(400, 300);
        mPath.close();


        //mPath.addCircle(400, 400, 100, Path.Direction.CW);
        mPathMeasure.setPath(mPath, false);
        mLength = mPathMeasure.getLength();
        mDst = new Path();

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                mEffect = new DashPathEffect(new float[]{mLength, mLength}, fraction * mLength);
                mPaint.setPathEffect(mEffect);

                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
/*        mDst.reset();
        // 硬件加速的BUG
        mDst.lineTo(0, 0);
        float stop = mLength * mAnimatorValue;
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));

        mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);*/
        //canvas.drawPath(mPath,mPaint);


        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(90);
        PathEffect effects = new DashPathEffect(new float[] { 100,100}, 100);
        p.setPathEffect(effects);
        canvas.drawLine(0, 40, 100, 40, p);
    }
}
