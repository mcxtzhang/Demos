package com.mcxtzhang.databindingdemo.recyclerview.multype;

import com.mcxtzhang.databindingdemo.R;
import com.mcxtzhang.zxtcommonlib.databinding.base.mul.IBaseMulInterface;

/**
 * 介绍：多种Item的Bean1
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/27.
 */

public class MBean1 implements IBaseMulInterface {
    private String url;
    private String name;

    public MBean1(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public MBean1 setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public MBean1 setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_mul_1;
    }
}
