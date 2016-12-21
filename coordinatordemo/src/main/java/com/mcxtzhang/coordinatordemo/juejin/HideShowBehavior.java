package com.mcxtzhang.coordinatordemo.juejin;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;

import com.mcxtzhang.coordinatordemo.base.BaseUpDownBehavior;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/21.
 */

public class HideShowBehavior extends BaseUpDownBehavior<View> {
    private static final String TAG = "zxt/HideShowBehavior";
    private Context mContext;
    private static int SCROLL_VALUE;

    public HideShowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        SCROLL_VALUE = ViewConfiguration.get(context).getScaledTouchSlop();
        Log.d(TAG, "HideShowBehavior() called with: SCROLL_VALUE = [" + SCROLL_VALUE + "], attrs = [" + attrs + "]");
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.i(TAG, "onStartNestedScroll() called with: coordinatorLayout = [" + coordinatorLayout + "], child = [" + child + "], directTargetChild = [" + directTargetChild + "], target = [" + target + "], nestedScrollAxes = [" + nestedScrollAxes + "]");
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.d(TAG, "onNestedScrollAccepted() called with: coordinatorLayout = [" + coordinatorLayout + "], child = [" + child + "], directTargetChild = [" + directTargetChild + "], target = [" + target + "], nestedScrollAxes = [" + nestedScrollAxes + "]");
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        Log.w(TAG, "onStopNestedScroll() called with: coordinatorLayout = [" + coordinatorLayout + "], child = [" + child + "], target = [" + target + "]");
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "onNestedScroll() called with: coordinatorLayout = [" + coordinatorLayout + "], child = [" + child + "], target = [" + target + "], dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed + "]");
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    protected int ANIM_TIME = 200;

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        Log.v(TAG, "onNestedPreScroll() called with: coordinatorLayout = [" + coordinatorLayout + "], child = [" + child + "], target = [" + target + "], dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed + "]");

    }

    @Override
    public void onScrollUp(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        child.animate().scaleX(1).scaleY(1)
                .alpha(1)
                .setDuration(ANIM_TIME)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    @Override
    public void onScrollDown(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        child.animate().scaleX(0).scaleY(0)
                .alpha(0)
                .setDuration(ANIM_TIME)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

}
