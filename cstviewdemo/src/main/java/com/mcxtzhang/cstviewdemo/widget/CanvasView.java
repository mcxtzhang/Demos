package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.cstviewdemo.R;

/**
 * 介绍：自定义View 主要练习Canvas Paint
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/1.
 */
public class CanvasView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setTextSize(30);
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wangcong);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundColor(Color.GREEN);
        mPaint.setColor(Color.YELLOW);

        canvas.save();
        canvas.rotate(90);

        canvas.drawRect(0, -getWidth(), getHeight(), 0, mPaint);

        canvas.drawBitmap(mBitmap,0,-mBitmap.getHeight(),mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawText("123456789", 50, -50, mPaint);

        canvas.restore();


        mPaint.setColor(Color.GREEN);
        canvas.drawOval(0,0,getRight()-getLeft(),getBottom()-getTop(),mPaint);

    }
}
