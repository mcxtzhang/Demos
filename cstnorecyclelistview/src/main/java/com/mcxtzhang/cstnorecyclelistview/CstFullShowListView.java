package com.mcxtzhang.cstnorecyclelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * 介绍：不回收的全展开Lv
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/9.
 */
public class CstFullShowListView extends LinearLayout {
    private LayoutInflater mInflater;

    public CstFullShowListView(Context context) {
        this(context, null);
    }

    public CstFullShowListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CstFullShowListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        setOrientation(VERTICAL);
    }



    public interface onBindListener<T> {
        void onBind(int pos, T t, View v);
    }

    private onBindListener onBindListener;

    /**
     * 2  设置数据填充回调
     * @param onBindListener
     * @return
     */
    public CstFullShowListView setOnBindListener(CstFullShowListView.onBindListener onBindListener) {
        this.onBindListener = onBindListener;
        return this;
    }

    private int mItemLayoutId;

    /**
     * 1 设置Item布局Id
     * @param mItemLayoutId
     * @return
     */
    public CstFullShowListView setItemLayoutId(int mItemLayoutId) {
        this.mItemLayoutId = mItemLayoutId;
        return this;
    }

    private int getItemLayoutId() {
        return mItemLayoutId;
    }


    private List mDatas;

    public <T> void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        updateUI();
    }

    public <T> void updateUI() {
        removeAllViews();
        if (null != mDatas && !mDatas.isEmpty()) {
            for (int i = 0; i < mDatas.size(); i++) {
                View v = mInflater.inflate(getItemLayoutId(), this, false);
                if (null != onBindListener) {
                    onBindListener.onBind(i, mDatas.get(i), v);
                }
                addView(v);
            }
        }
    }
}
