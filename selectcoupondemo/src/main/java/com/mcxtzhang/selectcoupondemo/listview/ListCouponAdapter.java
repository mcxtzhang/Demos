package com.mcxtzhang.selectcoupondemo.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.selectcoupondemo.R;
import com.mcxtzhang.selectcoupondemo.TestBean;

import java.util.List;

import static com.mcxtzhang.selectcoupondemo.R.id.ivSelect;
import static com.mcxtzhang.selectcoupondemo.R.id.tvCoupon;

/**
 * 介绍：ListView的Adapter
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/29.
 */

public class ListCouponAdapter extends BaseAdapter {
    private List<TestBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public ListCouponAdapter(List<TestBean> datas, Context context) {
        mDatas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CouponVH couponVH;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_coupon, viewGroup, false);
            couponVH = new CouponVH();
            couponVH.ivSelect = (ImageView) view.findViewById(ivSelect);
            couponVH.tvCoupon = (TextView) view.findViewById(tvCoupon);
            view.setTag(couponVH);
        } else {
            couponVH = (CouponVH) view.getTag();
        }
        couponVH.ivSelect.setSelected(mDatas.get(i).isSelected());
        couponVH.tvCoupon.setText(mDatas.get(i).getName());
        return view;
    }


    public static class CouponVH {
        private ImageView ivSelect;
        private TextView tvCoupon;
    }
}
