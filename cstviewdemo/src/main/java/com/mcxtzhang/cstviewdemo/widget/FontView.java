package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FontView extends View {
    private static final String TEXT = "ap爱哥ξτβбпшㄎㄊěǔぬも┰┠№＠↓";
    private Paint mPaint, linePaint;// 文本的画笔和中心线的画笔  ;// 画笔
    private Paint.FontMetrics mFontMetrics;// 文本测量对象

    public FontView(Context context) {
        this(context, null);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化画笔  
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔  
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.BLACK);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);
        linePaint.setColor(Color.RED);


        mFontMetrics = mPaint.getFontMetrics();

        Log.d("Aige", "ascent：" + mFontMetrics.ascent);
        Log.d("Aige", "top：" + mFontMetrics.top);
        Log.d("Aige", "leading：" + mFontMetrics.leading);
        Log.d("Aige", "descent：" + mFontMetrics.descent);
        Log.d("Aige", "bottom：" + mFontMetrics.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int midX = (int) (getWidth() / 2 - mPaint.measureText(TEXT) / 2);
        int midY = getHeight();


        canvas.drawText(TEXT, 0, Math.abs(mFontMetrics.ascent), mPaint);

        // 为了便于理解我们在画布中心处绘制一条中线
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, linePaint);


    }
} 