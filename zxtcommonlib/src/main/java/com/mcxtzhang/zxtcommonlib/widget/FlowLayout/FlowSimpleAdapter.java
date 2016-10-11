package com.mcxtzhang.zxtcommonlib.widget.FlowLayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 介绍：一个简化的Adapter 只支持用LayoutId 构建View
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/11.
 */

public abstract class FlowSimpleAdapter<T> extends FlowBaseAdapter<T> {

    private int mItemLayoutId;

    public FlowSimpleAdapter(List<T> datas, Context context, int itemLayoutId) {
        super(datas, context);
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public View getView(ViewGroup parent, int pos, T data) {
        //实现getView
        View itemView = /*onCreateView(parent, pos)*/mInflater.inflate(mItemLayoutId, parent, false);
        onBindView(parent, itemView, data, pos);
        return itemView;
    }

    /**
     * 暴漏这个 让外部bind数据
     *
     * @param parent
     * @param itemView
     * @param data
     * @param pos
     */
    public abstract void onBindView(ViewGroup parent, View itemView, T data, int pos);

/*    *//**
     * 通过ItemLayoutId inflate View
     *
     * @param parent
     * @param pos
     * @return
     *//*
    public View onCreateView(ViewGroup parent, int pos) {
        return createItemView(parent, pos);
    }

    public View createItemView(ViewGroup parent, int pos) {
        return mInflater.inflate(mItemLayoutId, parent, false);
    }*/
}
