package com.mcxtzhang.coordinatordemo.uc;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.coordinatordemo.util.ViewOffsetBehavior;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/22.
 */

public class CstNestContentBehavior extends ViewOffsetBehavior<View> {
    private static final String TAG = "zxt/内容Behavior";
    //它完全出现，SecondView Topoffset的距离
    protected int mFirstViewTopOffsetMax;

    protected int mMoveDistance;

    public CstNestContentBehavior() {
        super();
    }

    public CstNestContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "layoutDependsOn() called with: parent = [" + parent + "], child = [" + child + "], dependency = [" + dependency + "]");
        boolean flag = dependency == parent.getChildAt(0);
        if (flag) {
            mMoveDistance = (dependency.getHeight() - parent.getChildAt(1).getHeight() - parent.getChildAt(2).getHeight());
            mFirstViewTopOffsetMax = -(dependency.getHeight() - parent.getChildAt(1).getHeight());
        }
        return flag;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "onDependentViewChanged() called with: parent = [" + parent + "], child = [" + child + "], dependency = [" + dependency + "]");
        float fraction = dependency.getTop() * 1.0f / mFirstViewTopOffsetMax;

        setTopAndBottomOffset((int) (mMoveDistance * (1 - fraction)) +parent.getChildAt(1).getHeight() +parent.getChildAt(2).getHeight() );
        return true;
    }


}
