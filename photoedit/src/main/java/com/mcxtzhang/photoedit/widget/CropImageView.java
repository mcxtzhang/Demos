package com.mcxtzhang.photoedit.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
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
import android.view.View;
import android.view.ViewConfiguration;


/**
 * Created by zhangxutong on 2019/3/13.
 */
public class CropImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = CropImageView.class.getSimpleName();

    private UgcCropView mParentView;

    private int mWidth, mHeight;
    private Matrix mImageMatrix = new Matrix();
    private final float[] mFloats = new float[9];
    private Bitmap mOriginBitmap;
    private boolean isBusy;

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
                mCropDragView.setShowCropLine(true);
                if (getDrawable() == null) {
                    return true;
                }

                float scale = getScale();
                float scaleFactor = detector.getScaleFactor();

                float widthScaleMax = mCropDragView.getCropWidth() / mCropDragView.getPhotoMinWidthPixels();
                float heightScaleMax = mCropDragView.getCropHeight() / mCropDragView.getPhotoMinHeightPixels();
                float scaleMax = Math.min(widthScaleMax, heightScaleMax);
                if (scale * scaleFactor > scaleMax) {
                    scaleFactor = scaleMax / scale;
                }

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

            private float beginScale;

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                beginScale = getScale();
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                if (beginScale != 0) {
                    reportScale(getScale() / beginScale);
                }
            }
        });

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    public boolean isBusy() {
        return isBusy;
    }

    public CropImageView setParentView(UgcCropView parentView) {
        mParentView = parentView;
        return this;
    }

    public CropImageView setCropDragView(CropDragView cropDragView) {
        mCropDragView = cropDragView;
        return this;
    }

    public Bitmap getOriginBitmap() {
        return mOriginBitmap;
    }

    public void reset() {
        showBitmapInCenter(mOriginBitmap, null);
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    public RectF getMatrixRectF() {
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

    private boolean isReported;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "cropimageview onTouchEvent() called with: event = [" + event + "]");
        if (checkTooLongWide()) {
            return false;
        }

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
            case MotionEvent.ACTION_DOWN:
                mParentView.setBusy(true);
                isBusy = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isReported) {
                    isReported = true;
                }
                mCropDragView.setShowCropLine(true);
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
                isBusy = false;
                isReported = false;
                lastPointerCount = 0;

                //checkOverBorder();
                Drawable d = getDrawable();
                if (d == null) {
                    return true;
                }
                float scaleNow = getScale();
                float rotate = getRotate();
                float scale = 1;
                if (rotate == 0 || rotate == 180) {
                    scale = Math.max(mCropDragView.getCropWidth() * 1.0f / (d.getIntrinsicWidth() * scaleNow), mCropDragView.getCropHeight() * 1.0f / (d.getIntrinsicHeight() * scaleNow));
                } else {
                    scale = Math.max(mCropDragView.getCropWidth() * 1.0f / (d.getIntrinsicHeight() * scaleNow), mCropDragView.getCropHeight() * 1.0f / (d.getIntrinsicWidth() * scaleNow));
                }
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
                mCropDragView.onTouchUp();
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
        mImageMatrix.getValues(mFloats);
        // calculate real scale
        float scalex = mFloats[Matrix.MSCALE_X];
        float skewy = mFloats[Matrix.MSKEW_Y];
        float rScale = (float) Math.sqrt(scalex * scalex + skewy * skewy);
        return rScale;
    }

    public final float getRotate() {
        mImageMatrix.getValues(mFloats);
        // calculate the degree of rotation
        float rAngle = Math.round(Math.atan2(mFloats[Matrix.MSKEW_X], mFloats[Matrix.MSCALE_X]) * (180 / Math.PI));
        return rAngle;
    }

    /**
     * 剪切图片，返回剪切后的bitmap对象
     *
     * @return
     */
    public UGCPhotoCropRotateModel crop(float cropStartX, float cropStartY, float cropWidth, float cropHeight) {
        Log.d(TAG, "crop() called with: cropStartX = [" + cropStartX + "], cropStartY = [" + cropStartY + "], cropWidth = [" + cropWidth + "], cropHeight = [" + cropHeight + "]");
        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        float scale = getScale();
        float rotate = getRotate();

        mImageMatrix.getValues(mFloats);
        //偏移量 是和  放大系数 无关的 绝对长度
        float transX = mFloats[Matrix.MTRANS_X];
        float transY = mFloats[Matrix.MTRANS_Y];
        Log.d(TAG, "crop() called with: bitmap.getWidth() = [" + bitmap.getWidth() + "], bitmap.getHeight = [" + bitmap.getHeight() + "], rotate = [" + rotate + "], scale = [" + scale + "]");
        Log.d(TAG, "crop() called with: transX = [" + transX + "], transY = [" + transY);
        if (rotate == -180) {
            rotate = 180;
        }

        float startX = 0;
        float startY = 0;
        float width = 0;
        float height = 0;

        if (rotate == 0) {
            startX = -transX + cropStartX;
            startY = -transY + cropStartY;
            width = cropWidth;
            height = cropHeight;
        } else if (rotate == -90) {
            //顺时针90度
            startX = -transY + cropStartY;
            startY = transX - (cropStartX + cropWidth);
            width = cropHeight;
            height = cropWidth;
        } else if (rotate == 180) {
            startX = transX - (cropStartX + cropWidth);
            startY = transY - (cropStartY + cropHeight);
            width = cropWidth;
            height = cropHeight;
        } else if (rotate == 90) {
            startX = transY - (cropStartY + cropHeight);
            startY = -transX + cropStartX;
            width = cropHeight;
            height = cropWidth;
        }


        startX = startX / scale;
        startY = startY / scale;

        width = width / scale;
        height = height / scale;

//        return Bitmap.createBitmap(bitmap,
//                (int) startX,
//                (int) startY,
//                (int) width,
//                (int) height,
//                mImageMatrix, true);
        //check border
        if (startX < 0) {
            startX = 0;
        }
        if (startY < 0) {
            startY = 0;
        }
        int mOriginBitmapWidth = mOriginBitmap.getWidth();
        if (width > mOriginBitmapWidth) {
            width = mOriginBitmapWidth;
        }
        int mOriginBitmapHeight = mOriginBitmap.getHeight();
        if (height > mOriginBitmapHeight) {
            height = mOriginBitmapHeight;
        }
        if (width == mOriginBitmapWidth) {
            startX = 0;
        }
        if (height == mOriginBitmapHeight) {
            startY = 0;
        }
        if (startX + width > mOriginBitmapWidth) {
            width = mOriginBitmapWidth - startX;
        }
        if (startY + height > mOriginBitmapHeight) {
            height = mOriginBitmapHeight - startY;
        }

        UGCPhotoCropRotateModel cropRotateModel = new UGCPhotoCropRotateModel();
        cropRotateModel.x = (int) startX;
        cropRotateModel.y = (int) startY;
        cropRotateModel.width = (int) width;
        cropRotateModel.height = (int) height;
        cropRotateModel.rotate = rotate;
        cropRotateModel.cropRate = mCropDragView.getCropRate();
        return cropRotateModel;
    }

    /**
     * 将图片 缩放、居中展示
     */
    public void showBitmapInCenter(Bitmap bitmap, UGCPhotoCropRotateModel photoCropRotateModel) {
        if (bitmap == null) {
            return;
        }
        mImageMatrix = new Matrix();
        mOriginBitmap = bitmap;

        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        bitmapDrawable.setTargetDensity(bitmap.getDensity());
        setImageDrawable(bitmapDrawable);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Log.e(TAG, width + " , " + height);
        int dw, dh;
        if (null == photoCropRotateModel) {
            //Log.e(TAG, bitmap.getWidth() + " , " + bitmap.getHeight());
            // 拿到图片的宽和高
            dw = bitmap.getWidth();
            dh = bitmap.getHeight();

            // 图片移动至屏幕中心
            mImageMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
        } else {
            dw = photoCropRotateModel.width;
            dh = photoCropRotateModel.height;
            mImageMatrix.postTranslate((width - dw) / 2 - photoCropRotateModel.x, (height - dh) / 2 - photoCropRotateModel.y);
            double rotate = photoCropRotateModel.rotate;
            Log.d(TAG, "showBitmapInCenter() called with: rotate = [" + rotate + "], photoCropRotateModel = [" + photoCropRotateModel + "]");
        }
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
        final float tranX = (mWidth - cropWidth - cropStartX - cropStartX) / 2;
        final float tranY = (mHeight - cropHeight - cropStartY - cropStartY) / 2;

        float scale = Math.min(mWidth * 1.0f / cropWidth, mHeight * 1.0f / cropHeight);


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


        animateTranslate(tranX, tranY);
        animateScale(scale);
        animateUpdateDragCropRectUI(cropStartX, cropStartY, cropWidth, cropHeight, new Runnable() {
            @Override
            public void run() {
                mParentView.setBusy(false);
            }
        });

        final float finalStartX = cropStartX;
        final float finalStartY = cropStartY;
        final float finalCropWidth = cropWidth;
        final float finalCropHeight = cropHeight;
        Log.d(TAG, "updateShowInCenter() called with: tranX = [" + tranX + "], tranY = [" + tranY + "], scale = [" + scale + "], cropHeight = [" + cropHeight + "]");
    }

    private void animateTranslate(final float tobeTranX, final float tobeTranY) {
        float scale = getScale();
        final float tobeTranXInNoScale = tobeTranX / scale;
        final float tobeTranYInNoScale = tobeTranY / scale;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(250);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float lastTranX = 0, lastTranY = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();

                float tempTranX = tobeTranXInNoScale * fraction - lastTranX;
                float tempTranY = tobeTranYInNoScale * fraction - lastTranY;
                lastTranX += tempTranX;
                lastTranY += tempTranY;

                //可能与scale 动画一起执行，所以要考虑当前scale
                float currentScale = getScale();
                tempTranX *= currentScale;
                tempTranY *= currentScale;

                mImageMatrix.postTranslate(tempTranX, tempTranY);
                setImageMatrix(mImageMatrix);
            }
        });
        valueAnimator.start();

        Log.d(TAG, "animateTranslate() called with: tobeTranX = [" + tobeTranX + "], tobeTranY = [" + tobeTranY + "]");
        getImageMatrix().getValues(mFloats);
        Log.d(TAG, "animateTranslate() called with: MTRANS_X: " + mFloats[Matrix.MTRANS_X]
                + "MTRANS_Y: " + mFloats[Matrix.MTRANS_Y]);

    }

    private void animateScale(final float scale) {
        if (scale == 1) {
            return;
        }
        Log.d(TAG, "animateScale() called with: scale = [" + scale + "]");
        final float beginScale = getScale();
        final float finalScale = beginScale * scale;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(250);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();

                float tempTargetScale = beginScale + (finalScale - beginScale) * fraction;
                float tempScale = tempTargetScale / getScale();
                mImageMatrix.postScale(tempScale, tempScale, getWidth() / 2, getHeight() / 2);
                setImageMatrix(mImageMatrix);
            }
        });
        valueAnimator.start();
    }

    private void animateRotate(final float degree, final Runnable runnable) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(250);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float lastDegree = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                float tempDegree = degree * fraction - lastDegree;
                lastDegree += tempDegree;

                mImageMatrix.postRotate(tempDegree, mWidth / 2, mHeight / 2);
                setImageMatrix(mImageMatrix);
                //Log.d(TAG, "onAnimationUpdate() called with: getRotate = [" + getRotate() + "]");
                tempDegree = 90 * fraction;
                mCropDragView.setRotation(tempDegree);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //mCropDragView.setHideCropBorder(true);
                mCropDragView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //mCropDragView.setHideCropBorder(false);
                mCropDragView.setVisibility(View.VISIBLE);
                mCropDragView.setRotation(0);
                if (null != runnable) {
                    runnable.run();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    private void animateUpdateDragCropRectUI(final float finalStartX,
                                             final float finalStartY,
                                             final float finalCropWidth,
                                             final float finalCropHeight,
                                             final Runnable runnable) {
        final float beginStartX = mCropDragView.getStartX();
        final float beginStartY = mCropDragView.getStartY();
        final float beginCropWidth = mCropDragView.getCropWidth();
        final float beginCropHeight = mCropDragView.getCropHeight();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(250);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();

                float tempCropX = beginStartX + (finalStartX - beginStartX) * fraction;
                float tempCropY = beginStartY + (finalStartY - beginStartY) * fraction;
                float tempCropWidth = beginCropWidth + (finalCropWidth - beginCropWidth) * fraction;
                float tempCropHeight = beginCropHeight + (finalCropHeight - beginCropHeight) * fraction;

                setImageMatrix(mImageMatrix);
                mCropDragView.setStartX(tempCropX)
                        .setStartY(tempCropY)
                        .setCropWidth(tempCropWidth)
                        .setCropHeight(tempCropHeight)
                        .invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (null != runnable) {
                    runnable.run();
                }
            }
        });
        valueAnimator.start();
    }

    public boolean checkTooLongWide() {
        if (isBusy()) {
            return false;
        }
        RectF matrixRectF = getMatrixRectF();
        float scale = Math.max(mCropDragView.getCropMinHeight() / matrixRectF.height(), mCropDragView.getCropMinWidth() / matrixRectF.width());

        if (scale > 1) {
            Log.e(TAG, "pro1_ do checkTooLongWide: ");

            mImageMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mImageMatrix);

            int newCropWidth = (int) (mCropDragView.getCropWidth() * scale);
            int newCropHeight = (int) (mCropDragView.getCropHeight() * scale);
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
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    public void rotate() {
        rotate(false);
    }

    public void rotate(boolean animate) {
        final float cropWidth = mCropDragView.getCropWidth();
        final float cropHeight = mCropDragView.getCropHeight();

        float scale = 1;
        if (cropWidth < mHeight && cropHeight < mWidth) {
            //旋转后需要放大
            scale = Math.min(mHeight * 1.0f / cropWidth, mWidth * 1.0f / cropHeight);
        } else if (cropWidth > mHeight) {
            //旋转后 不够完全显示裁剪区域 需要缩小
            scale = (mHeight * 1.0f / cropWidth);
        } else if (cropHeight > mWidth) {
            scale = (mWidth * 1.0f / cropHeight);
        }
        if (scale != 1) {
            if (animate) {
                animateScale(scale);

                ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, scale).setDuration(250);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedValue = (float) animation.getAnimatedValue();
                        mCropDragView.setScaleX(animatedValue);
                        mCropDragView.setScaleY(animatedValue);
                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCropDragView.setScaleX(1);
                        mCropDragView.setScaleY(1);
                    }
                });
                valueAnimator.start();
            } else {
                mImageMatrix.postScale(scale, scale, mWidth / 2, mHeight / 2);
            }
        }
        if (animate) {
            mParentView.setBusy(true);
            final float finalScale = scale;
            animateRotate(90, new Runnable() {
                @Override
                public void run() {
                    int newCropWidth = (int) (cropHeight * finalScale);
                    int newCropHeight = (int) (cropWidth * finalScale);
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
                    mParentView.setBusy(false);
                }
            });
        } else {
            mImageMatrix.postRotate(90, mWidth / 2, mHeight / 2);
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

    public void scaleWithAnim(float scale) {
        Log.d(TAG, "scaleWithAnim() called with: scale = [" + scale + "]");
        //animateScale(scale);
        mImageMatrix.postScale(scale, scale, mWidth / 2, mHeight / 2);
        setImageMatrix(mImageMatrix);
    }

    public void translateWithAnim(float tranX, float tranY) {
        //animateTranslate(tranX, tranY);
        mImageMatrix.postTranslate(tranX, tranY);
        setImageMatrix(mImageMatrix);
    }

    private void reportScale(float scale) {
        if (scale > 1) {
        } else if (scale < 1) {
        }
    }
}
