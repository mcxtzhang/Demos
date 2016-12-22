package com.mcxtzhang.coordinatordemo.uc;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/22.
 */

public class CstTopLayout extends LinearLayout {
    private static final String TAG = "zxt/CstTopLayout";
    private int mBeginOffset;

    public CstTopLayout(Context context) {
        super(context);
        init(context);
    }

    public CstTopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CstTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

    }

    public int getBeginOffset() {
        return mBeginOffset;
    }

    public CstTopLayout setBeginOffset(int beginOffset) {
        mBeginOffset = beginOffset;
        return this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged() called with: w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
        super.onSizeChanged(w, h, oldw, oldh);
        mBeginOffset = -getChildAt(0).getMeasuredHeight();
        //setY(mBeginOffset);
        //ViewCompat.offsetTopAndBottom(this, mBeginOffset);
        //setTop(mBeginOffset);
    }
}
