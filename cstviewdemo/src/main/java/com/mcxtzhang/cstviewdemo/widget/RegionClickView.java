package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class RegionClickView extends View {
    Paint mPaint;
    Region globalRegion;
    Region circleRegion1;
    Region circleRegion2;
    Path circlePath1;
    Path circlePath2;

    public RegionClickView(Context context) {
        super(context);
    }

    public RegionClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);

        // 用Path创建两个圆
        circlePath1 = new Path();
        circlePath2 = new Path();
        circlePath1.addCircle(100, 100, 100, Path.Direction.CW);
        circlePath2.addCircle(200, 200, 100, Path.Direction.CW);

        // 创建 Region
        circleRegion1 = new Region();
        circleRegion2 = new Region();
    }

    public RegionClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 将剪裁边界设置为视图大小
        globalRegion = new Region(0, 0, w, h);

        // ▼将 Path 添加到 Region 中
        circleRegion1.setPath(circlePath1, globalRegion);
        circleRegion2.setPath(circlePath2, globalRegion);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                // ▼点击区域判断
                if (circleRegion1.contains(x, y)) {
                    Toast.makeText(this.getContext(), "圆1被点击", Toast.LENGTH_SHORT).show();
                }
                if (circleRegion2.contains(x, y)) {
                    Toast.makeText(this.getContext(), "圆2被点击", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // ▼注意此处将全局变量转化为局部变量，方便 GC 回收 Canvas
        Path circle1 = circlePath1;
        Path circle2 = circlePath2;

        // 绘制两个圆
        canvas.drawPath(circle1, mPaint);
        canvas.drawPath(circle2, mPaint);
    }
}