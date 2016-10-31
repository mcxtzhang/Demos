package com.mcxtzhang.databindingdemo.recyclerview.multype;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.mcxtzhang.databindingdemo.R;
import com.mcxtzhang.zxtcommonlib.databinding.base.BaseBindingVH;

import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class MulTypeAdapter<K> extends RecyclerView.Adapter<BaseBindingVH> {
    private Context mContext;
    private List<MulTypeBean> mDatas;
    private LayoutInflater mInfalter;

    public MulTypeAdapter(Context mContext, List<MulTypeBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mInfalter = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getRole();
    }

    @Override
    public BaseBindingVH onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new BaseBindingVH(DataBindingUtil.inflate(mInfalter, R.layout.item_mul_type_1, parent, false));
            case 0:
                return new BaseBindingVH(DataBindingUtil.inflate(mInfalter, R.layout.item_first_rv, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseBindingVH holder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                holder.getBinding().setVariable(BR.data, mDatas.get(position));
                break;
            case 0:
                holder.getBinding().setVariable(BR.bean, mDatas.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }
}
