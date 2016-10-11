package com.mcxtzhang.zxtcommonlib.widget.FlowLayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 介绍：一个简化的Adapter 只支持用LayoutId 构建View
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/11.
 */

public abstract class FlowSimpleAdapter<T> extends FlowBaseAdapter<T> {

    private int mItemLayoutId;

    public FlowSimpleAdapter(List<T> datas, Context context, int itemLayoutId) {
        super(datas, context);
        mItemLayoutId = itemLayoutId;
    }

    /**
     * 通过ItemLayoutId inflate View
     *
     * @param parent
     * @param pos
     * @return
     */
    @Override
    public View onCreateView(ViewGroup parent, int pos) {
        return createItemView(parent, pos);
    }

    public View createItemView(ViewGroup parent, int pos) {
        return mInflater.inflate(mItemLayoutId, parent, false);
    }
}
