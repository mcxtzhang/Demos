package com.mcxtzhang.coordinatordemo.uc;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.coordinatordemo.util.ViewOffsetBehavior;

/**
 * 介绍：一开始隐藏 后来从头部出现的那货
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/23.
 */

public class CstDelayEnterBehavior extends ViewOffsetBehavior<View> {
    private static final String TAG = "zxt/Delay延迟出现View";
    //它完全出现，SecondView Topoffset的距离
    protected int mFirstViewTopOffsetMax;

    protected int mMoveDistance;

    public CstDelayEnterBehavior() {
    }

    public CstDelayEnterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        boolean b = dependency == parent.getChildAt(0);
        if (b) {
            mMoveDistance = child.getHeight();
            mFirstViewTopOffsetMax = -(dependency.getHeight() - mMoveDistance);
        }
        Log.d(TAG, "layoutDependsOn() called with: b = [" + b + "], child = [" + child + "], dependency = [" + dependency + "]");
        return b;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float fraction = dependency.getTop() * 1.0f / mFirstViewTopOffsetMax;
        Log.d(TAG, "onDependentViewChanged() called with: fraction = [" + fraction + "], child = [" + child + "], dependency = [" + dependency + "]");
        // 0 隐藏 1 显示
        setTopAndBottomOffset(-(int) (mMoveDistance * (1 - fraction)));

        return true;
    }
}
