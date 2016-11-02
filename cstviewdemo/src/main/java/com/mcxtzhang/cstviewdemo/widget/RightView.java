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

public class RightView extends View {
    //画笔  
    private Paint mPaint;
    //隐形的路径  
    private Path mPath;
    //绘制用户看到的路径  
    private Path mSecondPath;  
    //路径测量  
    private PathMeasure mPathMeasure;
    //宽度和高度  
    private float width;  
    private float height;  
    //获取当前线段的位置 pos[0]为x轴　pos[1]为y轴  
    private float[] pos = new float[2];  
  
  
    public RightView(Context context, AttributeSet attrs) {
        super(context, attrs);  
        mPaint = new Paint();  
        mPaint.setStrokeWidth(10);  
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);  
        mPaint.setAntiAlias(true);  
        mPaint.setDither(true);  
        mPaint.setStrokeCap(Paint.Cap.ROUND);  
        mPaint.setStrokeJoin(Paint.Join.ROUND);
  
        mPath = new Path();  
        mPathMeasure = new PathMeasure();  
  
        mSecondPath = new Path();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
    }  
  
    @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
        width = getMeasuredWidth();  
        height = getMeasuredHeight();  
    }  
  
  
    @Override  
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);  
        mPath.moveTo(width / 2, height / 2);  
        mPath.rLineTo(40, 40);  
        mPath.rLineTo(90, -60);
  
        mPaint.setColor(Color.RED);  
        //mSecondPath.lineTo(pos[0], pos[1]);
        canvas.drawPath(mSecondPath, mPaint);  
    }  
  
  
    public void startAnim() {  
        mSecondPath.reset();  
        mPathMeasure.setPath(mPath, false);  
        mSecondPath.moveTo(width / 2, height / 2);  
        final ValueAnimator anim = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        anim.setDuration(500);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {  
            @Override  
            public void onAnimationUpdate(ValueAnimator animation) {  
                float value = (float) animation.getAnimatedValue();  
                //获取位置  
                //mPathMeasure.getPosTan(value, pos, null);
                mPathMeasure.getSegment(0,value,mSecondPath,true);
                postInvalidate();  
            }  
        });  
        anim.start();  
    }  
  
}  