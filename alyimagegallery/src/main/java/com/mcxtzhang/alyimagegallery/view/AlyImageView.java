package com.mcxtzhang.alyimagegallery.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Intro: 固定高度，宽度伸缩的ImageView
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/6/16.
 * History:
 */

public class AlyImageView extends android.support.v7.widget.AppCompatImageView {
    private static final int BASE_HEIGHT = 400;

    public AlyImageView(Context context) {
        this(context, null);
    }

    public AlyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        setScaleType(ScaleType.CENTER_CROP);
    }

    public void show(Bitmap bitmap) {
        if (bitmap == null)
            return;
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = BASE_HEIGHT;
        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();

        float ratio = (float) BASE_HEIGHT / bh;
        lp.width = (int) (bw * ratio);
        setLayoutParams(lp);
        setImageBitmap(bitmap);
    }

    public void show(Drawable drawable) {
        if (drawable == null || !(drawable instanceof BitmapDrawable))
            return;
        show(((BitmapDrawable) drawable).getBitmap());
    }

    public void show(@DrawableRes int drawableId) {
        show(ContextCompat.getDrawable(getContext(), drawableId));
    }
}
