package com.mcxtzhang.cstviewdemo.xfermode;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.cstviewdemo.R;

/**
 * 介绍：测试XferModeView
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/24.
 */

public class XfermodeView extends View {
    private Paint mPaint;
    private Path mPath;
    private PorterDuffXfermode mPorterDuffXfermode;

    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.RED);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mPaint.setXfermode(mPorterDuffXfermode);

        mPath= new Path();
        mPath.addCircle(200,200,100, Path.Direction.CW);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wangcong), 0, 0, mPaint);
        canvas.drawPath(mPath, mPaint);
    }
}
