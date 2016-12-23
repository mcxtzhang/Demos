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
 * 时间： 2016/12/23.
 */

public class CstListHeaderBehavior extends ViewOffsetBehavior<View> {
    private static final String TAG = "zxt/List出现HeaderView";
    //它完全出现，SecondView Topoffset的距离
    protected int mFirstViewTopOffsetMax;

    protected int mMoveDistance;

    public CstListHeaderBehavior() {
    }

    public CstListHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        boolean b = dependency == parent.getChildAt(0);
        if (b) {
            mMoveDistance = (dependency.getHeight() - parent.getChildAt(1).getHeight());
        }
        Log.d(TAG, "layoutDependsOn() called with: b = [" + b + "], child = [" + child + "], dependency = [" + dependency + "]");
        return b;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //dependency.getBottom()

        setTopAndBottomOffset(dependency.getBottom());


        return true;
    }
}
