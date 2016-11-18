package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

public class EmbossMaskFilterView extends View {
    private static final int H_COUNT = 2, V_COUNT = 4;// 水平和垂直切割数  
    private Paint mPaint;// 画笔
    private PointF[] mPointFs;// 存储各个巧克力坐上坐标的点
  
    private int width, height;// 单个巧克力宽高  
    private float coorY;// 单个巧克力坐上Y轴坐标值  
  
    public EmbossMaskFilterView(Context context) {
        this(context, null);  
    }  
  
    public EmbossMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);  
        // 不使用硬件加速  
        setLayerType(LAYER_TYPE_SOFTWARE, null);  
  
        // 初始化画笔  
        initPaint();  
  
        // 计算参数  
        cal(context);  
    }  
  
    /** 
     * 初始化画笔 
     */  
    private void initPaint() {  
        // 实例化画笔  
        mPaint = new Paint();  
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);  
        mPaint.setStyle(Paint.Style.FILL);  
        mPaint.setColor(0xFF603811);  
  
        // 设置画笔遮罩滤镜  
        mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 50, 50, 1F }, 0.1F, 100F, 20F));
    }  
  
    /** 
     * 计算参数 
     */  
    private void cal(Context context) {  

        width =  getResources().getDisplayMetrics().widthPixels;
        height =  getResources().getDisplayMetrics().heightPixels;
  
        int count = V_COUNT * H_COUNT;  
  
        mPointFs = new PointF[count];  
        for (int i = 0; i < count; i++) {  
            if (i % 2 == 0) {  
                coorY = i * height / 2F;  
                mPointFs[i] = new PointF(0, coorY);  
            } else {  
                mPointFs[i] = new PointF(width, coorY);  
            }  
        }  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);  
        canvas.drawColor(Color.GRAY);
  
        // 画矩形  
        for (int i = 0; i < V_COUNT * H_COUNT; i++) {  
            canvas.drawRect(mPointFs[i].x, mPointFs[i].y, mPointFs[i].x + width, mPointFs[i].y + height, mPaint);  
        }  
    }  
}  