package com.mcxtzhang.databindingdemo.recyclerview.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mcxtzhang.databindingdemo.recyclerview.base.BaseBindingViewHolder;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public abstract class BaseBindingAdapter<T extends ViewDataBinding, K> extends RecyclerView.Adapter<BaseBindingViewHolder<T>> {
    private Context mContext;
    private int mLayoutId;
    private List<K> mDatas;
    private LayoutInflater mInfalter;

    public BaseBindingAdapter(Context mContext, int mLayoutId, List<K> mDatas) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
        this.mInfalter = LayoutInflater.from(mContext);
    }

    @Override

    public BaseBindingViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseBindingViewHolder<T> holder = new BaseBindingViewHolder<>((T) DataBindingUtil.inflate(mInfalter, mLayoutId, parent, false));
        onCreateViewHolder(holder);
        return holder;
    }

    /**
     * 如果需要给Vh设置监听器啥的 可以在这里
     *
     * @param holder
     */
    public void onCreateViewHolder(BaseBindingViewHolder<T> holder) {

    }

    @Override
    public void onBindViewHolder(BaseBindingViewHolder<T> holder, int position) {
        onBindViewHolder(holder, position, holder.getBinding(), mDatas.get(position));
    }

    /**
     * 必须实现的方法 实现数据的绑定
     *
     * @param holder
     */
    public abstract void onBindViewHolder(BaseBindingViewHolder<T> holder, int position, T binding, K k);

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }
}
