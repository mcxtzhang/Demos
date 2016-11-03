package com.mcxtzhang.cstviewdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.mcxtzhang.cstviewdemo.widget.res.StoreHousePath;

import java.util.ArrayList;

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

    public FlashView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
/*        mPath.moveTo(200, 200);
        mPath.lineTo(200, 500);
        mPath.lineTo(500, 500);
        mPath.lineTo(500, 200);
        //mPath.close();

        mPath.moveTo(900, 900);
        mPath.lineTo(900, 1000);
        mPath.lineTo(100, 1000);
        mPath.lineTo(900, 900);*/

        ArrayList<float[]> path = StoreHousePath.getPath("AnLaiYe");
        for (int i = 0; i < path.size(); i++) {
            float[] floats = path.get(i);
            mPath.moveTo(floats[0], floats[1]);
            mPath.lineTo(floats[2], floats[3]);
        }


        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, false);

        mDst = new Path();

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();

                //mPathMeasure.getSegment(mPathMeasure.getLength() * fraction - mPathMeasure.getLength() / 10, mPathMeasure.getLength() * fraction, mDst, true);
                mPathMeasure.getSegment(0, mPathMeasure.getLength() * fraction, mDst, true);
                invalidate();
            }
        });



        final boolean isInfinite = true;
        AnimatorListenerAdapter endListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                Log.e("TAG", "onAnimationCancel: ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mPathMeasure.nextContour();
                if (mPathMeasure.getLength() == 0) {
                    if (isInfinite) {
                        mDst.reset();
                        // 硬件加速的BUG
                        mDst.lineTo(0, 0);
                        mPathMeasure.setPath(mPath, false);
                    } else {
                        animation.end();
                    }

                }
                Log.e("TAG", "onAnimationRepeat: ");
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("TAG", "onAnimationStart() called with: animation = [" + animation + "]");
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //mPathMeasure.nextContour();
                Toast.makeText(context, "结束", Toast.LENGTH_SHORT).show();
                Log.w("TAG", "onAnimationEnd: ");

            }
        };
        valueAnimator.addListener(endListener);
        valueAnimator.setDuration(100);
        valueAnimator.setInterpolator(new LinearInterpolator());
        //valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        setOnClickListener(new OnClickListener() {
            boolean open = true;

            @Override
            public void onClick(View v) {
                if (open) {
                    open =false;
                    mDst.reset();
                    // 硬件加速的BUG
                    mDst.lineTo(0, 0);
                    valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                    mPathMeasure.setPath(mPath, false);
                    valueAnimator.start();
                } else {
                    valueAnimator.setRepeatCount(0);
                    valueAnimator.end();
                }
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


        mPaint.setColor(Color.WHITE);
        canvas.drawPath(mDst, mPaint);
    }
}
