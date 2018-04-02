package com.mcxtzhang.coordinatordemo.ir;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.coordinatordemo.R;
import com.mcxtzhang.coordinatordemo.util.ViewOffsetBehavior;

/**
 * 折叠的Behavior  它不依赖任何View 只做嵌套滑动
 */
public class CstCollapsedBehavior extends ViewOffsetBehavior<View> {
    private static final String TAG = "zxt/CstCollapsed";

    private boolean init = true;

    //开始被隐藏的View
    private View mLitterTitle;

    private int mSelfHeight;
    private int mTotalOffsetY;

    public CstCollapsedBehavior() {
    }

    public CstCollapsedBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "layoutDependsOn() called with: parent = [" + parent + "], child = [" + child + "], dependency = [" + dependency + "]");
        boolean flag = dependency == parent.findViewById(R.id.bitTitleTopSpace);
        return flag;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "onDependentViewChanged() called with: parent = [" + parent + "], child = [" + child + "], dependency = [" + dependency + "]");
        if (init) {
            init = false;
            setTopAndBottomOffset(dependency.getBottom());
            mLitterTitle = parent.findViewById(R.id.litterTitle);
            //mTopHeight = mLitterTitle.getHeight();
            mSelfHeight = child.getHeight();
            return true;
        } else {
            return super.onDependentViewChanged(parent, child, dependency);
        }
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) {
            return true;
        }
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "onNestedPreScroll(), child = [" + child + "], target = [" + target + "], dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed + "]");
/*        if (dy > 0) {
            target.setTop(target.getTop() - dy);
            consumed[1] = dy;
        }*/
        Log.w(TAG, "onNestedPreScroll: dy:" + dy + ", mTotalOffsetY:" + mTotalOffsetY + ", mSelfHeight:" + mSelfHeight);
        if (dy > 0) {//显示底端 折叠exit
            if (dy + mTotalOffsetY <= mSelfHeight) {

            } else {
                dy = mSelfHeight - mTotalOffsetY;
            }
            mTotalOffsetY = mTotalOffsetY + dy;

            if (dy != 0) {
                setTopAndBottomOffset(getTopAndBottomOffset() - dy);
                consumed[1] = dy;
            }
        } else {
            //做quick show 需要用到
        }
        if (mTotalOffsetY == mSelfHeight) {
            mLitterTitle.setVisibility(View.VISIBLE);
        } else {
            mLitterTitle.setVisibility(View.GONE);
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.d(TAG, "onNestedScroll() called with: coordinatorLayout = [" + coordinatorLayout + "], child = [" + child + "], target = [" + target + "], dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed + "]");
        if (dyConsumed == 0 && dyUnconsumed < 0) {
            Log.w(TAG, "onNestedScroll: mTotalOffsetY:" + mTotalOffsetY + ",  dyUnconsumed:" + dyUnconsumed);
            //展开 enter
            if (mTotalOffsetY + dyUnconsumed >= /*-mTopHeight*/ 0) {

            } else {
                dyUnconsumed = -mTotalOffsetY;
            }

            mTotalOffsetY = mTotalOffsetY + dyUnconsumed;

            if (dyUnconsumed != 0) {
                setTopAndBottomOffset(getTopAndBottomOffset() - dyUnconsumed);
            }

        }

        if (mTotalOffsetY == mSelfHeight) {
            mLitterTitle.setVisibility(View.VISIBLE);
        } else {
            mLitterTitle.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling() called with: child = [" + child + "], target = [" + target + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "], consumed = [" + consumed + "]");
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}