package com.mcxtzhang.fontdemo;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 介绍：设置字体工具类 模仿洋神公众号文章
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/18.
 */

public class FontHelper {
    public static final String FONTS_DIR = "fonts/";
    public static final String DEF_FONT = FONTS_DIR + "iconfont.ttf";

    public static void injectFont(View rootView) {
        injectFont(rootView, Typeface.createFromAsset(rootView.getContext().getAssets(), DEF_FONT));
    }

    public static void injectFont(View rootView, Typeface tf) {
        if (rootView instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) rootView;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                injectFont(vg.getChildAt(i), tf);
            }
        } else if (rootView instanceof TextView) {
            ((TextView) rootView).setTypeface(tf);
        }

    }
}
