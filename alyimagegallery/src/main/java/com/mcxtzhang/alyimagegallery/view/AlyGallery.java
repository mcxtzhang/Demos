package com.mcxtzhang.alyimagegallery.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mcxtzhang.alyimagegallery.R;

/**
 * Intro: 用Glide加载图片的Gallery
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/7/4.
 * History:
 */

public class AlyGallery extends AlyBaseGallery {
    public AlyGallery(Context context) {
        super(context);
        init(context);
    }

    public AlyGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AlyGallery(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(final Context context) {
        setOnLoadImageListener(new AlyBaseGallery.OnLoadImageListener() {
            @Override
            public void onLoadImage(final AlyImageView image, String path) {
                image.show(R.mipmap.ic_launcher);
                Glide.with(context)
                        .load(path)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                image.show(resource);
                            }
                        });
            }
        });
    }
}
