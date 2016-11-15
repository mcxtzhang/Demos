package com.mcxtzhang.cstviewdemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.cstviewdemo.R;

public class Oval extends View implements View.OnClickListener {

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private PathEffect mEffect;
    private float fraction = 0;
    private ValueAnimator mAnimator;

    public Oval(Context context) {
        super(context);
    }

    public Oval(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPath.reset();
        mPath.lineTo(0, 0);


        RectF oval = new RectF(100, 100, 600, 600);


        mPath.arcTo(oval, 90, 180);

        //我画一个圆
        //mPath.addRoundRect(200,200,600,600,50,50, Path.Direction.CCW);
/*
        mPath.moveTo(50,100);
        mPath.lineTo(getWidth(),100);*/

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

    }

    public Oval(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawPath(mPath, mPaint);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wangcong);
        // 将画布坐标系移动到画布中央
        canvas.translate(getWidth() / 2, getHeight() / 2);

// 指定图片绘制区域(左上角的四分之一)
        Rect src = new Rect(bitmap.getWidth() / 3, bitmap.getWidth() / 3, bitmap.getWidth(), bitmap.getHeight());

// 指定图片在屏幕上显示的区域
        Rect dst = new Rect(100, 100, 200, 400);

// 绘制图片
        canvas.drawBitmap(bitmap, src, dst, null);

    }

    @Override
    public void onClick(View view) {
        mAnimator.start();
    }
}