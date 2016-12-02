package com.mcxtzhang.cstviewdemo.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mcxtzhang.cstviewdemo.R;

public class BrickView extends View {
    private Paint mFillPaint, mStrokePaint;// 填充和描边的画笔
    private BitmapShader mBitmapShader;// Bitmap着色器

    private float posX, posY;// 触摸点的XY坐标  

    public BrickView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化画笔  
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {  
        /* 
         * 实例化描边画笔并设置参数 
         */
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStrokePaint.setColor(0xFF000000);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(5);

        // 实例化填充画笔  
        mFillPaint = new Paint();  
  
        /* 
         * 生成BitmapShader 
         */
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flower);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mFillPaint.setShader(mBitmapShader);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* 
         * 手指移动时获取触摸点坐标并刷新视图 
         */
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            posX = event.getRawX();
            posY = event.getRawY();

            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 设置画笔背景色  
        canvas.drawColor(Color.DKGRAY);
        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.flower);
        BitmapShader bs1 = new BitmapShader(b1, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Paint p1 = new Paint();;
        p1.setShader(bs1);

        canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), p1);
  
        /* 
         * 绘制圆和描边 
         */
        canvas.save();
        canvas.translate(posX,posY);
/*        float scale = 1.2f;
        canvas.scale(scale, scale);*/

        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.flower);
        BitmapShader bm2 = new BitmapShader(b2, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        // ▼ 获得当前矩阵的逆矩阵
        Matrix invertMatrix = new Matrix();
        canvas.getMatrix().invert(invertMatrix);
        bm2.setLocalMatrix(invertMatrix);

        Paint p2 = new Paint();
        p2.setShader(bm2);

        canvas.drawCircle(0,0,300,p2);
        canvas.drawCircle(0,0,300,mStrokePaint);
        canvas.restore();

/*


        float[] pts = {posX, posY};
        //invertMatrix.mapPoints(pts);
        canvas.drawCircle(pts[0]/scale, pts[1]/scale, 300, mFillPaint);
        canvas.drawCircle(pts[0]/scale, pts[1]/scale, 300, mStrokePaint);*/



    }
}  