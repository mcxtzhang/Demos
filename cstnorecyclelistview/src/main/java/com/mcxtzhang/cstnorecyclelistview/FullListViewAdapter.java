package com.mcxtzhang.cstnorecyclelistview;

import android.view.View;

import java.util.List;

/**
 * 介绍：完全伸展开的ListView的适配器
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/09.
 */

public abstract class FullListViewAdapter<T> {
    private int mItemLayoutId;//看名字
    private List<T> mDatas;//数据源

    public FullListViewAdapter(int mItemLayoutId, List<T> mDatas) {
        this.mItemLayoutId = mItemLayoutId;
        this.mDatas = mDatas;
    }

    /**
     * 被FullListView调用
     *
     * @param i
     * @param v
     */
    public void onBind(int i, View v) {
        //回调bind方法，多传一个data过去
        onBind(i, mDatas.get(i), v);
    }

    /**
     * 数据绑定方法
     *
     * @param pos 位置
     * @param t   数据
     * @param v   ItemView
     */
    abstract void onBind(int pos, T t, View v);

    public int getItemLayoutId() {
        return mItemLayoutId;
    }

    public void setItemLayoutId(int mItemLayoutId) {
        this.mItemLayoutId = mItemLayoutId;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

}
