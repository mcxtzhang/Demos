package com.mcxtzhang.cstviewdemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * 介绍：仿饿了么加减Button
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/18.
 */

public class AddDelButton extends View {
    private Paint mPaint;
    private int mEnableColor;
    private int mDisableColor;

    //最大数量和当前数量
    private int mMaxCount;
    private int mCount;

    //圆的半径
    private float mRadius;
    //圆圈的宽度
    private float mCircleWidth;
    //线的宽度
    private float mLineWidth;


    //文字和圆的间距
    private float mGap;
    private float mTestSize;
    private Paint mTextPaint;
    private Paint.FontMetrics mFontMetrics;


    public AddDelButton(Context context) {
        this(context, null);
    }

    public AddDelButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddDelButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public int getCount() {
        return mCount;
    }

    public AddDelButton setCount(int count) {
        mCount = count;
        return this;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);

        mEnableColor = 0xff000000;
        mDisableColor = 0xff9c9c9c;
        mMaxCount = 1;
        mCount = 1;


        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12.5f, getResources().getDisplayMetrics());
        mCircleWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, getResources().getDisplayMetrics());
        mLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, getResources().getDisplayMetrics());

        mGap = mRadius / 3;

        mTestSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14.5f, getResources().getDisplayMetrics());
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTestSize);
        mFontMetrics = mTextPaint.getFontMetrics();


        //以后删除
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount = (mCount + 1) % (mMaxCount + 1);
                if (mCount == 0) {
                    //动画

                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mRadius * 2 + mGap * 2 + mTextPaint.measureText(mCount + ""));
                    valueAnimator.setDuration(350);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            //透明度 旋转 位移
                            mAnimOffset = (float) animation.getAnimatedValue();
                            Log.d("TAG", "mAnimOffset() called with: animation = [" + mAnimOffset + "]");
                            invalidate();
                        }
                    });

                    ValueAnimator alpha = ValueAnimator.ofInt(255, 0);
                    alpha.setDuration(350);
                    alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            //透明度 旋转 位移
                            mAnimAlpha = (int) animation.getAnimatedValue();
                            Log.d("TAG", "mAnimAlpha() called with: animation = [" + mAnimAlpha + "]");
                            invalidate();
                        }
                    });

                    ValueAnimator rotate = ValueAnimator.ofInt(0, 360);
                    rotate.setDuration(350);
                    rotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            //透明度 旋转 位移
                            mAnimRotate = (int) animation.getAnimatedValue();
                            Log.d("TAG", "mAnimRotate() called with: animation = [" + mAnimRotate + "]");
                            invalidate();
                        }
                    });
                    rotate.start();
                    alpha.start();
                    valueAnimator.start();

                } else {
                    mAnimRotate = 0;
                    mAnimAlpha = 255;
                    mAnimOffset = 0;
                    invalidate();
                }

            }
        });
    }

    private float mAnimOffset = 0;
    private int mAnimAlpha = 255;
    private int mAnimRotate = 0;

    private int mLeft, mTop;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeft = getPaddingLeft();
        mTop = getPaddingTop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //左边
        if (mCount > 0) {
            mPaint.setColor(mEnableColor);
        } else {
            //mPaint.setColor(Color.TRANSPARENT);
        }
        mPaint.setAlpha(mAnimAlpha);



        mPaint.setStrokeWidth(mCircleWidth);
        canvas.drawCircle(mAnimOffset + mLeft + mRadius, mTop + mRadius, mRadius, mPaint);
        mPaint.setStrokeWidth(mLineWidth);


        //旋转动画
        canvas.save();
        canvas.translate(mAnimOffset + mLeft + mRadius , mTop + mRadius);
        canvas.rotate(mAnimRotate);
        /*canvas.drawLine(mAnimOffset + mLeft + mRadius / 2, mTop + mRadius,
                mAnimOffset + mLeft + mRadius / 2 + mRadius, mTop + mRadius,
                mPaint);*/
        canvas.drawLine(-mRadius / 2, 0,
                + mRadius / 2 , 0,
                mPaint);
        canvas.restore();


        //数量
        canvas.drawText(mCount + "", mGap + mLeft + mRadius * 2, mTop + mRadius - (mFontMetrics.top + mFontMetrics.bottom) / 2, mTextPaint);

        //右边
        if (mCount < mMaxCount) {
            mPaint.setColor(mEnableColor);
        } else {
            mPaint.setColor(mDisableColor);
        }
        mPaint.setStrokeWidth(mCircleWidth);
        float left = mLeft + mRadius * 2 + mGap * 2 + mTextPaint.measureText(mCount + "");
        canvas.drawCircle(left + mRadius, mTop + mRadius, mRadius, mPaint);
        mPaint.setStrokeWidth(mLineWidth);
        canvas.drawLine(left + mRadius / 2, mTop + mRadius, left + mRadius / 2 + mRadius, mTop + mRadius, mPaint);
        canvas.drawLine(left + mRadius, mTop + mRadius / 2, left + mRadius, mTop + mRadius / 2 + mRadius, mPaint);
    }

}
