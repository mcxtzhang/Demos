package com.mcxtzhang.photoedit.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

/**
 * Created by zhangxutong on 2019/3/13.
 */
public class ZoomImageView extends android.support.v7.widget.AppCompatImageView implements OnScaleGestureListener, ViewTreeObserver.OnGlobalLayoutListener

{
    private static final String TAG = ZoomImageView.class.getSimpleName();

    public static final float SCALE_MAX = 4.0f;
    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];

    private boolean once = true;

    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector = null;

    private final Matrix mImageMatrix = new Matrix();


    private int mTouchSlop;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        //this.setOnTouchListener(this);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null)
            return true;

        mImageMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
        setImageMatrix(mImageMatrix);
        /**
         * 缩放的范围控制
         */
//        if ((scale < SCALE_MAX && scaleFactor > 1.0f)
//                || (scale > initScale && scaleFactor < 1.0f)) {
//            /**
//             * 最大值最小值判断
//             */
//            if (scaleFactor * scale < initScale) {
//                scaleFactor = initScale / scale;
//            }
//            if (scaleFactor * scale > SCALE_MAX) {
//                scaleFactor = SCALE_MAX / scale;
//            }
//            /**
//             * 设置缩放比例
//             */
//            mImageMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2,
//                    getHeight() / 2);
//            setImageMatrix(mImageMatrix);
//        }
        return true;

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mImageMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }


    private int lastPointerCount;
    boolean isCanDrag;
    float mLastX, mLastY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0, y = 0;
        // 拿到触摸点的个数
        final int pointerCount = event.getPointerCount();
        // 得到多个触摸点的x与y均值
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;

        /**
         * 每当触摸点发生变化时，重置mLasX , mLastY
         */
        if (pointerCount != lastPointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }


        lastPointerCount = pointerCount;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE");
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag) {
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null) {
//                        isCheckLeftAndRight = isCheckTopAndBottom = true;
//                        // 如果宽度小于屏幕宽度，则禁止左右移动
//                        if (rectF.width() < getWidth()) {
//                            dx = 0;
//                            isCheckLeftAndRight = false;
//                        }
//                        // 如果高度小雨屏幕高度，则禁止上下移动
//                        if (rectF.height() < getHeight()) {
//                            dy = 0;
//                            isCheckTopAndBottom = false;
//                        }
                        mImageMatrix.postTranslate(dx, dy);
                        //checkMatrixBounds();
                        setImageMatrix(mImageMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "ACTION_UP");
                lastPointerCount = 0;
                break;
        }
        float[] floats = new float[9];
        mImageMatrix.getValues(floats);

//        Log.e(TAG, "MSCALE_X: " + floats[Matrix.MSCALE_X]
//                + "MSCALE_Y: " + floats[Matrix.MSCALE_Y]
//                + "MTRANS_X: " + floats[Matrix.MTRANS_X]
//                + "MTRANS_Y: " + floats[Matrix.MTRANS_Y]
//        );

        return true;

    }

    /**
     * 是否是推动行为
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy) {
        //return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
        return true;
    }


    /**
     * 获得当前的缩放比例
     *
     * @return
     */
    public final float getScale() {
        mImageMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }


    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding = 20;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding;
//
//    @Override
//    public void onGlobalLayout()
//    {
//        if (once)
//        {
//            Drawable d = getDrawable();
//            if (d == null)
//                return;
//            Log.e(TAG, d.getIntrinsicWidth() + " , " + d.getIntrinsicHeight());
//            // 计算padding的px
//            mHorizontalPadding = (int) TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding,
//                    getResources().getDisplayMetrics());
//            // 垂直方向的边距
//            mVerticalPadding = (getHeight() - (getWidth() - 2 * mHorizontalPadding)) / 2;
//
//            int width = getWidth();
//            int height = getHeight();
//            // 拿到图片的宽和高
//            int dw = d.getIntrinsicWidth();
//            int dh = d.getIntrinsicHeight();
//            float scale = 1.0f;
//            if (dw < getWidth() - mHorizontalPadding * 2
//                    && dh > getHeight() - mVerticalPadding * 2)
//            {
//                scale = (getWidth() * 1.0f - mHorizontalPadding * 2) / dw;
//            }
//
//            if (dh < getHeight() - mVerticalPadding * 2
//                    && dw > getWidth() - mHorizontalPadding * 2)
//            {
//                scale = (getHeight() * 1.0f - mVerticalPadding * 2) / dh;
//            }
//
//            if (dw < getWidth() - mHorizontalPadding * 2
//                    && dh < getHeight() - mVerticalPadding * 2)
//            {
//                float scaleW = (getWidth() * 1.0f - mHorizontalPadding * 2)
//                        / dw;
//                float scaleH = (getHeight() * 1.0f - mVerticalPadding * 2) / dh;
//                scale = Math.max(scaleW, scaleH);
//            }
//
//            initScale = scale;
//            SCALE_MID = initScale * 2;
//            SCALE_MAX = initScale * 4;
//            Log.e(TAG, "initScale = " + initScale);
//            mImageMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
//            mImageMatrix.postScale(scale, scale, getWidth() / 2,
//                    getHeight() / 2);
//            // 图片移动至屏幕中心
//            setImageMatrix(mImageMatrix);
//            once = false;
//        }
//
//    }

    float[] floats = new float[9];

    /**
     * 剪切图片，返回剪切后的bitmap对象
     *
     * @return
     */
    public Bitmap crop(int cropStartX, int cropStartY, int cropWidth, int cropHeight) {
        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();

        mImageMatrix.getValues(floats);
        float scaleX = floats[Matrix.MSCALE_X];
        float scaleY = floats[Matrix.MSCALE_Y];
        //偏移量 是和  放大系数 无关的 绝对长度
        float transX = floats[Matrix.MTRANS_X];
        float transY = floats[Matrix.MTRANS_Y];

        float leftOffset = -transX + cropStartX;
        leftOffset = leftOffset / scaleX;

        float topOffset = -transY + cropStartY;
        topOffset = topOffset / scaleY;

        float width = cropWidth;
        width = width / scaleX;
        float height = cropHeight;
        height = height / scaleY;

        return Bitmap.createBitmap(bitmap,
                (int) leftOffset,
                (int) topOffset,
                (int) width,
                (int) height);
    }


    /**
     * 剪切图片，返回剪切后的bitmap对象
     *
     * @return
     */
    public Bitmap clip() {
        mHorizontalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
                        .getDisplayMetrics());
        //计算矩形区域的宽度
        int mWidth = getWidth() - 2 * mHorizontalPadding;
        //计算距离屏幕垂直边界 的边距
        mVerticalPadding = (getHeight() - mWidth) / 2;

        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        float leftOffset = mHorizontalPadding;
        float topOffset = mVerticalPadding;
        float width = getWidth() - 2 * mHorizontalPadding;
        float height = getWidth() - 2 * mHorizontalPadding;

        float[] floats = new float[9];
        mImageMatrix.getValues(floats);
        float scaleX = floats[Matrix.MSCALE_X];
        float scaleY = floats[Matrix.MSCALE_Y];
        //偏移量 是和  放大系数 无关的 绝对长度
        float transX = floats[Matrix.MTRANS_X];
        float transY = floats[Matrix.MTRANS_Y];
//        Log.e(TAG, "MSCALE_X: " + scaleX
//                + "MSCALE_Y: " + scaleY
//                + "MTRANS_X: " + transX
//                + "MTRANS_Y: " + transY);

        leftOffset = -transX + leftOffset;
        leftOffset = leftOffset / scaleX;

        topOffset = -transY + topOffset;
        topOffset = topOffset / scaleY;

        width = width / scaleX;
        height = height / scaleY;

        return Bitmap.createBitmap(bitmap,
                (int) leftOffset,
                (int) topOffset,
                (int) width,
                (int) height);


//        float top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
//        float left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 77, getResources().getDisplayMetrics());
//        float width = getWidth() - left;
//        float height = getHeight() - top;
//
//        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
//        Log.d(TAG, "clip() called：" + bitmap.getHeight());
//
//        float[] floats = new float[9];
//        mImageMatrix.getValues(floats);
//        float scaleX = floats[Matrix.MSCALE_X];
//        float scaleY = floats[Matrix.MSCALE_Y];
//        //偏移量 是和  放大系数 无关的 绝对长度
//        float transX = floats[Matrix.MTRANS_X];
//        float transY = floats[Matrix.MTRANS_Y];
////        Log.e(TAG, "MSCALE_X: " + scaleX
////                + "MSCALE_Y: " + scaleY
////                + "MTRANS_X: " + transX
////                + "MTRANS_Y: " + transY);
//
//        top = -transY + top;
//        top = top / scaleY;
//
//        left = -transX + left;
//        left = left / scaleX;
//
//
//        width = width / scaleX;
//        height = height / scaleY;
//
//
//        return Bitmap.createBitmap(bitmap,
//                (int) left,
//                (int) top,
//                (int) width,
//                (int) height);
    }

    /**
     * 边界检测
     */
    private void checkBorder() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width - 2 * mHorizontalPadding) {
            if (rect.left > mHorizontalPadding) {
                deltaX = -rect.left + mHorizontalPadding;
            }
            if (rect.right < width - mHorizontalPadding) {
                deltaX = width - mHorizontalPadding - rect.right;
            }
        }
        if (rect.height() >= height - 2 * mVerticalPadding) {
            if (rect.top > mVerticalPadding) {
                deltaY = -rect.top + mVerticalPadding;
            }
            if (rect.bottom < height - mVerticalPadding) {
                deltaY = height - mVerticalPadding - rect.bottom;
            }
        }
        mImageMatrix.postTranslate(deltaX, deltaY);

    }


    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable d = getDrawable();
            if (d == null)
                return;
            Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();

            int width = getWidth();
            int height = getHeight();
            Log.e(TAG, d.getIntrinsicWidth() + " , " + d.getIntrinsicHeight());
            Log.e(TAG, bitmap.getWidth() + " , " + bitmap.getHeight());
            Log.e(TAG, width + " , " + height);
            // 拿到图片的宽和高
            int dw = bitmap.getWidth();
            int dh = bitmap.getHeight();
            float scale = 1.0f;
            // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
            if (dw > width && dh <= height) {
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw <= width) {
                scale = height * 1.0f / dh;
            }
            // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
            if (dw > width && dh > height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            } else if (dw < width && dh < height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            initScale = scale;
            // 图片移动至屏幕中心
            mImageMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
            mImageMatrix
                    .postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mImageMatrix);
            once = false;
        }

    }
}
