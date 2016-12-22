package com.mcxtzhang.coordinatordemo.uc;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CstTopBehavior extends CoordinatorLayout.Behavior<CstTopLayout> {
    private static final String TAG = "zxt/CstTopBehavior";

    protected int mTopHeight;

    public CstTopBehavior() {
    }

    public CstTopBehavior(Context context, AttributeSet attrs) {

        super(context, attrs);
        Log.d(TAG, "CstTopBehavior() called with: context = [" + context + "], attrs = [" + attrs + "]");
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, CstTopLayout child, int layoutDirection) {
        mTopHeight = child.getChildAt(0).getMeasuredHeight();
        Log.d(TAG, "onLayoutChild() called with: parent = [" + mTopHeight + "], child = [" + child + "], layoutDirection = [" + layoutDirection + "]");
        child.layout(child.getLeft(), -mTopHeight
                , child.getLeft() + child.getMeasuredWidth(), -mTopHeight + child.getMeasuredHeight());
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, CstTopLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        if (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) {
            return true;
        }
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, CstTopLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, CstTopLayout child, View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "onNestedPreScroll() called with: coordinatorLayout = [" + coordinatorLayout + "], child = [" + child + "], target = [" + target + "], dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed + "]");
/*        if (dy > 0) {
            target.setTop(target.getTop() - dy);
            consumed[1] = dy;
        }*/
        ViewCompat.offsetTopAndBottom(child, 20);
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, CstTopLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, CstTopLayout child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}