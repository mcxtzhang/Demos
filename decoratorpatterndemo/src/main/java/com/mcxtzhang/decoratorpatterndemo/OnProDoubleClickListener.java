package com.mcxtzhang.decoratorpatterndemo;

import android.view.View;

/**
 * 介绍：用装饰者模式实现，
 * 防止多次点击的View.OnClickListener
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/17.
 */

public class OnProDoubleClickListener implements View.OnClickListener {
    private static final long TIME_GAP = 3000;//判断重复点击的时间间隔  单位毫秒
    private long mLastClickTime;//上次点击的时间
    private View.OnClickListener mOnClickListener;

    public OnProDoubleClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - mLastClickTime > TIME_GAP) {
            mLastClickTime = System.currentTimeMillis();
            if (null != mOnClickListener) {
                mOnClickListener.onClick(v);
            }
        }
    }
}
