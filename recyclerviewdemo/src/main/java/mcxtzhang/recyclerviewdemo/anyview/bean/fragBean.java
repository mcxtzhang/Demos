package mcxtzhang.recyclerviewdemo.anyview.bean;


import com.mcxtzhang.commonadapter.databinding.rv.mul.IBaseMulInterface;

import mcxtzhang.recyclerviewdemo.R;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/12/07.
 */

public class FragBean implements IBaseMulInterface {
    @Override
    public int getItemLayoutId() {
        return R.layout.item_fragment;
    }
}
