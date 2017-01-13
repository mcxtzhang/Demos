package com.mcxtzhang.viewpagerdemo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Intro
 * Author      zhangxutong
 * E-mail      mcxtzhang@163.com
 * Home Page   http://blog.csdn.net/zxt0601
 * Created    2017/1/13.
 * Since
 * History
 */
public class DetailAdapter extends PagerAdapter {
    private List<View> mViews;

    public DetailAdapter(List<View> views) {
        mViews = views;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public int getCount() {
        return null != mViews ? mViews.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
