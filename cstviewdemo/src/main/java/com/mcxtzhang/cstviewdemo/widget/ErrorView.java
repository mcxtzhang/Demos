package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.cstviewdemo.outpututils.PathAnimHelper;

public class ErrorView extends View {

    private Paint mPaint;
    private Path mPath;
    //private PathMeasure mPathMeasure;
    private Path dstPath;

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);

        mPath = new Path();
        mPath.moveTo(100, 100);
        mPath.rLineTo(100, 100);
        mPath.moveTo(200, 100);
        mPath.rLineTo(-100, 100);

        mPath.moveTo(400, 100);
        mPath.lineTo(500, 100);

        //mPathMeasure = new PathMeasure();
        dstPath = new Path();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //startAnim();
                new PathAnimHelper(ErrorView.this, mPath, dstPath).startAnim();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawPath(dstPath, mPaint);
    }
/*

    public void startAnim() {
        //这里每次都重置，因为每次点击就都可以看到效果  
        dstPath.reset();
        dstPath.lineTo(0, 0);
        mPathMeasure.setPath(mPath, false);

        doAnim();
    }

    private void doAnim() {
        ValueAnimator mValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(200);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                //19版本之前要想看到效果最简单的方法就是使用rLineTo(0,0)
                dstPath.rLineTo(0, 0);
                //获取一个段落
                mPathMeasure.getSegment(0, mPathMeasure.getLength() * value, dstPath, true);
                invalidate();
            }
        });

        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //绘制完一条线之后，再绘制另外一条线
                mPathMeasure.nextContour();
                if (mPathMeasure.getLength() != 0) {
                    doAnim();
                }
                Log.e("TAG", "onAnimationEnd: ");
            }
        });
        mValueAnimator.start();
    }*/


}  