package com.mcxtzhang.coordinatordemo.base;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.view.View;

/**
 * 介绍：基类的上滑下滑Behavior
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/21.
 */

public abstract class BaseUpDownBehavior<V extends View> extends CoordinatorLayout.Behavior<V> implements IUpDownCallback {
    public BaseUpDownBehavior() {
    }

    public BaseUpDownBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //上一次的dy
    protected int preDy;


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        return dependency instanceof NestedScrollingChild;
    }

    /**
     * 嵌套滑动的时候，开始调用。 所有CoordinatorLayout的直接子View的Behavior都要触发这个函数 返回true代表嵌套滑动了。 只有返回true，才会接管 随后的嵌套滚动事件。
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
        //显示底部 隐藏
        if (dy > 0) {
            if (preDy <= 0) {
                onScrollDown(coordinatorLayout, child, target, dx, dy, consumed);
            }
        } else {//显示头部 显示
            if (preDy >= 0) {
                onScrollUp(coordinatorLayout, child, target, dx, dy, consumed);
            }
        }
        preDy = dy;
    }

}
