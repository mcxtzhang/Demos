package com.mcxtzhang.cstnorecyclelistview.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：完全伸展开的ListView（LinearLayout）
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/9.
 */
public class CstFullShowListView extends LinearLayout {
    private LayoutInflater mInflater;
    private List<FullViewHolder> mViewCahces;//缓存ItemView的List,按照下标缓存，

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
        mViewCahces = new ArrayList<FullViewHolder>();
        setOrientation(VERTICAL);
    }


    private FullListViewAdapter mAdapter;

    /**
     * 外部调用  同时刷新视图
     *
     * @param mAdapter
     */
    public void setAdapter(FullListViewAdapter mAdapter) {
        this.mAdapter = mAdapter;
        updateUI();
    }


    public void updateUI() {
        if (null != mAdapter) {
            if (null != mAdapter.getDatas() && !mAdapter.getDatas().isEmpty()) {
                //数据源有数据
                if (mAdapter.getDatas().size() > getChildCount()) {//数据源大于现有子View不清空

                } else if (mAdapter.getDatas().size() < getChildCount()) {//数据源小于现有子View，删除后面多的
                    removeViews(mAdapter.getDatas().size(), getChildCount() - mAdapter.getDatas().size());
                    //删除View也清缓存
                    while (mViewCahces.size() > mAdapter.getDatas().size()) {
                        mViewCahces.remove(mViewCahces.size() - 1);
                    }
                }
                for (int i = 0; i < mAdapter.getDatas().size(); i++) {
                    FullViewHolder holder;
                    if (mViewCahces.size() - 1 >= i) {//说明有缓存，不用inflate，否则inflate
                        holder = mViewCahces.get(i);
                    } else {
                        holder = new FullViewHolder(getContext(), mInflater.inflate(mAdapter.getItemLayoutId(), this, false));
                        mViewCahces.add(holder);//inflate 出来后 add进来缓存
                    }
                    mAdapter.onBind(i, holder);
                    //如果View没有父控件 添加
                    if (null == holder.getConvertView().getParent()) {
                        this.addView(holder.getConvertView());
                    }
                }
            } else {
                removeAllViews();//数据源没数据 清空视图
            }
        } else {
            removeAllViews();//适配器为空 清空视图
        }
    }
}
