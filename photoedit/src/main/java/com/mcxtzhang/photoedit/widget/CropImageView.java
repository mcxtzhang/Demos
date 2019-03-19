package com.mcxtzhang.photoedit.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ViewConfiguration;

/**
 * Created by zhangxutong on 2019/3/13.
 */
public class CropImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = CropImageView.class.getSimpleName();

    private int mWidth, mHeight;
    private final Matrix mImageMatrix = new Matrix();
    private final float[] matrixValues = new float[9];

    private ScaleGestureDetector mScaleGestureDetector = null;

    /**
     * 外部通信变量
     */
    private CropDragView mCropDragView;

    private int mTouchSlop;

    public CropImageView(Context context) {
        this(context, null);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, new OnScaleGestureListener() {
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
        });

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    public CropImageView setCropDragView(CropDragView cropDragView) {
        mCropDragView = cropDragView;
        return this;
    }

    public void reset() {
        mImageMatrix.reset();
        showBitmapInCenter();
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
            mLastX = x;
            mLastY = y;
        }


        lastPointerCount = pointerCount;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
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
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;

                //checkOverBorder();
                Drawable d = getDrawable();
                if (d == null) {
                    return true;
                }
                float scaleNow = getScale();
                float scale = Math.max(mCropDragView.getCropWidth() * 1.0f / (d.getIntrinsicWidth() * scaleNow), mCropDragView.getCropHeight() * 1.0f / (d.getIntrinsicHeight() * scaleNow));
                if (scale > 1) {
                    mImageMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
                }

                RectF matrixRectF = getMatrixRectF();
                Log.d(TAG, "onTouchEvent() called with: matrixRectF = [" + matrixRectF + "]");
                Log.d(TAG, "onTouchEvent() called with: matrixRectF = [" + matrixRectF + "]");
                int tranX = 0, tranY = 0;

                if (matrixRectF.top > mCropDragView.getStartY()) {
                    tranY = (int) (mCropDragView.getStartY() - matrixRectF.top);
                }
                if (matrixRectF.bottom < mCropDragView.getStartY() + mCropDragView.getCropHeight()) {
                    tranY = (int) (mCropDragView.getStartY() + mCropDragView.getCropHeight() - matrixRectF.bottom);
                }
                if (matrixRectF.left > mCropDragView.getStartX()) {
                    tranX = (int) (mCropDragView.getStartX() - matrixRectF.left);
                }
                if (matrixRectF.right < mCropDragView.getStartX() + mCropDragView.getCropWidth()) {
                    tranX = (int) (mCropDragView.getStartX() + mCropDragView.getCropWidth() - matrixRectF.right);
                }
                mImageMatrix.postTranslate(tranX, tranY);

                setImageMatrix(mImageMatrix);
                break;
        }
//        float[] floats = new float[9];
//        mImageMatrix.getValues(floats);
//
//        Log.e(TAG, "MSCALE_X: " + floats[Matrix.MSCALE_X]
//                + "MSCALE_Y: " + floats[Matrix.MSCALE_Y]
//                + "MTRANS_X: " + floats[Matrix.MTRANS_X]
//                + "MTRANS_Y: " + floats[Matrix.MTRANS_Y]
//        );

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

    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding = 20;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding;

    float[] floats = new float[9];

    /**
     * 剪切图片，返回剪切后的bitmap对象
     *
     * @return
     */
    public Bitmap crop(float cropStartX, float cropStartY, float cropWidth, float cropHeight) {
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

    /**
     * 将图片 缩放、居中展示
     */
    public void showBitmapInCenter() {
        Drawable d = getDrawable();
        if (!(d instanceof BitmapDrawable))
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

        // 图片移动至屏幕中心
        mImageMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
        mImageMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        setImageMatrix(mImageMatrix);
    }

    /**
     * 裁剪后被选中区域自动放大、居中展示。
     *
     * @param cropStartX
     * @param cropStartY
     * @param cropWidth
     * @param cropHeight
     */
    public void updateShowInCenter(float cropStartX, float cropStartY, float cropWidth, float cropHeight) {
        float tranX = 0, tranY = 0;
        tranX = (mWidth - cropWidth - cropStartX - cropStartX) / 2;
        tranY = (mHeight - cropHeight - cropStartY - cropStartY) / 2;
//        int horizontalSpace = ;
//        if (mWidth >) {
//            //右边离中轴线更近
//            tranX =
//        } else {
//
//        }
        mImageMatrix.postTranslate(tranX, tranY);


        float scale = Math.min(mWidth * 1.0f / cropWidth, mHeight * 1.0f / cropHeight);
        mImageMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        setImageMatrix(mImageMatrix);


        cropWidth *= scale;
        cropHeight *= scale;
        if (cropWidth > mWidth) {
            cropWidth = mWidth;
        }
        if (cropHeight > mHeight) {
            cropHeight = mHeight;
        }
        cropStartY = (mHeight - cropHeight) / 2;
        cropStartX = (mWidth - cropWidth) / 2;

        mCropDragView.setStartX(cropStartX)
                .setStartY(cropStartY)
                .setCropWidth(cropWidth)
                .setCropHeight(cropHeight)
                .invalidate();

        Log.d(TAG, "updateShowInCenter() called with: tranX = [" + tranX + "], tranY = [" + tranY + "], scale = [" + scale + "], cropHeight = [" + cropHeight + "]");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    public void rotate() {
        mImageMatrix.postRotate(90, mWidth / 2, mHeight / 2);

        float cropWidth = mCropDragView.getCropWidth();
        float cropHeight = mCropDragView.getCropHeight();

        float scale = 0;
        if (cropWidth < mHeight && cropHeight < mWidth) {
            //旋转后需要放大
            scale = Math.min(mHeight * 1.0f / cropWidth, mWidth * 1.0f / cropHeight);
        } else if (cropWidth > mHeight) {
            //旋转后 不够完全显示裁剪区域 需要缩小
            scale = (mHeight * 1.0f / cropWidth);
        } else if (cropHeight > mWidth) {
            scale = (mWidth * 1.0f / cropHeight);
        }
        mImageMatrix.postScale(scale, scale, mWidth / 2, mHeight / 2);
        setImageMatrix(mImageMatrix);


        int newCropWidth = (int) (cropHeight * scale);
        int newCropHeight = (int) (cropWidth * scale);
        if (newCropWidth > mWidth) {
            newCropWidth = mWidth;
        }
        if (newCropHeight > mHeight) {
            newCropHeight = mHeight;
        }
        int cropStartX, cropStartY;
        cropStartY = (mHeight - newCropHeight) / 2;
        cropStartX = (mWidth - newCropWidth) / 2;

        mCropDragView.setStartX(cropStartX)
                .setStartY(cropStartY)
                .setCropWidth(newCropWidth)
                .setCropHeight(newCropHeight)
                .invalidate();
    }
}
