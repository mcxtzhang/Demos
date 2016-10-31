package com.mcxtzhang.zxtcommonlib.databinding.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mcxtzhang.zxtcommonlib.BR;

import java.util.List;

/**
 * 介绍：普通Adapter
 * 泛型D:是Bean类型，如果有就传。
 * 泛型B:是对应的xml Layout的Bingding类
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class BaseBindingAdapter<D, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseBindingVH<B>> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<D> mDatas;
    protected LayoutInflater mInfalter;

    public BaseBindingAdapter(Context mContext, int mLayoutId, List mDatas) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
        this.mInfalter = LayoutInflater.from(mContext);
    }

    public BaseBindingAdapter(Context mContext, List mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mInfalter = LayoutInflater.from(mContext);
    }

    @Override
    public BaseBindingVH<B> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseBindingVH<B> holder = new BaseBindingVH<B>((B) DataBindingUtil.inflate(mInfalter, mLayoutId, parent, false));
        onCreateViewHolder(holder);
        return holder;
    }

    /**
     * 如果需要给Vh设置监听器啥的 可以在这里
     *
     * @param holder
     */
    public void onCreateViewHolder(BaseBindingVH<B> holder) {

    }

    /**
     * 子类除了绑定数据，还要设置监听器等其他操作。
     * 可以重写这个方法，不要删掉super.onBindViewHolder(holder, position);
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseBindingVH<B> holder, int position) {
        holder.getBinding().setVariable(BR.data, mDatas.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }
}
