package com.mcxtzhang.coordinatordemo.zhihu;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.coordinatordemo.base.BaseUpDownBehavior;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/21.
 */

public class CstZHFABBehavior extends BaseUpDownBehavior<View> {

    protected int ANIM_TIME = 500;

    public CstZHFABBehavior() {
    }

    public CstZHFABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onScrollUp(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        child.animate()
                .translationY(0)
                .rotation(0)
                .setDuration(ANIM_TIME)
                .start();

    }

    @Override
    public void onScrollDown(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        child.animate()
                .rotation(359)
                .translationY(child.getTop())
                .setDuration(ANIM_TIME)
                .start();
    }
}
