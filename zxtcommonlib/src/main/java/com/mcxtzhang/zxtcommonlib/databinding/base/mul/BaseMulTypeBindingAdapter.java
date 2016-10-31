package com.mcxtzhang.zxtcommonlib.databinding.base.mul;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import com.mcxtzhang.zxtcommonlib.databinding.base.BaseBindingAdapter;
import com.mcxtzhang.zxtcommonlib.databinding.base.BaseBindingVH;

import java.util.List;

/**
 * 介绍：多种ItemType的Base类
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 时间： 16/09/25.
 */

public class BaseMulTypeBindingAdapter<T extends IBaseMulInterface> extends BaseBindingAdapter {
    protected List<T> mDatas;

    public BaseMulTypeBindingAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
        this.mDatas = mDatas;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getItemLayoutId();
    }

    @Override
    public BaseBindingVH onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseBindingVH holder = new BaseBindingVH(DataBindingUtil.inflate(mInfalter, viewType, parent, false));
        onCreateViewHolder(holder);
        return holder;
    }

}
