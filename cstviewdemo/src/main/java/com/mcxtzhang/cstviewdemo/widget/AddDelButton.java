package com.mcxtzhang.cstviewdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 介绍：仿饿了么加减Button
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/18.
 */

public class AddDelButton extends View {
    //加减的圆的Path的Region
    private Region mAddRegion, mDelRegion;
    private Path mAddPath, mDelPath;

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
        mAddRegion = new Region();
        mDelRegion = new Region();
        mAddPath = new Path();
        mDelPath = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);

        mEnableColor = 0xff000000;
        mDisableColor = 0xff9c9c9c;
        mMaxCount = 4;
        mCount = 1;


        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12.5f, getResources().getDisplayMetrics());
        mCircleWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, getResources().getDisplayMetrics());
        mLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, getResources().getDisplayMetrics());

        mGap = mRadius / 3;

        mTestSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14.5f, getResources().getDisplayMetrics());
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTestSize);
        mFontMetrics = mTextPaint.getFontMetrics();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                int computeSize = (int) (getPaddingLeft() + mRadius * 2 + mGap * 2 + mTextPaint.measureText(mCount + "") + mRadius * 2 + getPaddingRight());
                wSize = computeSize < wSize ? computeSize : wSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                computeSize = (int) (getPaddingLeft() + mRadius * 2 + mGap * 2 + mTextPaint.measureText(mCount + "") + mRadius * 2 + getPaddingRight());
                wSize = computeSize;
                break;
        }
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                int computeSize = (int) (getPaddingTop() + mRadius * 2 + getPaddingBottom());
                hSize = computeSize < hSize ? computeSize : hSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                computeSize = (int) (getPaddingTop() + mRadius * 2 + getPaddingBottom());
                hSize = computeSize;
                break;
        }


        setMeasuredDimension(wSize, hSize);
    }


    private int mLeft, mTop;
    private int mWidth, mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeft = getPaddingLeft();
        mTop = getPaddingTop();
        mWidth = w;
        mHeight = h;
    }

    private float mAnimOffset = 0;
    private int mAnimAlpha = 255;
    private int mAnimRotate = 0;

    private float mAnimFraction;

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
        mDelPath.reset();
        mDelPath.addCircle(mAnimOffset + mLeft + mRadius, mTop + mRadius, mRadius, Path.Direction.CW);
        mDelRegion.setPath(mDelPath, new Region(mLeft, mTop, mWidth - getPaddingRight(), mHeight - getPaddingBottom()));
        //canvas.drawCircle(mAnimOffset + mLeft + mRadius, mTop + mRadius, mRadius, mPaint);
        canvas.drawPath(mDelPath, mPaint);


        mPaint.setStrokeWidth(mLineWidth);


        //旋转动画
        canvas.save();
        canvas.translate(mAnimOffset + mLeft + mRadius, mTop + mRadius);
        canvas.rotate(mAnimRotate);
        /*canvas.drawLine(mAnimOffset + mLeft + mRadius / 2, mTop + mRadius,
                mAnimOffset + mLeft + mRadius / 2 + mRadius, mTop + mRadius,
                mPaint);*/
        canvas.drawLine(-mRadius / 2, 0,
                +mRadius / 2, 0,
                mPaint);
        canvas.restore();


        //数量
        canvas.save();
        //平移动画
        canvas.translate(mAnimFraction * (mGap + mRadius), 0);
        //旋转动画,旋转中心点，x 是绘图中心,y 是控件中心
        canvas.rotate(360 * mAnimFraction,
                /*mAnimFraction * (mGap + mRadius) +*/mGap + mLeft + mRadius * 2 + mTextPaint.measureText(mCount + "") / 2,
                mTop + mRadius);
        //透明度动画
        mTextPaint.setAlpha((int) (255 * (1 - mAnimFraction)));
        //是没有动画的普通写法,x left, y baseLine
        canvas.drawText(mCount + "", mGap + mLeft + mRadius * 2, mTop + mRadius - (mFontMetrics.top + mFontMetrics.bottom) / 2, mTextPaint);
        canvas.restore();


        //右边
        if (mCount < mMaxCount) {
            mPaint.setColor(mEnableColor);
        } else {
            mPaint.setColor(mDisableColor);
        }
        mPaint.setStrokeWidth(mCircleWidth);
        float left = mLeft + mRadius * 2 + mGap * 2 + mTextPaint.measureText(mCount + "");
        mAddPath.reset();
        mAddPath.addCircle(left + mRadius, mTop + mRadius, mRadius, Path.Direction.CW);
        mAddRegion.setPath(mAddPath, new Region(mLeft, mTop, mWidth - getPaddingRight(), mHeight - getPaddingBottom()));
        //canvas.drawCircle(left + mRadius, mTop + mRadius, mRadius, mPaint);
        canvas.drawPath(mAddPath, mPaint);

        mPaint.setStrokeWidth(mLineWidth);
        canvas.drawLine(left + mRadius / 2, mTop + mRadius, left + mRadius / 2 + mRadius, mTop + mRadius, mPaint);
        canvas.drawLine(left + mRadius, mTop + mRadius / 2, left + mRadius, mTop + mRadius / 2 + mRadius, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                if (mAddRegion.contains((int) event.getX(), (int) event.getY())) {
                    onAddClick();
                    return true;
                } else if (mDelRegion.contains((int) event.getX(), (int) event.getY())) {
                    onDelClick();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }


        return super.onTouchEvent(event);

    }

    private void onDelClick() {
        if (mCount > 0) {
            mCount--;
            onCountDelListener();
        }

    }

    private void onAddClick() {
        if (mCount < mMaxCount) {
            mCount++;
            onCountAddListener();
        }
    }

    private void onCountAddListener() {
        if (mCount == 1) {
            //动画
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator translateDel = ValueAnimator.ofFloat(mRadius * 2 + mGap * 2 + mTextPaint.measureText(mCount + ""),0);
            translateDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //透明度 旋转 位移
                    mAnimOffset = (float) animation.getAnimatedValue();
                    Log.d("TAG", "mAnimOffset() called with: animation = [" + mAnimOffset + "]");
                    invalidate();
                }
            });
            ValueAnimator alphaDel = ValueAnimator.ofInt(0, 255);
            alphaDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //透明度 旋转 位移
                    mAnimAlpha = (int) animation.getAnimatedValue();
                    Log.d("TAG", "mAnimAlpha() called with: animation = [" + mAnimAlpha + "]");
                    invalidate();
                }
            });
            ValueAnimator rotateDel = ValueAnimator.ofInt(360, 0);
            rotateDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //透明度 旋转 位移
                    mAnimRotate = (int) animation.getAnimatedValue();
                    Log.d("TAG", "mAnimRotate() called with: animation = [" + mAnimRotate + "]");
                    invalidate();
                }
            });

            ValueAnimator animDel = ValueAnimator.ofFloat(1, 0);
            animDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mAnimFraction = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animatorSet.playTogether(translateDel, alphaDel, rotateDel, animDel);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                }
            });
            animatorSet.setDuration(350);
            animatorSet.start();

        } else {
            mAnimRotate = 0;
            mAnimAlpha = 255;
            mAnimOffset = 0;
            invalidate();
        }
    }

    private void onCountDelListener() {
        if (mCount == 0) {
            //动画
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator translateDel = ValueAnimator.ofFloat(0, mRadius * 2 + mGap * 2 + mTextPaint.measureText(mCount + ""));
            translateDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //透明度 旋转 位移
                    mAnimOffset = (float) animation.getAnimatedValue();
                    Log.d("TAG", "mAnimOffset() called with: animation = [" + mAnimOffset + "]");
                    invalidate();
                }
            });
            ValueAnimator alphaDel = ValueAnimator.ofInt(255, 0);
            alphaDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //透明度 旋转 位移
                    mAnimAlpha = (int) animation.getAnimatedValue();
                    Log.d("TAG", "mAnimAlpha() called with: animation = [" + mAnimAlpha + "]");
                    invalidate();
                }
            });
            ValueAnimator rotateDel = ValueAnimator.ofInt(0, 360);
            rotateDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //透明度 旋转 位移
                    mAnimRotate = (int) animation.getAnimatedValue();
                    Log.d("TAG", "mAnimRotate() called with: animation = [" + mAnimRotate + "]");
                    invalidate();
                }
            });

            ValueAnimator animDel = ValueAnimator.ofFloat(0, 1);
            animDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mAnimFraction = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animatorSet.playTogether(translateDel, alphaDel, rotateDel, animDel);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                }
            });
            animatorSet.setDuration(350);
            animatorSet.start();

        } else {
            mAnimRotate = 0;
            mAnimAlpha = 255;
            mAnimOffset = 0;
            invalidate();
        }
    }


}
