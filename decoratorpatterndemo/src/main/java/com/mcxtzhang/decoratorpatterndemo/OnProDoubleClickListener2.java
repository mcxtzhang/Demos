package com.mcxtzhang.decoratorpatterndemo;

import android.view.View;

/**
 * 介绍：另一种实现防止多次点击的Listener
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/17.
 */

public abstract class OnProDoubleClickListener2 implements View.OnClickListener {
    private static final long TIME_GAP = 3000;//判断重复点击的时间间隔  单位毫秒
    private long mLastClickTime;//上次点击的时间

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - mLastClickTime > TIME_GAP) {
            mLastClickTime = System.currentTimeMillis();
            onProDoubleClick(v);
        }
    }

    public abstract void onProDoubleClick(View v);
}
