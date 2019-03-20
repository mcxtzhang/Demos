package com.mcxtzhang.photoedit.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by zhangxutong on 2019/3/13.
 */

public class CropDragView extends View {

    private static final String TAG = CropDragView.class.getSimpleName();

    public static final int CROP_RATE_FREE = 1;
    public static final int CROP_RATE_11 = 2;
    public static final int CROP_RATE_34 = 3;
    public static final int CROP_RATE_43 = 4;

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
    private float mCropMinBaseLength;
    private float mCropMinWidth, mCropMinHeight;
    //裁剪单边最小像素（不可小于100px）
    private int mPhotoMinBasePixels = 100;
    private float mPhotoMinWidthPixels, mPhotoMinHeightPixels;

    private float mStartX, mStartY;
    private float mCropWidth, mCropHeight;
    private RectF mCropRect;

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

    private int mCropRate;


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
        mCropMinBaseLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, displayMetrics);

        mCropRect = new RectF();

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
        if (mFloats[Matrix.MTRANS_X] <= 0) {
            mStartX = 0;
        } else {
            mStartX = mFloats[Matrix.MTRANS_X];
        }
        if (mFloats[Matrix.MTRANS_Y] <= 0) {
            mStartY = 0;
        } else {
            mStartY = mFloats[Matrix.MTRANS_Y];
        }
        mCropWidth = mWidth - mStartX - mStartX;
        mCropHeight = mHeight - mStartY - mStartY;
        /*if (mCropWidth == 0 && mCropHeight == 0) {
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

        }*/
        invalidate();
        Log.d(TAG, "updateCropAreaPosition，裁剪区域像素：" + mCropWidth / mCropImageView.getScale() + "," + mCropHeight / mCropImageView.getScale());


    }

    public CropDragView setStartX(float startX) {
        mStartX = startX;
        return this;
    }

    public CropDragView setStartY(float startY) {
        mStartY = startY;
        return this;
    }

    public CropDragView setCropWidth(float cropWidth) {
        mCropWidth = cropWidth;
        return this;
    }

    public CropDragView setCropHeight(float cropHeight) {
        mCropHeight = cropHeight;
        return this;
    }

    public float getStartX() {
        return mStartX;
    }

    public float getStartY() {
        return mStartY;
    }

    public float getCropWidth() {
        return mCropWidth;
    }

    public float getCropHeight() {
        return mCropHeight;
    }

    public int getCropRate() {
        return mCropRate;
    }

    public CropDragView setCropRate(int cropRate) {
        mCropRate = cropRate;

        if (null == mCropImageView) {
            return this;
        }
        Matrix imageMatrix = mCropImageView.getImageMatrix();
        imageMatrix.getValues(mFloats);

        switch (mCropRate) {
            case CROP_RATE_FREE:
                mCropMinWidth = mCropMinBaseLength;
                mCropMinHeight = mCropMinBaseLength;
                mPhotoMinWidthPixels = mPhotoMinBasePixels;
                mPhotoMinHeightPixels = mPhotoMinBasePixels;
                break;
            case CROP_RATE_11:
                mCropMinWidth = mCropMinBaseLength;
                mCropMinHeight = mCropMinBaseLength;
                mPhotoMinWidthPixels = mPhotoMinBasePixels;
                mPhotoMinHeightPixels = mPhotoMinBasePixels;

                //以图片短边为基准边长
                float edge;
                if (mFloats[Matrix.MTRANS_X] > 0) {
                    edge = (mWidth - mFloats[Matrix.MTRANS_X] * 2);
                } else if (mFloats[Matrix.MTRANS_Y] > 0) {
                    edge = (mHeight - mFloats[Matrix.MTRANS_Y] * 2);
                } else {
                    edge = Math.min(mWidth, mHeight);
                }
                mCropWidth = edge;
                mCropHeight = edge;
                break;
            case CROP_RATE_34:
                mCropMinWidth = mCropMinBaseLength;
                mCropMinHeight = (mCropMinBaseLength * 4.0f / 3);
                mPhotoMinWidthPixels = mPhotoMinBasePixels;
                mPhotoMinHeightPixels = (mPhotoMinBasePixels * 4.0f / 3);

                //特殊图以短边为基准
                if (mFloats[Matrix.MTRANS_X] > 0) {
                    mCropWidth = (mWidth - mFloats[Matrix.MTRANS_X] * 2);
                    mCropHeight = (mCropWidth * 4.0f / 3);
                } else if (mFloats[Matrix.MTRANS_Y] > 0) {
                    mCropHeight = (mHeight - mFloats[Matrix.MTRANS_Y] * 2);
                    mCropWidth = (mCropHeight * 3.0f / 4);
                } else {
                    mCropHeight = mHeight;
                    mCropWidth = (mCropHeight * 3.0f / 4);
                }
                break;
            case CROP_RATE_43:
                mCropMinWidth = (mCropMinBaseLength * 4.0f / 3);
                mCropMinHeight = mCropMinBaseLength;
                mPhotoMinWidthPixels = (mPhotoMinBasePixels * 4.0f / 3);
                mPhotoMinHeightPixels = mPhotoMinBasePixels;

                if (mFloats[Matrix.MTRANS_X] > 0) {
                    mCropWidth = (mWidth - mFloats[Matrix.MTRANS_X] * 2);
                    mCropHeight = (mCropWidth * 3.0f / 4);
                } else if (mFloats[Matrix.MTRANS_Y] > 0) {
                    mCropHeight = (mHeight - mFloats[Matrix.MTRANS_Y] * 2);
                    mCropWidth = (mCropHeight * 4.0f / 3);
                } else {
                    mCropHeight = (mHeight - mFloats[Matrix.MTRANS_Y] * 2);
                    mCropWidth = mWidth;
                }
                break;
        }

        mStartX = (mWidth - mCropWidth) / 2;
        mStartY = (mHeight - mCropHeight) / 2;

        invalidate();
        return this;
    }

    public float getCropMinWidth() {
        return mCropMinWidth;
    }

    public float getCropMinHeight() {
        return mCropMinHeight;
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
        float horizontalStep = (mCropWidth / 3);
        for (float x = mStartX + horizontalStep; x <= mStartX + horizontalStep + horizontalStep; x = x + horizontalStep) {
            canvas.drawLine(x, mStartY, x, mStartY + mCropHeight, mCropLinePaint);
        }
        float verticalStep = (mCropHeight / 3);
        for (float y = mStartY + verticalStep; y <= mStartY + verticalStep + verticalStep; y = y + verticalStep) {
            canvas.drawLine(mStartX, y, mStartX + mCropWidth, y, mCropLinePaint);
        }

        //4 corners
        float offset = (mCropCornerLineWidth - mCropLineWidth) / 2;
        float cropCornerLineXLength = mCropCornerLineLength > mCropWidth ? mCropWidth : mCropCornerLineLength;
        float cropCornerLineYLength = mCropCornerLineLength > mCropHeight ? mCropHeight : mCropCornerLineLength;
        //lt
        canvas.drawLine(mStartX + offset, mStartY + offset, mStartX + cropCornerLineXLength, mStartY + offset, mCropCornerPaint);
        canvas.drawLine(mStartX + offset, mStartY + offset, mStartX + offset, mStartY + cropCornerLineYLength, mCropCornerPaint);
        //rt
        canvas.drawLine(mStartX + mCropWidth - offset, mStartY + offset, mStartX + mCropWidth - cropCornerLineXLength, mStartY + offset, mCropCornerPaint);
        canvas.drawLine(mStartX + mCropWidth - offset, mStartY + offset, mStartX + mCropWidth - offset, mStartY + cropCornerLineYLength, mCropCornerPaint);
        //rb
        canvas.drawLine(mStartX + mCropWidth - offset, mStartY + mCropHeight - offset, mStartX + mCropWidth - cropCornerLineXLength, mStartY + mCropHeight - offset, mCropCornerPaint);
        canvas.drawLine(mStartX + mCropWidth - offset, mStartY + mCropHeight - offset, mStartX + mCropWidth - offset, mStartY + mCropHeight - cropCornerLineYLength, mCropCornerPaint);
        //lb
        canvas.drawLine(mStartX + offset, mStartY + mCropHeight - offset, mStartX + cropCornerLineXLength, mStartY + mCropHeight - offset, mCropCornerPaint);
        canvas.drawLine(mStartX + offset, mStartY + mCropHeight - offset, mStartX + offset, mStartY + mCropHeight - cropCornerLineYLength, mCropCornerPaint);

        //shadow
        int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0, 0, mWidth, mHeight, mBgPaint);
        mBgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawRect(mCropRect, mBgPaint);
        mBgPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }

    private PointF mLastDownPoint = new PointF();
    private int mDragMode;

    private class UpdateUiRunnable implements Runnable {
        private int dragMode;

        public UpdateUiRunnable setDragMode(int dragMode) {
            this.dragMode = dragMode;
            return this;
        }

        @Override
        public void run() {
            if (null != mCropImageView) {
                float scale = mCropImageView.getScale();
                float widthPixels = mCropWidth / scale;
                float xOffsetPixels = widthPixels - mPhotoMinWidthPixels;
                float heightPixels = mCropHeight / scale;
                float yOffsetPixels = heightPixels - mPhotoMinHeightPixels;

                Log.d(TAG, "松手后 调整前，裁剪区域像素：" + widthPixels + "," + heightPixels);
                if (xOffsetPixels < 0) {
                    // TODO: 2019/3/19  
                    Toast.makeText(getContext(), "图片尺寸过小，无法进行裁剪", Toast.LENGTH_SHORT).show();
                    float photoMinWidth = (mPhotoMinWidthPixels * scale);
                    switch (dragMode) {
                        case MODE_CORNER_LT:
                            //拖拽左上角，改变起点 和 宽高
                            //终点不变，所以从终点逆推
                            mStartX = mStartX + mCropWidth - photoMinWidth;
                            mCropWidth = photoMinWidth;
                            break;
                        case MODE_CORNER_RT:
                            //拖拽右上角，水平位移改变宽度,竖直位移改变起点Y和高度
                            mCropWidth = photoMinWidth;
                            break;
                        case MODE_CORNER_RB:
                            //拖拽右下角，水平位移改变宽度，竖直位移改变高度
                            mCropWidth = photoMinWidth;
                            break;
                        case MODE_CORNER_LB:
                            //拖拽左下角，水平位移改变起点X和宽度，竖直位移改变高度
                            mStartX = mStartX + mCropWidth - photoMinWidth;
                            mCropWidth = photoMinWidth;
                            break;
                        case MODE_EDGE_L:
                            //拖拽左边，水平位移，改变起点和宽度
                            mStartX = mStartX + mCropWidth - photoMinWidth;
                            mCropWidth = photoMinWidth;
                            break;
                        case MODE_EDGE_T:
                            //拖拽上边，竖直位移，改变起点和高度
                            break;
                        case MODE_EDGE_R:
                            //拖拽右边，水平位移，改变宽度
                            mCropWidth = photoMinWidth;
                            break;
                        case MODE_EDGE_B:
                            //拖拽底边，述职位移，改变高度
                            break;
                    }
                }
                if (yOffsetPixels < 0) {
                    // TODO: 2019/3/19
                    Toast.makeText(getContext(), "图片尺寸过小，无法进行裁剪", Toast.LENGTH_SHORT).show();
                    float photoMinHeight = (mPhotoMinHeightPixels * scale);
                    switch (dragMode) {
                        case MODE_CORNER_LT:
                            //拖拽左上角，改变起点 和 宽高
                            mStartY = mStartY + mCropHeight - photoMinHeight;
                            mCropHeight = photoMinHeight;
                            break;
                        case MODE_CORNER_RT:
                            //拖拽右上角，水平位移改变宽度,竖直位移改变起点Y和高度
                            mStartY = mStartY + mCropHeight - photoMinHeight;
                            mCropHeight = photoMinHeight;
                            break;
                        case MODE_CORNER_RB:
                            //拖拽右下角，水平位移改变宽度，竖直位移改变高度
                            mCropHeight = photoMinHeight;
                            break;
                        case MODE_CORNER_LB:
                            //拖拽左下角，水平位移改变起点X和宽度，竖直位移改变高度
                            mCropHeight = photoMinHeight;
                            break;
                        case MODE_EDGE_L:
                            //拖拽左边，水平位移，改变起点和宽度
                            break;
                        case MODE_EDGE_T:
                            //拖拽上边，竖直位移，改变起点和高度
                            mStartY = mStartY + mCropHeight - photoMinHeight;
                            mCropHeight = photoMinHeight;
                            break;
                        case MODE_EDGE_R:
                            //拖拽右边，水平位移，改变宽度
                            break;
                        case MODE_EDGE_B:
                            //拖拽底边，述职位移，改变高度
                            mCropHeight = photoMinHeight;
                            break;
                    }
                }

                mCropImageView.updateShowInCenter(mStartX, mStartY, mCropWidth, mCropHeight);

                Log.d(TAG, "松手后，裁剪区域像素：" + mCropWidth / mCropImageView.getScale() + "," + mCropHeight / mCropImageView.getScale());

            }
        }
    }

    private UpdateUiRunnable mUpdateUIRunnable = new UpdateUiRunnable();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        removeCallbacks(mUpdateUIRunnable);

        if (mCropImageView.checkTooLongWide()) {
            return false;
        }

        float eventX = event.getX();
        float eventY = event.getY();
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + ",    " + mTouchDeviationThreshold + ",,, " + mDragMode);
        if (event.getPointerId(0) > 0) {
            //多指
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastDownPoint.set(eventX, eventY);
                if (touchOnCorner(mCropRect, eventX, eventY)) {
                    return true;
                } else if (mCropRate == CROP_RATE_FREE && touchOnBorder(mCropRect, eventX, eventY)) {
                    //只有自由比例时，四个边才允许拖拽
                    return true;
                } else {
                }
            case MotionEvent.ACTION_MOVE:
                float moveX = eventX - mLastDownPoint.x;
                float moveY = eventY - mLastDownPoint.y;

                float absX = Math.abs(moveX);
                float absY = Math.abs(moveY);
                switch (mCropRate) {
                    case CROP_RATE_FREE:
                        break;
                    case CROP_RATE_11:
                        //观察美图秀秀，以较小移动距离为准
                        if (absX > absY) {
                            if (moveX > 0) {
                                moveX = absY;
                            } else {
                                moveX = -absY;
                            }
                        } else {
                            if (moveY > 0) {
                                moveY = absX;
                            } else {
                                moveY = -absX;
                            }
                        }
                        break;
                    case CROP_RATE_34:
                        if (absX > absY) {
                            if (moveX > 0) {
                                moveX = (absY * 3.0f / 4);
                            } else {
                                moveX = (-absY * 3.0f / 4);
                            }
                        } else {
                            if (moveY > 0) {
                                moveY = (absX * 4.0f / 3);
                            } else {
                                moveY = (-absX * 4.0f / 3);
                            }
                        }
                        break;
                    case CROP_RATE_43:
                        if (absX > absY) {
                            if (moveX > 0) {
                                moveX = (absY * 4.0f / 3);
                            } else {
                                moveX = (-absY * 4.0f / 3);
                            }
                        } else {
                            if (moveY > 0) {
                                moveY = (absX * 3.0f / 4);
                            } else {
                                moveY = (-absX * 3.0f / 4);
                            }
                        }
                        break;
                }

                int cropLimitLength;
                switch (mDragMode) {
                    case MODE_CORNER_LT:
                        if (mCropRate != CROP_RATE_FREE) {
                            if (moveX * moveY < 0) {
                                moveX = 0;
                                moveY = 0;
                            }
                        }
                        //拖拽左上角，改变起点 和 宽高
                        mStartX += moveX;
                        mCropWidth -= moveX;
                        mStartY += moveY;
                        mCropHeight -= moveY;

                        checkStartXInDrag();
                        checkStartYInDrag();
                        break;
                    case MODE_CORNER_RT:
                        if (mCropRate != CROP_RATE_FREE) {
                            if (moveX * moveY > 0) {
                                moveX = 0;
                                moveY = 0;
                            }
                        }
                        //拖拽右上角，水平位移改变宽度,竖直位移改变起点Y和高度
                        mCropWidth += moveX;
                        mStartY += moveY;
                        mCropHeight -= moveY;

                        checkWidthInDrag();
                        checkStartYInDrag();

                        break;
                    case MODE_CORNER_RB:
                        if (mCropRate != CROP_RATE_FREE) {
                            if (moveX * moveY < 0) {
                                moveX = 0;
                                moveY = 0;
                            }
                        }
                        //拖拽右下角，水平位移改变宽度，竖直位移改变高度
                        mCropWidth += moveX;
                        mCropHeight += moveY;

                        checkWidthInDrag();
                        checkHeightInDrag();
                        break;
                    case MODE_CORNER_LB:
                        if (mCropRate != CROP_RATE_FREE) {
                            if (moveX * moveY > 0) {
                                moveX = 0;
                                moveY = 0;
                            }
                        }
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
                postDelayed(mUpdateUIRunnable.setDragMode(mDragMode), 1500);
                mDragMode = 0;
                break;
        }
        return super.onTouchEvent(event);
    }

    private void checkBorderInDrag() {
        RectF matrixRectF = mCropImageView.getMatrixRectF();
        float leftLimit = matrixRectF.left < 0 ? 0 : matrixRectF.left;
        float topLimit = matrixRectF.top < 0 ? 0 : matrixRectF.top;
        float rightLimit = matrixRectF.right > mWidth ? mWidth : matrixRectF.right;
        float bottomLimit = matrixRectF.bottom > mHeight ? mHeight : matrixRectF.bottom;

        if (mStartX < leftLimit) {
            mCropWidth += (mStartX - leftLimit);
            mStartX = leftLimit;
        }
        if (mStartY < topLimit) {
            mCropHeight += (mStartY - topLimit);
            mStartY = topLimit;
        }
        if (mStartX + mCropWidth > rightLimit) {
            mCropWidth = rightLimit - mStartX;
        }
        if (mStartY + mCropHeight > bottomLimit) {
            mCropHeight = bottomLimit - mStartY;
        }
    }

    private void checkHeightInDrag() {
        if (mCropHeight < mCropMinHeight) {
            mCropHeight = mCropMinHeight;
        }
    }

    private void checkWidthInDrag() {
        if (mCropWidth < mCropMinWidth) {
            mCropWidth = mCropMinWidth;
        }
    }

    private void checkStartYInDrag() {
        float cropLimitLength;
        cropLimitLength = mCropHeight - mCropMinHeight;
        if (cropLimitLength < 0) {
            mCropHeight -= cropLimitLength;
            mStartY += cropLimitLength;
        }
    }

    private void checkStartXInDrag() {
        float cropLimitLength;
        cropLimitLength = mCropWidth - mCropMinWidth;
        if (cropLimitLength < 0) {
            mCropWidth -= cropLimitLength;
            mStartX += cropLimitLength;
        }
    }

    private boolean touchOnCorner(RectF rect, float x, float y) {
        PointF[] points = new PointF[4];
        points[0] = new PointF(rect.left, rect.top);
        points[1] = new PointF(rect.right, rect.top);
        points[2] = new PointF(rect.right, rect.bottom);
        points[3] = new PointF(rect.left, rect.bottom);

        for (int i = 0; i < points.length; i++) {
            if (Math.sqrt((points[i].x - x) * (points[i].x - x) + (points[i].y - y) * (points[i].y - y)) < mTouchDeviationThreshold) {
                mDragMode = MODE_CORNER[i];
                return true;
            }
        }
        return false;
    }

    private boolean touchOnBorder(RectF rect, float x, float y) {
        float x1 = rect.left - mTouchDeviationThreshold;
        float x2 = rect.right + mTouchDeviationThreshold;
        float x3 = rect.left + mTouchDeviationThreshold;
        float x4 = rect.right - mTouchDeviationThreshold;

        float y1 = rect.top - mTouchDeviationThreshold;
        float y2 = rect.bottom + mTouchDeviationThreshold;
        float y3 = rect.top + mTouchDeviationThreshold;
        float y4 = rect.bottom - mTouchDeviationThreshold;

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
