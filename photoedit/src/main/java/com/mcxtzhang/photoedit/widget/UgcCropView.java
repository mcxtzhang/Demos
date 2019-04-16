package com.mcxtzhang.photoedit.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by zhangxutong on 2019/4/8.
 */

public class UgcCropView extends FrameLayout {
    CropDragView mCropDragView;
    CropImageView mCropImageView;

    private boolean isBusy = false;

    public UgcCropView(@NonNull Context context) {
        this(context, null);
    }

    public UgcCropView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UgcCropView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mCropImageView = new CropImageView(context)
                .setParentView(this);
        addView(mCropImageView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mCropDragView = new CropDragView(context)
                .setParentView(this);
        addView(mCropDragView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public boolean isBusy() {
        return isBusy;
    }

    public UgcCropView setBusy(boolean busy) {
        isBusy = busy;
        return this;
    }

    public CropDragView getCropDragView() {
        return mCropDragView;
    }

    public CropImageView getCropImageView() {
        return mCropImageView;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                NovaCodeLog.i(UgcCropView.class, "crop_block", "dispatch touch down,set busy ture");
//                isBusy = true;
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
