package com.mcxtzhang.coordinatordemo;

import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/10/23.
 */

public class BottomHideBehavior extends CoordinatorLayout.Behavior<View> {
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d("TAG", "height" + dependency.getHeight() + ",bottom:" + dependency.getBottom() + ",top:" + dependency.getTop());
        child.setTranslationY(dependency.getHeight() - dependency.getBottom());


        Rect rect = new Rect();
        ViewGroupUtils.getDescendantRect(parent, child, rect);
        Log.e(TAG, "appBarRect = [" + rect +"]");


        return super.onDependentViewChanged(parent, child, dependency);
    }
}
