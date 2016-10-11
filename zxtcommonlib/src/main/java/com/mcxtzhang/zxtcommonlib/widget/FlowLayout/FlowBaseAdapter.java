package com.mcxtzhang.zxtcommonlib.widget.FlowLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 介绍：基类Adapter
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/11.
 */

public abstract class FlowBaseAdapter<T> {
    protected List<T> mDatas;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public FlowBaseAdapter(List<T> datas, Context context) {
        mDatas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public View getView(ViewGroup parent, int pos) {
        View itemView = onCreateView(parent, pos);
        onBindView(parent, itemView, mDatas.get(pos), pos);
        return itemView;
    }

    public abstract View onCreateView(ViewGroup parent, int pos);

    public abstract void onBindView(ViewGroup parent, View itemView, T data, int pos);

    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public FlowBaseAdapter setDatas(List<T> datas) {
        mDatas = datas;
        return this;
    }
}
