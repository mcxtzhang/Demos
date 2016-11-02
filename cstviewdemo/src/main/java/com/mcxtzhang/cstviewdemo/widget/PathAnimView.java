package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.cstviewdemo.outpututils.AnimUtils;
import com.mcxtzhang.cstviewdemo.widget.res.StoreHousePath;

import java.util.ArrayList;

/**
 * 介绍：一个路径动画的View
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public class PathAnimView extends View {
    private Path mPath;
    private Path mDstPath;
    private Paint mPaint;

    public PathAnimView(Context context) {
        this(context, null);
    }

    public PathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
        ArrayList<float[]> path = StoreHousePath.getPath("AnLaiYe");
        for (int i = 0; i < path.size(); i++) {
            float[] floats = path.get(i);
            mPath.moveTo(floats[0], floats[1]);
            mPath.lineTo(floats[2], floats[3]);
        }

        mDstPath = new Path();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.startAnim(PathAnimView.this, mPath, mDstPath);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(20, 20);
        setBackgroundColor(Color.BLACK);
        mPaint.setColor(Color.GRAY);
        canvas.drawPath(mPath, mPaint);


        mPaint.setColor(Color.WHITE);
        canvas.drawPath(mDstPath, mPaint);
    }
}
