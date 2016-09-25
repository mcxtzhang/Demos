package com.mcxtzhang.databindingdemo.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mcxtzhang.databindingdemo.databinding.ItemFirstRvBinding;
import com.mcxtzhang.databindingdemo.recyclerview.base.BaseBindingViewHolder;
import com.mcxtzhang.databindingdemo.recyclerview.m.FirstBindingBean;
import com.mcxtzhang.databindingdemo.recyclerview.vm.FirstBindingBeanViewModel;

import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class FirstBindingAdapter extends RecyclerView.Adapter<BaseBindingViewHolder<ItemFirstRvBinding>> {
    private Context mContext;
    private List<FirstBindingBean> mDatas;
    private LayoutInflater mInfalter;


    private FirstBindingBeanViewModel viewModel;

    public FirstBindingAdapter(Context mContext, List<FirstBindingBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInfalter = LayoutInflater.from(mContext);
        viewModel = new FirstBindingBeanViewModel();
    }

    @Override
    public BaseBindingViewHolder<ItemFirstRvBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseBindingViewHolder<ItemFirstRvBinding> holder = new BaseBindingViewHolder<>(ItemFirstRvBinding.inflate(mInfalter));
        holder.getBinding().setUtil(viewModel);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseBindingViewHolder<ItemFirstRvBinding> holder, int position) {
        holder.getBinding().setBean(mDatas.get(position));

        //holder.getBinding().executePendingBindings(); 这句话 我没加 显示也是正常的
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }
}
