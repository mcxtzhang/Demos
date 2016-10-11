package com.mcxtzhang.zxtcommonlib.widget.FlowLayout;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.mcxtzhang.zxtcommonlib.BR;

import java.util.List;


/**
 * 介绍：DataBinding用的 流式布局的Adapter
 * create bind 在一起做了
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/11.
 */

public class FlowDatabindingAdapter<T> extends FlowBaseAdapter<T> {
    private int mItemLayoutId;

    public FlowDatabindingAdapter(List<T> datas, Context context, int itemLayoutId) {
        super(datas, context);
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public View getView(ViewGroup parent, int pos, T data) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(mInflater, mItemLayoutId, parent, false);
        viewDataBinding.setVariable(BR.data, data);
        return viewDataBinding.getRoot();
    }
}
