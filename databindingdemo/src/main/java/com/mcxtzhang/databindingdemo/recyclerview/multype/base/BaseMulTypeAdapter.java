package com.mcxtzhang.databindingdemo.recyclerview.multype.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.mcxtzhang.databindingdemo.R;
import com.mcxtzhang.databindingdemo.recyclerview.base.BaseBindingViewHolder;

import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class BaseMulTypeAdapter<T extends IBaseMulInterface> extends RecyclerView.Adapter<BaseBindingViewHolder> {
    private Context mContext;
    private List<T> mDatas;
    private LayoutInflater mInfalter;

    public BaseMulTypeAdapter(Context mContext, List mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mInfalter = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getItemLayoutId();
    }

    @Override
    public BaseBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseBindingViewHolder(DataBindingUtil.inflate(mInfalter, viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseBindingViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.data, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }
}
