package com.mcxtzhang.photoedit.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhangxutong on 2019/3/13.
 */

public class CropDragView extends View {

    private static final String TAG = CropDragView.class.getSimpleName();

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

    private int mWidth, mHeight;
    private float[] mFloats = new float[9];
    private int mTouchDeviationThreshold;
    private int mCropMinLength;


    private int mStartX, mStartY;
    private int mCropWidth, mCropHeight;
    private Rect mCropRect;

    private Paint mCropLinePaint;
    private int mCropLineWidth;

    private Paint mCropCornerPaint;
    private int mCropCornerLineWidth;
    private int mCropCornerLineLength;

    private Paint mBgPaint;


    /**
     * 外部通信变量
     */
    private CropImageView mCropImageView;


    public CropDragView(Context context) {
        this(context, null);
    }

    public CropDragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropDragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        mTouchDeviationThreshold = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 21, displayMetrics);
        mCropMinLength = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, displayMetrics);

        mCropRect = new Rect();

        mCropLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, displayMetrics);
        mCropLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCropLinePaint.setColor(Color.WHITE);
        mCropLinePaint.setStrokeWidth(mCropLineWidth);
        mCropLinePaint.setStyle(Paint.Style.STROKE);


        mCropCornerLineLength = mTouchDeviationThreshold;
        mCropCornerLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, displayMetrics);
        mCropCornerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCropCornerPaint.setColor(Color.WHITE);
        mCropCornerPaint.setStrokeWidth(mCropCornerLineWidth);
        mCropCornerPaint.setStyle(Paint.Style.FILL);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.parseColor("#66000000"));
    }

    public CropDragView bindCropImageView(CropImageView cropImageView) {
        mCropImageView = cropImageView;
        updateCropAreaPosition();
        return this;
    }

    public void updateCropAreaPosition() {
        if (null == mCropImageView) {
            return;
        }
        Matrix imageMatrix = mCropImageView.getImageMatrix();
        imageMatrix.getValues(mFloats);

        //init
        if (mCropWidth == 0 && mCropHeight == 0) {
            if (mFloats[Matrix.MTRANS_X] <= 0) {
                mStartX = 0;
            } else {
                mStartX = (int) mFloats[Matrix.MTRANS_X];
            }
            if (mFloats[Matrix.MTRANS_Y] <= 0) {
                mStartY = 0;
            } else {
                mStartY = (int) mFloats[Matrix.MTRANS_Y];
            }
            mCropWidth = mWidth - mStartX - mStartX;
            mCropHeight = mHeight - mStartY - mStartY;
        } else {

        }
        invalidate();


    }

    public CropDragView setStartX(int startX) {
        mStartX = startX;
        return this;
    }

    public CropDragView setStartY(int startY) {
        mStartY = startY;
        return this;
    }

    public CropDragView setCropWidth(int cropWidth) {
        mCropWidth = cropWidth;
        return this;
    }

    public CropDragView setCropHeight(int cropHeight) {
        mCropHeight = cropHeight;
        return this;
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
        if (mCropWidth == 0 || mCropHeight == 0) {
            return;
        }
        //border
        mCropRect.set(mStartX, mStartY, mStartX + mCropWidth, mStartY + mCropHeight);
        canvas.drawRect(mCropRect, mCropLinePaint);

        // 4 lines
        float horizontalStep = mCropWidth * 1.0f / 3;
        for (float x = mStartX + horizontalStep; x <= mStartX + horizontalStep * 2; x = x + horizontalStep) {
            canvas.drawLine(x, mStartY, x, mStartY + mCropHeight, mCropLinePaint);
        }
        float verticalStep = mCropHeight * 1.0f / 3;
        for (float y = mStartY + verticalStep; y <= mStartY + verticalStep * 2; y = y + verticalStep) {
            canvas.drawLine(mStartX, y, mStartX + mCropWidth, y, mCropLinePaint);
        }

        //4 corners
        int offset = (mCropCornerLineWidth - mCropLineWidth) / 2;
        //lt
        canvas.drawLine(mStartX + offset, mStartY + offset, mStartX + mCropCornerLineLength, mStartY + offset, mCropCornerPaint);
        canvas.drawLine(mStartX + offset, mStartY + offset, mStartX + offset, mStartY + mCropCornerLineLength, mCropCornerPaint);
        //rt
        canvas.drawLine(mStartX + mCropWidth - offset, mStartY + offset, mStartX + mCropWidth - mCropCornerLineLength, mStartY + offset, mCropCornerPaint);
        canvas.drawLine(mStartX + mCropWidth - offset, mStartY + offset, mStartX + mCropWidth - offset, mStartY + mCropCornerLineLength, mCropCornerPaint);
        //rb
        canvas.drawLine(mStartX + mCropWidth - offset, mStartY + mCropHeight - offset, mStartX + mCropWidth - mCropCornerLineLength, mStartY + mCropHeight - offset, mCropCornerPaint);
        canvas.drawLine(mStartX + mCropWidth - offset, mStartY + mCropHeight - offset, mStartX + mCropWidth - offset, mStartY + mCropHeight - mCropCornerLineLength, mCropCornerPaint);
        //lb
        canvas.drawLine(mStartX + offset, mStartY + mCropHeight - offset, mStartX + mCropCornerLineLength, mStartY + mCropHeight - offset, mCropCornerPaint);
        canvas.drawLine(mStartX + offset, mStartY + mCropHeight - offset, mStartX + offset, mStartY + mCropHeight - mCropCornerLineLength, mCropCornerPaint);

        //shadow
        int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0, 0, mWidth, mHeight, mBgPaint);
        mBgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawRect(mCropRect, mBgPaint);
        mBgPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }

    private Point mLastDownPoint = new Point();
    private int mDragMode;
    private Runnable mUpdateUIRunnable = new Runnable() {
        @Override
        public void run() {
            if (null != mCropImageView) {
                mCropImageView.updateShowInCenter(mStartX, mStartY, mCropWidth, mCropHeight);
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        removeCallbacks(mUpdateUIRunnable);
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + ",    " + mTouchDeviationThreshold + ",,, " + mDragMode);
        if (event.getPointerId(0) > 0) {
            //多指
            return super.onTouchEvent(event);
        }
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
                int cropLimitLength;
                switch (mDragMode) {
                    case MODE_CORNER_LT:
                        //拖拽左上角，改变起点 和 宽高
                        mStartX += moveX;
                        mCropWidth -= moveX;
                        mStartY += moveY;
                        mCropHeight -= moveY;

                        checkStartXInDrag();
                        checkStartYInDrag();
                        break;
                    case MODE_CORNER_RT:
                        //拖拽右上角，水平位移改变宽度,竖直位移改变起点Y和高度
                        mCropWidth += moveX;
                        mStartY += moveY;
                        mCropHeight -= moveY;

                        checkWidthInDrag();
                        checkStartYInDrag();

                        break;
                    case MODE_CORNER_RB:
                        //拖拽右下角，水平位移改变宽度，竖直位移改变高度
                        mCropWidth += moveX;
                        mCropHeight += moveY;

                        checkWidthInDrag();
                        checkHeightInDrag();
                        break;
                    case MODE_CORNER_LB:
                        //拖拽左下角，水平位移改变起点X和宽度，竖直位移改变高度
                        mStartX += moveX;
                        mCropWidth -= moveX;
                        mCropHeight += moveY;

                        checkStartXInDrag();
                        checkHeightInDrag();
                        break;

                    case MODE_EDGE_L:
                        //拖拽左边，水平位移，改变起点和宽度
                        mStartX += moveX;
                        mCropWidth -= moveX;

                        checkStartXInDrag();
                        break;
                    case MODE_EDGE_T:
                        //拖拽上边，竖直位移，改变起点和高度
                        mStartY += moveY;
                        mCropHeight -= moveY;

                        checkStartYInDrag();
                        break;
                    case MODE_EDGE_R:
                        //拖拽右边，水平位移，改变宽度
                        mCropWidth += moveX;

                        checkWidthInDrag();
                        break;
                    case MODE_EDGE_B:
                        //拖拽底边，述职位移，改变高度
                        mCropHeight += moveY;

                        checkHeightInDrag();
                        break;
                }
                //边界处理 limit max
                checkBorderInDrag();

                invalidate();
                mLastDownPoint.set(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDragMode = 0;
                postDelayed(mUpdateUIRunnable, 1500);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void checkBorderInDrag() {
        if (mStartX < 0) {
            mCropWidth += mStartX;
            mStartX = 0;
        }
        if (mStartY < 0) {
            mCropHeight += mStartY;
            mStartY = 0;
        }
        if (mStartX + mCropWidth > mWidth) {
            mCropWidth = mWidth - mStartX;
        }
        if (mStartY + mCropHeight > mHeight) {
            mCropHeight = mHeight - mStartY;
        }
    }

    private void checkHeightInDrag() {
        if (mCropHeight < mCropMinLength) {
            mCropHeight = mCropMinLength;
        }
    }

    private void checkWidthInDrag() {
        if (mCropWidth < mCropMinLength) {
            mCropWidth = mCropMinLength;
        }
    }

    private void checkStartYInDrag() {
        int cropLimitLength;
        cropLimitLength = mCropHeight - mCropMinLength;
        if (cropLimitLength < 0) {
            mCropHeight -= cropLimitLength;
            mStartY += cropLimitLength;
        }
    }

    private void checkStartXInDrag() {
        int cropLimitLength;
        cropLimitLength = mCropWidth - mCropMinLength;
        if (cropLimitLength < 0) {
            mCropWidth -= cropLimitLength;
            mStartX += cropLimitLength;
        }
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


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
    }
}
