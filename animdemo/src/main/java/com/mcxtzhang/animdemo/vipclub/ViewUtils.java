package com.mcxtzhang.animdemo.vipclub;

import android.view.ViewGroup;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/12/08.
 */

public class ViewUtils {

    public static void addViews(ViewGroup viewGroup, BaseVgAdapter adapter, boolean removeViews) {
        if (viewGroup == null || adapter == null) {
            return;
        }
        if (removeViews) {
            viewGroup.removeAllViews();
        }
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            viewGroup.addView(adapter.getView(viewGroup, i));
        }
    }

}
