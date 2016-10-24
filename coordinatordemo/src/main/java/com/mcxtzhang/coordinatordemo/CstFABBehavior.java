package com.mcxtzhang.coordinatordemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.Log;
import android.view.View;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/10/23.
 */

public class CstFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private static final String TAG = "TAG1";

    private int mTranslationY = 80 * 3;

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll() called with: coordinatorLayout = [" + coordinatorLayout + "], child = [" + child + "], directTargetChild = [" + directTargetChild + "], target = [" + target + "], nestedScrollAxes = [" + nestedScrollAxes + "]");
        //mTranslationY = coordinatorLayout.getHeight();
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//判断是否竖直滚动
    }

    private boolean hideAnimate = false;
    private boolean showAnimate = false;

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, final FloatingActionButton child, View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, ", child = [" + child.getTop() + "], target = [" + target + "], dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed + "]");
        //dy >0 显示尾,dy<0显示头
        if (dy > 0 && child.getVisibility() == View.VISIBLE && !hideAnimate) {// hide
/*            ValueAnimator valueAnimator = ValueAnimator.ofFloat(child.getTranslationY(), mTranslationY);
            valueAnimator.setInterpolator(new FastOutSlowInInterpolator());
            valueAnimator.setDuration(200).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float v = (float) valueAnimator.getAnimatedValue();
                    child.setTranslationY(v);
                }
            });
            valueAnimator.start();*/
            hideAnimate = true;
            child.animate().cancel();
            child.animate()
                    .scaleX(0f)
                    .scaleY(0f)
                    .alpha(0f)
                    .setDuration(200)
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hideAnimate = false;
                        }
                    });
                    //.start();

        } else if (dy < 0  && !hideAnimate /*&& child.getVisibility() == View.GONE*/) {//show
            hideAnimate = true;
            child.animate().cancel();
            child.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .alpha(1)
                    .setDuration(200)
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hideAnimate = false;
                        }
                    });
                    //.start();
        }


    }

    //滑动了才会调用，而onNestedPreScroll 一直在调用。有时候target没滑动，距离被别人消耗掉了
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        //Log.d(TAG, "onNestedScroll() called with: coordinatorLayout = [" + coordinatorLayout + "], child = [" + child + "], target = [" + target + "], dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed + "]");


    }
}
