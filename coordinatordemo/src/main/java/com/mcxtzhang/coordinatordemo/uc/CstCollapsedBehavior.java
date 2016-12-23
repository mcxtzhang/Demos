package com.mcxtzhang.coordinatordemo.uc;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.coordinatordemo.util.ViewOffsetBehavior;

/**
 * 折叠的Behavior  它不依赖任何View 只做嵌套滑动
 */
public class CstCollapsedBehavior extends ViewOffsetBehavior<View> {
    private static final String TAG = "zxt/CstCollapsed";

    //开始被隐藏View的高度
    protected int mTopHeight;

    public CstCollapsedBehavior() {
    }

    public CstCollapsedBehavior(Context context, AttributeSet attrs) {

        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) {
            return true;
        }
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        mTopHeight = coordinatorLayout.getChildAt(1).getHeight();
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "onNestedPreScroll(), child = [" + child + "], target = [" + target + "], dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed + "]");
/*        if (dy > 0) {
            target.setTop(target.getTop() - dy);
            consumed[1] = dy;
        }*/
        if (dy > 0) {//显示底端 折叠exit
            if (child.getBottom() - dy >= mTopHeight) {

            } else {
                dy = child.getBottom() - mTopHeight;
            }
            if (dy != 0) {
                setTopAndBottomOffset(getTopAndBottomOffset() - dy);
                consumed[1] = dy;

/*                int temp = (int) (-getTopAndBottomOffset() * 1.0f / (child.getHeight() - mTopHeight) * mMoveDistance);
                Log.e(TAG, "onNestedPreScroll: temp:" + temp);
                mTopViewHelper.setTopAndBottomOffset(temp);*/
            }
        } else {
            //展开 enter
/*            if (child.getTop() - dy <= -mTopHeight) {

            } else {
                dy = child.getTop() + mTopHeight;
            }*/
        }
        /*setTopAndBottomOffset(getTopAndBottomOffset() - dy);
        consumed[1] = dy;*/

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed == 0 && dyUnconsumed < 0) {
            //展开 enter
            if (child.getTop() - dyUnconsumed <= /*-mTopHeight*/ 0) {

            } else {
                dyUnconsumed = child.getTop() + /*mTopHeight;*/ 0;
            }
            if (dyUnconsumed != 0) {
                setTopAndBottomOffset(getTopAndBottomOffset() - dyUnconsumed);
            }

        }

    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}