package com.mcxtzhang.coordinatordemo.base;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/21.
 */

public interface IUpDownCallback {

    //向头部滑动
    void onScrollUp(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed);

    //向底部滑动
    void onScrollDown(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed);
}
