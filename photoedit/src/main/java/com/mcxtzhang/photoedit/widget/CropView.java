package com.mcxtzhang.photoedit.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by zhangxutong on 2019/3/13.
 */

public class CropView extends View {

    private int mStartX, mStartY;
    private int mCropWidth, mCropHeight;

    private Paint mCropLinePaint;
    private int mCropLineWidth;

    private Paint mCropArrowPaint;

    public CropView(Context context) {
        this(context, null);
    }

    public CropView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCropLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCropLinePaint.setColor(Color.WHITE);
        mCropLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        mCropLinePaint.setStrokeWidth(mCropLineWidth);
        mCropLinePaint.setStyle(Paint.Style.STROKE);
    }

    public int getStartX() {
        return mStartX;
    }

    public int getStartY() {
        return mStartY;
    }

    public int getCropWidth() {
        return mCropWidth;
    }

    public int getCropHeight() {
        return mCropHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mStartX = 100;
        mStartY = 100;
        mCropWidth = 500;
        mCropHeight = 500;
        canvas.drawRect(mStartX, mStartY, mStartX + mCropWidth, mStartY + mCropHeight, mCropLinePaint);
    }
}
