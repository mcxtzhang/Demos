package com.mcxtzhang.zxtcommonlib.databinding.base.mul;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import com.mcxtzhang.zxtcommonlib.databinding.base.BaseBindingAdapter;
import com.mcxtzhang.zxtcommonlib.databinding.base.BaseBindingViewHolder;

import java.util.List;

/**
 * 介绍：多种ItemType的Base类
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 时间： 16/09/25.
 */

public class BaseMulTypeAdapter<T extends IBaseMulInterface> extends BaseBindingAdapter {
    protected List<T> mDatas;

    public BaseMulTypeAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
        this.mDatas = mDatas;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getItemLayoutId();
    }

    @Override
    public BaseBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseBindingViewHolder holder = new BaseBindingViewHolder(DataBindingUtil.inflate(mInfalter, viewType, parent, false));
        onCreateViewHolder(holder);
        return holder;
    }

}
