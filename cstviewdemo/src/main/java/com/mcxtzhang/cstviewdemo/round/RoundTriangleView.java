package com.mcxtzhang.cstviewdemo.round;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangxutong on 2018/8/2.
 */

public class RoundTriangleView extends View {
    public RoundTriangleView(Context context) {
        super(context);
    }

    public RoundTriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundTriangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String COLOR_BG = "#66222222";

    private static final String COLOR_BORDER = "#4FFFFFFF";

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint2.setColor(Color.parseColor(COLOR_BG));
        paint2.setStyle(Paint.Style.FILL);
        paint2.setPathEffect(new CornerPathEffect(35));
        Path path = new Path();
        int lineWidth = 2;
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(400, 800);
        path.lineTo(80, 800);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint2);


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.parseColor(COLOR_BORDER));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setPathEffect(new CornerPathEffect(35));

        canvas.drawLine(40, 40, 40, 500, paint);

        // 绘制这个三角形,你可以绘制任意多边形
        path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(400, 800);
        path.lineTo(80, 800);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);
        //canvas.round//


    }
}
