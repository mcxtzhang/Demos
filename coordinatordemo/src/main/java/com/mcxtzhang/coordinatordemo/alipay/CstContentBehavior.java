package com.mcxtzhang.coordinatordemo.alipay;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/22.
 */

public class CstContentBehavior extends CoordinatorLayout.Behavior<NestedScrollView> {
    private static final String TAG = "zxt/CstContentBehavior";
    public CstContentBehavior() {
    }

    public CstContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, NestedScrollView child, View dependency) {
        return /*dependency.getId() == R.id.topLayout*/ false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, NestedScrollView child, View dependency) {
        Log.d(TAG, "onDependentViewChanged() called with: parent = [" + parent + "], child = [" + child + "], dependency = [" + dependency + "]");

        ViewCompat.offsetTopAndBottom(child, dependency.getHeight());
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
