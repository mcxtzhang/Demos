package com.mcxtzhang.photoedit.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhangxutong on 2019/3/13.
 */

public class CropView extends View {

    private static final String TAG = CropView.class.getSimpleName();

    private static final int MODE_CORNER_LT = 1;
    private static final int MODE_CORNER_RT = 2;
    private static final int MODE_CORNER_RB = 3;
    private static final int MODE_CORNER_LB = 4;
    private static final int[] MODE_CORNER = new int[]{
            MODE_CORNER_LT,
            MODE_CORNER_RT,
            MODE_CORNER_RB,
            MODE_CORNER_LB};

    private static final int MODE_EDGE_L = 5;
    private static final int MODE_EDGE_T = 6;
    private static final int MODE_EDGE_R = 7;
    private static final int MODE_EDGE_B = 8;


    private int mStartX, mStartY;
    private int mCropWidth, mCropHeight;
    private Rect mCropRect;

    private Paint mCropLinePaint;
    private int mCropLineWidth;

    private Paint mCropCornerPaint;

    private int mTouchDeviationThreshold;

    public CropView(Context context) {
        this(context, null);
    }

    public CropView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCropRect = new Rect();

        mStartX = 300;
        mStartY = 300;
        mCropWidth = 500;
        mCropHeight = 500;

        mCropLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCropLinePaint.setColor(Color.WHITE);
        mCropLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        mCropLinePaint.setStrokeWidth(mCropLineWidth);
        mCropLinePaint.setStyle(Paint.Style.STROKE);


        mTouchDeviationThreshold = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
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
        mCropRect.set(mStartX, mStartY, mStartX + mCropWidth, mStartY + mCropHeight);
        canvas.drawRect(mCropRect, mCropLinePaint);
    }

    private Point mLastDownPoint = new Point();
    private int mDragMode;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + ",    " + mTouchDeviationThreshold + ",,, " + mDragMode);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastDownPoint.set(eventX, eventY);
                if (touchOnCorner(mCropRect, eventX, eventY)) {
                    Log.e("TAG", "摸在角上: ");
                    return true;
                } else if (touchOnBorder(mCropRect, eventX, eventY)) {
                    Log.e("TAG", "摸在边上: ");
                    return true;
                } else {
                    Log.e("TAG", "miss: ");
                }
            case MotionEvent.ACTION_MOVE:
                int moveX = eventX - mLastDownPoint.x;
                int moveY = eventY - mLastDownPoint.y;

                switch (mDragMode) {
                    case MODE_CORNER_LT:
                        //拖拽左上角，改变起点 和 宽高
                        mStartX += moveX;
                        mCropWidth -= moveX;
                        mStartY += moveY;
                        mCropHeight -= moveY;
                        break;
                    case MODE_CORNER_RT:
                        //拖拽右上角，水平位移改变宽度,竖直位移改变起点Y和高度
                        mCropWidth += moveX;
                        mStartY += moveY;
                        mCropHeight -= moveY;
                        break;
                    case MODE_CORNER_RB:
                        //拖拽右下角，水平位移改变宽度，竖直位移改变高度
                        mCropWidth += moveX;
                        mCropHeight += moveY;
                        break;
                    case MODE_CORNER_LB:
                        //拖拽左下角，水平位移改变起点X和宽度，竖直位移改变高度
                        mStartX += moveX;
                        mCropWidth -= moveX;
                        mCropHeight += moveY;
                        break;

                    case MODE_EDGE_L:
                        //拖拽左边，水平位移，改变起点和宽度
                        mStartX += moveX;
                        mCropWidth -= moveX;
                        break;
                    case MODE_EDGE_T:
                        //拖拽上边，竖直位移，改变起点和高度
                        mStartY += moveY;
                        mCropHeight -= moveY;
                        break;
                    case MODE_EDGE_R:
                        //拖拽右边，水平位移，改变宽度
                        mCropWidth += moveX;
                        break;
                    case MODE_EDGE_B:
                        //拖拽底边，述职位移，改变高度
                        mCropHeight += moveY;
                        break;
                }

                invalidate();
                mLastDownPoint.set(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDragMode = 0;
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean touchOnCorner(Rect rect, int x, int y) {
        Point[] points = new Point[4];
        points[0] = new Point(rect.left, rect.top);
        points[1] = new Point(rect.right, rect.top);
        points[2] = new Point(rect.right, rect.bottom);
        points[3] = new Point(rect.left, rect.bottom);

        for (int i = 0; i < points.length; i++) {
            if (Math.sqrt((points[i].x - x) * (points[i].x - x) + (points[i].y - y) * (points[i].y - y)) < mTouchDeviationThreshold) {
                mDragMode = MODE_CORNER[i];
                return true;
            }
        }
        return false;
    }

    private boolean touchOnBorder(Rect rect, int x, int y) {
        int x1 = rect.left - mTouchDeviationThreshold;
        int x2 = rect.right + mTouchDeviationThreshold;
        int x3 = rect.left + mTouchDeviationThreshold;
        int x4 = rect.right - mTouchDeviationThreshold;

        int y1 = rect.top - mTouchDeviationThreshold;
        int y2 = rect.bottom + mTouchDeviationThreshold;
        int y3 = rect.top + mTouchDeviationThreshold;
        int y4 = rect.bottom - mTouchDeviationThreshold;

        if (x >= x3 && x <= x4 && y >= y1 && y <= y3) {
            Log.d(TAG, "触摸上边，可竖直方向拖动");
            mDragMode = MODE_EDGE_T;
            return true;
        }
        if (x >= x3 && x <= x4 && y >= y4 && y <= y2) {
            Log.d(TAG, "触摸下边，可竖直方向拖动");
            mDragMode = MODE_EDGE_B;
            return true;
        }
        if (x >= x1 && x <= x3 && y >= y3 && y <= y4) {
            Log.d(TAG, "触摸左边，可水平方向拖动");
            mDragMode = MODE_EDGE_L;
            return true;
        }
        if (x >= x4 && x <= x2 && y >= y3 && y <= y4) {
            Log.d(TAG, "触摸右边，可水平方向拖动");
            mDragMode = MODE_EDGE_R;
            return true;
        }
        return false;
    }


}
