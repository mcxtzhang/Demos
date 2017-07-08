package com.mcxtzhang.alyimagegallery.gallery.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.mcxtzhang.alyimagegallery.R;
import com.mcxtzhang.commonadapter.viewgroup.VGUtil;
import com.mcxtzhang.commonadapter.viewgroup.adapter.cache.ViewHolder;
import com.mcxtzhang.commonadapter.viewgroup.adapter.single.SingleAdapter;

import java.util.List;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/6/16.
 * History:
 */

public class BaseAlyGallery extends HorizontalScrollView {

    public BaseAlyGallery(Context context) {
        super(context);
    }

    public BaseAlyGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseAlyGallery(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setDatas(List<String> imgList) {
        if (null == imgList || imgList.isEmpty())
            return;
        LinearLayout linearLayout = new LinearLayout(getContext());
        HorizontalScrollView.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(linearLayout, lp);
        new VGUtil.Builder()
                .setParent(linearLayout)
                .setAdapter(new SingleAdapter<String>(getContext(), imgList, R.layout.base_item_image) {
                    @Override
                    public void onBindViewHolder(ViewGroup viewGroup, ViewHolder viewHolder, String s, int i) {
                        final AlyImageView view = (AlyImageView) viewHolder.getView(R.id.iv);
                        if (null != mOnLoadImageListener) {
                            mOnLoadImageListener.onLoadImage(view, s);
                        }
                    }
                })
                .build()
                .bind();
/*        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        setAdapter(new CommonAdapter<String>(getContext(), imgList, R.layout.base_item_image) {
            @Override
            public void convert(final com.mcxtzhang.commonadapter.rv.ViewHolder viewHolder, String s) {
                Glide.with(getContext())

                        .load(s)
                        .asBitmap()
                        .placeholder(R.drawable.ic_launcher)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                ((AlyImageView) viewHolder.getView(R.id.iv)).show(resource);
                            }
                        });
            }
        });*/

    }


    public interface OnLoadImageListener {
        void onLoadImage(AlyImageView image, String path);
    }

    private OnLoadImageListener mOnLoadImageListener;

    public OnLoadImageListener getOnLoadImageListener() {
        return mOnLoadImageListener;
    }

    public BaseAlyGallery setOnLoadImageListener(OnLoadImageListener onLoadImageListener) {
        mOnLoadImageListener = onLoadImageListener;
        return this;
    }
}
