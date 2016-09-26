package com.mcxtzhang.databindingdemo.recyclerview.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

import java.util.List;

/**
 * 介绍：另一种 貌似是大帅的写法？
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class NewBaseBindingAdapter extends RecyclerView.Adapter<BaseBindingViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List mDatas;
    private LayoutInflater mInfalter;

    public NewBaseBindingAdapter(Context mContext, int mLayoutId, List mDatas) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
        this.mInfalter = LayoutInflater.from(mContext);
    }

    @Override

    public BaseBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseBindingViewHolder holder = new BaseBindingViewHolder(DataBindingUtil.inflate(mInfalter, mLayoutId, parent, false));
        onCreateViewHolder(holder);
        return holder;
    }

    /**
     * 如果需要给Vh设置监听器啥的 可以在这里
     *
     * @param holder
     */
    public void onCreateViewHolder(BaseBindingViewHolder holder) {

    }

    @Override
    public void onBindViewHolder(BaseBindingViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.data, mDatas.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }
}
