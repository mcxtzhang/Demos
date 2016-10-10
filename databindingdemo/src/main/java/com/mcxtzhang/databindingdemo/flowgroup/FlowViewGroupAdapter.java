package com.mcxtzhang.databindingdemo.flowgroup;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.mcxtzhang.databindingdemo.R;
import com.mcxtzhang.databindingdemo.databinding.ItemFlowBinding;
import com.mcxtzhang.zxtcommonlib.widget.FlowViewGroup;

import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/10.
 */

public class FlowViewGroupAdapter {
    @BindingAdapter({"flowDatas"})
    public static void setDatas(final FlowViewGroup flowViewGroup, List<FlowBean> flowBeanList) {
        flowViewGroup.removeAllViews();
        TextView tv;
        if (flowBeanList != null) {
            Context context = flowViewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            for (final FlowBean bean : flowBeanList) {
                ItemFlowBinding itemFlowBinding = DataBindingUtil.inflate(inflater, R.layout.item_flow, flowViewGroup, false);
                itemFlowBinding.setData(bean);
                flowViewGroup.addView(itemFlowBinding.getRoot());
            }
        }
    }
}
