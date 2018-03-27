package com.mcxtzhang.coordinatordemo.ir;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.coordinatordemo.R;
import com.mcxtzhang.coordinatordemo.util.ViewOffsetBehavior;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/22.
 */

public class CsScrollContentBehavior extends ViewOffsetBehavior<View> {
    private static final String TAG = "zxt/内容Behavior";

    public CsScrollContentBehavior() {
        super();
    }

    public CsScrollContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "layoutDependsOn() called with: parent = [" + parent + "], child = [" + child + "], dependency = [" + dependency + "]");
        boolean flag = dependency == parent.findViewById(R.id.bigTitleBottomSpace);
        return flag;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "onDependentViewChanged() called with: parent = [" + parent + "], child = [" + child + "], dependency = [" + dependency + "]");
        setTopAndBottomOffset(dependency.getBottom());
        return true;
    }


}
