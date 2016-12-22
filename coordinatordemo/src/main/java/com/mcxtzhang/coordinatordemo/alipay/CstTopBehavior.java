package com.mcxtzhang.coordinatordemo.alipay;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/22.
 */

public class CstTopBehavior extends CoordinatorLayout.Behavior<ViewGroup> {

    ViewGroup beforeView;
    ViewGroup afterView;

    public CstTopBehavior() {
    }

    public CstTopBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ViewGroup child, View dependency) {
        return child instanceof NestedScrollingChild;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ViewGroup child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, ViewGroup child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        //beforeView = (ViewGroup) child.findViewById(R.id.before);
        //afterView = (ViewGroup) child.findViewById(R.id.after);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ViewGroup child, View target, int dx, int dy, int[] consumed) {
        //显示底部
        ViewGroup.LayoutParams lp1 = beforeView.getLayoutParams();
        ViewGroup.LayoutParams lp2 = afterView.getLayoutParams();
        int heightGap = lp1.height - lp2.height;

        if (dy > 0 && heightGap > 0) {
            //折叠自己

            if (heightGap >= dy) {
                lp1.height -= dy;
                beforeView.setLayoutParams(lp1);
                consumed[1] = dy;
            } else {
                lp1.height = lp2.height;
                afterView.setLayoutParams(lp1);
                consumed[1] = heightGap;
            }

        } else {
            //显示头部

        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, ViewGroup child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, ViewGroup child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}
